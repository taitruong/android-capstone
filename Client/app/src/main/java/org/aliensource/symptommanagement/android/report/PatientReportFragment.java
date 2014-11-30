package org.aliensource.symptommanagement.android.report;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.ValueDependentColor;

import org.aliensource.symptommanagement.android.AbstractFragment;
import org.aliensource.symptommanagement.android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.InjectView;

/**
 * Created by ttruong on 30-Nov-14.
 */
public class PatientReportFragment extends AbstractFragment implements View.OnTouchListener{

    private int currentX;
    private int currentY;
    @InjectView(R.id.container)
    protected RelativeLayout container;

    @InjectView(R.id.soreThroatLayout)
    protected LinearLayout soreThroatLayout;

    @InjectView(R.id.eatDrinkLayout)
    protected LinearLayout eatDrinkLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        createSoreThroatReport();
        createEatDrinkReport();
        view.setOnTouchListener(this);
        container.setOnTouchListener(this);
        soreThroatLayout.setOnTouchListener(this);
        eatDrinkLayout.setOnTouchListener(this);
        return view;
    }

    protected void createSoreThroatReport() {
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
        // init example series data
        Calendar cal1 = new GregorianCalendar();
        cal1.add(Calendar.DAY_OF_MONTH, -1);
        Calendar cal2 = new GregorianCalendar();
        cal2.add(Calendar.DAY_OF_MONTH, -1);
        cal2.add(Calendar.HOUR_OF_DAY, 2);
        Calendar cal3 = new GregorianCalendar();
        cal3.add(Calendar.DAY_OF_MONTH, -1);
        cal3.add(Calendar.HOUR_OF_DAY, 8);
        Calendar cal4 = new GregorianCalendar();
        cal4.add(Calendar.DAY_OF_MONTH, -1);
        cal4.add(Calendar.HOUR_OF_DAY, 12);
        Calendar cal5 = new GregorianCalendar();
        cal5.add(Calendar.DAY_OF_MONTH, -1);
        cal5.add(Calendar.HOUR_OF_DAY, 16);
        Calendar cal6 = new GregorianCalendar();
        cal6.add(Calendar.DAY_OF_MONTH, -1);
        cal6.add(Calendar.HOUR_OF_DAY, 18);
        Calendar cal7 = new GregorianCalendar();
        cal7.add(Calendar.DAY_OF_MONTH, -1);
        cal7.add(Calendar.HOUR_OF_DAY, 20);

        GraphViewSeries exampleSeries = new GraphViewSeries(
                "XXXXX",
                seriesStyle,
                new GraphView.GraphViewData[] {
                    new GraphView.GraphViewData(cal1.getTimeInMillis(), 0)
                    , new GraphView.GraphViewData(cal2.getTimeInMillis(), 1)
                    , new GraphView.GraphViewData(cal3.getTimeInMillis(), 1)
                    , new GraphView.GraphViewData(cal4.getTimeInMillis(), 0)
                    , new GraphView.GraphViewData(cal5.getTimeInMillis(), 2)
                    , new GraphView.GraphViewData(cal6.getTimeInMillis(), 2)
                    , new GraphView.GraphViewData(cal7.getTimeInMillis(), 3)
        });

        GraphView graphView = new BarGraphView(
                getActivity() // context
                , getString(R.string.sore_throat) // heading
        );
        graphView.addSeries(exampleSeries); // data

        graphView.setScrollable(false);
        graphView.setScalable(false);
        String[] verticalLabels = {
                getString(R.string.check_in_symptom1_value3),
                getString(R.string.check_in_symptom1_value2),
                getString(R.string.check_in_symptom1_value1),
                ""};
        graphView.setVerticalLabels(verticalLabels);
        graphView.getGraphViewStyle().setTextSize(getResources().getDimension(R.dimen.text_size_small));

        //horizontal custom label
		/*
		 * date as label formatter
		 */
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
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
        graphView.setOnTouchListener(this);
        soreThroatLayout.addView(graphView);
    }

    protected void createEatDrinkReport() {
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
        // init example series data
        Calendar cal1 = new GregorianCalendar();
        cal1.add(Calendar.DAY_OF_MONTH, -2);
        Calendar cal2 = new GregorianCalendar();
        cal2.add(Calendar.DAY_OF_MONTH, -2);
        cal2.add(Calendar.HOUR_OF_DAY, 2);
        Calendar cal3 = new GregorianCalendar();
        cal3.add(Calendar.DAY_OF_MONTH, -2);
        cal3.add(Calendar.HOUR_OF_DAY, 8);
        Calendar cal4 = new GregorianCalendar();
        cal4.add(Calendar.DAY_OF_MONTH, -2);
        cal4.add(Calendar.HOUR_OF_DAY, 12);
        Calendar cal5 = new GregorianCalendar();
        cal5.add(Calendar.DAY_OF_MONTH, -2);
        cal5.add(Calendar.HOUR_OF_DAY, 16);
        Calendar cal6 = new GregorianCalendar();
        cal6.add(Calendar.DAY_OF_MONTH, -2);
        cal6.add(Calendar.HOUR_OF_DAY, 18);
        Calendar cal7 = new GregorianCalendar();
        cal7.add(Calendar.DAY_OF_MONTH, -2);
        cal7.add(Calendar.HOUR_OF_DAY, 20);
        Calendar cal8 = new GregorianCalendar();
        cal8.add(Calendar.DAY_OF_MONTH, -2);
        cal8.add(Calendar.HOUR_OF_DAY, 48);

        GraphViewSeries exampleSeries = new GraphViewSeries(
                "",
                seriesStyle,
                new GraphView.GraphViewData[] {
                        new GraphView.GraphViewData(cal1.getTimeInMillis(), 0)
                        , new GraphView.GraphViewData(cal2.getTimeInMillis(), 1)
                        , new GraphView.GraphViewData(cal3.getTimeInMillis(), 1)
                        , new GraphView.GraphViewData(cal4.getTimeInMillis(), 0)
                        , new GraphView.GraphViewData(cal5.getTimeInMillis(), 2)
                        , new GraphView.GraphViewData(cal6.getTimeInMillis(), 2)
                        , new GraphView.GraphViewData(cal7.getTimeInMillis(), 3)
                        , new GraphView.GraphViewData(cal8.getTimeInMillis(), 1)
                });

        GraphView graphView = new BarGraphView(
                getActivity() // context
                , getString(R.string.eat_drink) // heading
        );
        graphView.addSeries(exampleSeries); // data

        graphView.setScrollable(true);
        graphView.setScalable(true);
        String[] verticalLabels = {
                getString(R.string.check_in_symptom2_value3),
                getString(R.string.check_in_symptom2_value2),
                getString(R.string.check_in_symptom2_value1),
                ""};
        graphView.setVerticalLabels(verticalLabels);
        graphView.getGraphViewStyle().setTextSize(getResources().getDimension(R.dimen.text_size_small));

        //horizontal custom label
		/*
		 * date as label formatter
		 */
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
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
        graphView.setOnTouchListener(this);
        eatDrinkLayout.addView(graphView);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                currentX = (int) event.getRawX();
                currentY = (int) event.getRawY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                int x2 = (int) event.getRawX();
                int y2 = (int) event.getRawY();
                container.scrollBy(currentX - x2 , currentY - y2);
                currentX = x2;
                currentY = y2;
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        return true;
    }

}
