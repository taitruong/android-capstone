package org.aliensource.symptommanagement.android.checkin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.aliensource.symptommanagement.DateTimeUtils;
import org.aliensource.symptommanagement.android.AbstractFragment;
import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.client.service.CallableTask;
import org.aliensource.symptommanagement.client.service.SymptomSvc;
import org.aliensource.symptommanagement.client.service.TaskCallback;
import org.aliensource.symptommanagement.cloud.repository.IntakeTime;
import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Callable;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ttruong on 24-Nov-14.
 */
public class SaveFragment extends AbstractFragment {

    @InjectView(R.id.saveCheckIn)
    Button save;

    @InjectView(R.id.cancelCheckIn)
    Button cancel;

    //the number of the medications
    protected int prefSuffix;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        prefSuffix = getArguments().getInt(CheckInUtils.ARG_PREF_SUFFIX);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @OnClick(R.id.saveCheckIn)
    protected void save() {
        SymptomTime s1 = createSymptom(0);
        SymptomTime s2 = createSymptom(1);

    }

    protected SymptomTime createSymptom(int prefSuffix) {
        final DataWrapper data = CheckInUtils.getEditor(getActivity(), prefSuffix, CheckInUtils.PREF_SYMPTOM_PREFIX);
        //set reference to symptom
        final SymptomTime symptomTime = new SymptomTime();
        final SymptomSvcApi api = SymptomSvc.getInstance().init(getActivity());
        CallableTask.invoke(new Callable<Symptom>() {
            @Override
            public Symptom call() throws Exception {
                return api.findOne(data.id);
            }
        }, new TaskCallback<Symptom>() {
            @Override
            public void success(Symptom result) {
                symptomTime.setSymptom(result);
            }

            @Override
            public void error(Exception e) {
                throw new RuntimeException("Symptom with id " + data.id + " not found!");
            }
        });
        //set timestamp
        symptomTime.setTimestamp(data.dateTime);
        symptomTime.setSeverity(data.selection);
        return symptomTime;
    }

    protected void createIntakeTimes() {
        for (int suffix = 0; suffix <= prefSuffix; suffix++) {
            final DataWrapper data = CheckInUtils.getEditor(getActivity(), suffix, CheckInUtils.PREF_SYMPTOM_PREFIX);
            IntakeTime intakeTime = new IntakeTime();
            intakeTime.setTimestamp(data.dateTime);
        }
    }
}
