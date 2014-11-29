package org.aliensource.symptommanagement.android.checkin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.aliensource.symptommanagement.DateTimeUtils;
import org.aliensource.symptommanagement.android.AbstractFragment;
import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.main.MainActivity;
import org.aliensource.symptommanagement.android.main.MainUtils;
import org.aliensource.symptommanagement.client.service.CallableTask;
import org.aliensource.symptommanagement.client.service.MedicationSvc;
import org.aliensource.symptommanagement.client.service.PatientSvc;
import org.aliensource.symptommanagement.client.service.SymptomSvc;
import org.aliensource.symptommanagement.client.service.TaskCallback;
import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.IntakeTime;
import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;
import org.aliensource.symptommanagement.cloud.service.MedicationSvcApi;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ttruong on 24-Nov-14.
 */
public class SaveFragment extends AbstractFragment {

    public static final String TAG = "Checkin.SaveFragment";

    @InjectView(R.id.saveCheckIn)
    Button save;

    @InjectView(R.id.cancelCheckIn)
    Button cancel;

    //the number of the medications
    protected int prefSuffix;

    @Override
    public void onStart() {
        prefSuffix = getArguments().getInt(CheckInUtils.ARG_PREF_SUFFIX);
        super.onStart();
    }

    @OnClick(R.id.saveCheckIn)
    protected void save() {

        final String username = MainUtils.getCredentials(getActivity()).username;
        final PatientSvcApi patientService = PatientSvc.getInstance().init(getActivity());
        final SymptomSvcApi symptomSvcApi = SymptomSvc.getInstance().init(getActivity());
        final MedicationSvcApi medicationSvcApi = MedicationSvc.getInstance().init(getActivity());

        //create the symptom times
        final List<SymptomTime> symptomTimes = createSymptomTimes();
        final List<IntakeTime> intakeTimes = createIntakeTimes();

        if (intakeTimes.isEmpty() && symptomTimes.isEmpty()) {
            String message =
                    getActivity().getResources().getString(R.string.check_in_not_possible);
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        } else {
            CallableTask.invoke(new Callable<Patient>() {
                @Override
                public Patient call() throws Exception {
                    return patientService.findByUsername(username);
                }
            }, new TaskCallback<Patient>() {
                @Override
                public void success(final Patient patient) {
                    if (patient == null) {
                        new RuntimeException("Cannot Check-In: Patient " + username + " not found!");
                    }
                    //attach to check-in
                    final CheckIn checkIn = new CheckIn();
                    checkIn.setTimestamp(new GregorianCalendar().getTimeInMillis());
                    checkIn.setIntakeTimes(intakeTimes);
                    checkIn.setSymptomTimes(symptomTimes);
                    patient.getCheckIns().add(checkIn);
                    CallableTask.invoke(new Callable<Patient>() {
                        @Override
                        public Patient call() throws Exception {
                            return patientService.save(patient);
                        }
                    }, new TaskCallback<Patient>() {
                        @Override
                        public void success(Patient patient) {
                            //do nothing
                        }

                        @Override
                        public void error(Exception e) {
                            throw new RuntimeException("Cannot Save Check-In!", e);
                        }
                    });
                }

                @Override
                public void error(Exception e) {
                    throw new RuntimeException("Cannot Check-In: Patient " + username + " not found!", e);
                }
            });
            //reset values entered for check-in and return to MainActivity
            CheckInUtils.resetCheckIn(getActivity(), true);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    protected List<SymptomTime> createSymptomTimes() {
        List<SymptomTime> symptomTimes = new ArrayList<SymptomTime>();
        for (int suffix = 0 ; suffix < 2; suffix++) {
            final DataWrapper data = CheckInUtils.getEditor(getActivity(), CheckInUtils.PREF_SYMPTOM_PREFIX, prefSuffix);
            if (data.selection <= 0) {
                continue;
            }
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
            symptomTimes.add(symptomTime);
        }
        return symptomTimes;
    }

    protected List<IntakeTime> createIntakeTimes() {
        //create the intake times for the medications
        List<IntakeTime> intakeTimes = new ArrayList<IntakeTime>();
        for (int suffix = 0; suffix <= prefSuffix; suffix++) {
            final DataWrapper data = CheckInUtils.getEditor(getActivity(), CheckInUtils.PREF_MEDICATION_PREFIX, suffix);
            if (data.selection == CheckInUtils.PREF_BOOLEAN_FALSE) {
                continue;
            }
            final IntakeTime intakeTime = new IntakeTime();

            final MedicationSvcApi medicationSvcApi = MedicationSvc.getInstance().init(getActivity());
            CallableTask.invoke(new Callable<Medication>() {
                @Override
                public Medication call() throws Exception {
                    return medicationSvcApi.findOne(data.id);
                }
            }, new TaskCallback<Medication>() {
                @Override
                public void success(Medication medication) {
                    intakeTime.setMedicament(medication.getMedicament());
                }

                @Override
                public void error(Exception e) {
                    throw new RuntimeException("Cannot Check-In: Medication with id " + data.id + " not found!", e);
                }
            });
            intakeTime.setTimestamp(data.dateTime);
            intakeTimes.add(intakeTime);
        }
        return intakeTimes;
    }
}
