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
    protected int prefMedicationSuffix;

    @Override
    public void onStart() {
        prefMedicationSuffix = getArguments().getInt(CheckInUtils.ARG_PREF_SUFFIX);
        super.onStart();
    }

    @OnClick(R.id.saveCheckIn)
    protected void save() {
            final SymptomSvcApi api = SymptomSvc.getInstance().init(getActivity());
            CallableTask.invoke(new Callable<List<SymptomTime>>() {
                @Override
                public List<SymptomTime> call() throws Exception {
                    List<SymptomTime> symptomTimes = new ArrayList<SymptomTime>();
                    for (int suffix = 0; suffix < 2; suffix++) {
                        final DataWrapper data = CheckInUtils.getEditor(getActivity(), CheckInUtils.PREF_SYMPTOM_PREFIX, suffix);
                        // create only when something has selected, including
                        // severity level 0 (Well-Controlled: sore/throat/ No: eat/drink)
                        if (data.selection < 0) {
                            continue;
                        }
                        //set reference to symptom
                        final SymptomTime symptomTime = new SymptomTime();
                        Symptom symptom = api.findOne(data.id);
                        symptomTime.setSymptom(symptom);
                        //set timestamp
                        symptomTime.setTimestamp(data.dateTime);
                        symptomTime.setSeverity(data.selection);
                        symptomTimes.add(symptomTime);
                    }
                    return symptomTimes;
                }
            }, new TaskCallback<List<SymptomTime>>() {
                @Override
                public void success(final List<SymptomTime> symptomTimes) {
                    //create the intake times for the medications
                    final MedicationSvcApi medicationSvcApi = MedicationSvc.getInstance().init(getActivity());
                    CallableTask.invoke(new Callable<List<IntakeTime>>() {
                        @Override
                        public List<IntakeTime> call() throws Exception {
                            List<IntakeTime> intakeTimes = new ArrayList<IntakeTime>();
                            for (int suffix = 0; suffix <= prefMedicationSuffix; suffix++) {
                                final DataWrapper data = CheckInUtils.getEditor(getActivity(), CheckInUtils.PREF_MEDICATION_PREFIX, suffix);
                                //create only medication when selection is 'Yes'
                                if (data.selection == CheckInUtils.PREF_BOOLEAN_FALSE) {
                                    continue;
                                }
                                final IntakeTime intakeTime = new IntakeTime();
                                Medication medication = medicationSvcApi.findOne(data.id);
                                intakeTime.setMedicament(medication.getMedicament());
                                intakeTime.setTimestamp(data.dateTime);
                                intakeTimes.add(intakeTime);
                            }
                            return intakeTimes;
                        }
                    }, new TaskCallback<List<IntakeTime>>() {
                        @Override
                        public void success(final List<IntakeTime> intakeTimes) {
                            if (intakeTimes.isEmpty() && symptomTimes.isEmpty()) {
                                String message =
                                        getActivity().getResources().getString(R.string.check_in_not_possible);
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            } else {
                                final String username = MainUtils.getCredentials(getActivity()).username;
                                final PatientSvcApi patientService = PatientSvc.getInstance().init(getActivity());
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
                                        checkIn.setPatientId(patient.getId());
                                        checkIn.setTimestamp(new GregorianCalendar().getTimeInMillis());
                                        //TODO since symptom and medicament references in intake and symptom times are
                                        //retrieved and set in a different background thread there is rare chance it might be null here
                                        //fix this later...
                                        checkIn.setIntakeTimes(intakeTimes);
                                        checkIn.setSymptomTimes(symptomTimes);
                                        CallableTask.invoke(new Callable<CheckIn>() {
                                            @Override
                                            public CheckIn call() throws Exception {
                                                return patientService.addCheckIn(patient.getId(), checkIn);
                                            }
                                        }, new TaskCallback<CheckIn>() {
                                            @Override
                                            public void success(CheckIn checkIn) {
                                                //reset values entered for check-in and return to MainActivity
                                                CheckInUtils.resetCheckIn(getActivity(), true);
                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                startActivity(intent);
                                                //show toast messages
                                                for (SymptomTime symptomTime: symptomTimes) {
                                                    String message;
                                                    if (ServiceUtils.SYMPTOM_TYPE_SORE_THROAT.equals(
                                                            symptomTime.getSymptom().getType())) {
                                                        String severity;
                                                        switch (symptomTime.getSeverity()) {
                                                            case 0:
                                                                severity = getString(R.string.check_in_symptom1_value1);
                                                                break;
                                                            case 1:
                                                                severity = getString(R.string.check_in_symptom1_value2);
                                                                break;
                                                            default:
                                                                severity = getString(R.string.check_in_symptom1_value3);
                                                        }
                                                        String dateTime = DateTimeUtils.FORMAT_HHMM.format(
                                                                new Date(symptomTime.getTimestamp()));
                                                        message = getString(
                                                                R.string.check_in_create_symptom_sore_throat,
                                                                severity, dateTime);
                                                    } else {
                                                        String severity;
                                                        switch (symptomTime.getSeverity()) {
                                                            case 0:
                                                                severity = getString(R.string.check_in_symptom2_value1);
                                                                break;
                                                            case 1:
                                                                severity = getString(R.string.check_in_symptom2_value2);
                                                                break;
                                                            default:
                                                                severity = getString(R.string.check_in_symptom2_value3);
                                                        }
                                                        String dateTime = DateTimeUtils.FORMAT_HHMM.format(
                                                                new Date(symptomTime.getTimestamp()));
                                                        message = getString(
                                                                R.string.check_in_create_symptom_eat_drink,
                                                                severity, dateTime);
                                                    }
                                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                                }
                                                for (IntakeTime intakeTime: intakeTimes) {
                                                    String dateTime = DateTimeUtils.FORMAT_HHMM.format(
                                                            new Date(intakeTime.getTimestamp()));
                                                    String message = getString(
                                                            R.string.check_in_create_medication,
                                                            intakeTime.getMedicament().getName(), dateTime);
                                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                                }
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
                            }

                        }

                        @Override
                        public void error(Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                @Override
                public void error(Exception e) {
                    throw new RuntimeException(e);
                }
            });
    }

}
