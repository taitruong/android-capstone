package org.aliensource.symptommanagement.android.report;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.ValueDependentColor;

import org.aliensource.symptommanagement.android.AbstractFragment;
import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.doctor.DoctorUtils;
import org.aliensource.symptommanagement.client.service.CallableTask;
import org.aliensource.symptommanagement.client.service.PatientSvc;
import org.aliensource.symptommanagement.client.service.TaskCallback;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.InjectView;

/**
 * Created by ttruong on 30-Nov-14.
 */
public class PatientReportFragment extends AbstractFragment {

    @InjectView(R.id.soreThroatMouthPainLayout)
    protected LinearLayout soreThroatMouthPainLayout;

    @InjectView(R.id.eatDrinkLayout)
    protected LinearLayout eatDrinkLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        createReport();
        return view;
    }

    protected void createReport() {
        final PatientSvcApi patientSvcApi = PatientSvc.getInstance().init(getActivity());
        final long patientId = DoctorUtils.getPatientId(getActivity());
        CallableTask.invoke( new Callable<List<SymptomTime>[]>() {
            @Override
            public List<SymptomTime>[] call() throws Exception {
                List<SymptomTime>[] soreThroatEatDrink =
                        patientSvcApi.getSymptomTimesByEatDrinkOrSoreThroatMouthPain(patientId);
                return soreThroatEatDrink;
            }
        }, new TaskCallback<List<SymptomTime>[]>() {
            @Override
            public void success(List<SymptomTime>[] soreThroatEatDrink) {
                if (!soreThroatEatDrink[0].isEmpty()) {
                    soreThroatMouthPainLayout.addView(
                            createReport(
                                    soreThroatEatDrink[0],
                                    getString(R.string.sore_throat_mouth_pain),
                                    getString(R.string.check_in_symptom1_value3),
                                    getString(R.string.check_in_symptom1_value2),
                                    getString(R.string.check_in_symptom1_value1)));
                }
                if (!soreThroatEatDrink[1].isEmpty()) {
                    eatDrinkLayout.addView(
                            createReport(
                                    soreThroatEatDrink[1],
                                    getString(R.string.eat_drink),
                                    getString(R.string.check_in_symptom2_value3),
                                    getString(R.string.check_in_symptom2_value2),
                                    getString(R.string.check_in_symptom2_value1)));
                }

            }

            @Override
            public void error(Exception e) {

            }
        });
    }

    protected GraphView.GraphViewData[] createData(List<SymptomTime> allSymptomTimes) {
        List<GraphView.GraphViewData> data = new ArrayList<GraphView.GraphViewData>();
        long last = 0;
        for (SymptomTime symptomTime: allSymptomTimes) {
            Date date = new Date(symptomTime.getTimestamp());
            data.add(new GraphView.GraphViewData(symptomTime.getTimestamp(), symptomTime.getSeverity() + 1));
            last = symptomTime.getTimestamp();
        }
        //add current date as baseline
        data.add(new GraphView.GraphViewData(last + 1, 0));
        GraphView.GraphViewData[] result = new GraphView.GraphViewData[data.size()];
        return data.toArray(result);
    }

    protected GraphView createReport(
            List<SymptomTime> allSymptomTimes,
            String title,
            String vLabel3,
            String vLabel2,
            String vLabel1) {
        GraphViewSeries.GraphViewSeriesStyle seriesStyle = new GraphViewSeries.GraphViewSeriesStyle();
        seriesStyle.setValueDependentColor(new ValueDependentColor() {
            @Override
            public int get(GraphViewDataInterface data) {

                if (data.getY() >= 3) {
                    return Color.RED;
                } else if (data.getY() >= 2) {
                    return Color.YELLOW;
                } else {
                    return Color.GREEN;
                }
            }
        });

        GraphView.GraphViewData[] data = createData(allSymptomTimes);

        GraphViewSeries exampleSeries = new GraphViewSeries( "", seriesStyle, data);

        GraphView graphView = new BarGraphView(
                getActivity() // context
                , title // heading
        );
        graphView.addSeries(exampleSeries); // data

        graphView.setScrollable(false);
        graphView.setScalable(false);
        String[] verticalLabels = {
                vLabel3,
                vLabel2,
                vLabel1,
                ""};
        graphView.setVerticalLabels(verticalLabels);
        graphView.getGraphViewStyle().setTextSize(getResources().getDimension(R.dimen.text_size_xsmall));
        graphView.getGraphViewStyle().setVerticalLabelsWidth(200);
        //horizontal custom label
		/*
		 * date as label formatter
		 */
        final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd HH:ss");
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Date d = new Date((long) value);
                    return dateFormat.format(d);
                }
                return null; // let graphview generate Y-axis label for us
            }
        });

        graphView.getGraphViewStyle().setGridStyle(GraphViewStyle.GridStyle.BOTH);
        graphView.getGraphViewStyle().setGridColor(Color.GREEN);
        return graphView;
    }

}
