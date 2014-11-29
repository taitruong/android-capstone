package org.aliensource.symptommanagement.android.doctor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.client.service.CallableTask;
import org.aliensource.symptommanagement.client.service.MedicamentSvc;
import org.aliensource.symptommanagement.client.service.PatientSvc;
import org.aliensource.symptommanagement.client.service.TaskCallback;
import org.aliensource.symptommanagement.cloud.repository.Medicament;
import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.service.MedicamentSvcApi;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

/**
 * Created by ttruong on 27-Nov-14.
 * More to learn on contacts list here: https://developer.android.com/training/contacts-provider/index.html
 * Extending from ListFragment allows to show either ListView or empty TextView. Otherwise both are overlapped.
 */
public class DoctorMedicationFragment extends ListFragment implements AdapterView.OnItemClickListener {

    protected View fragmentView;

    @InjectView(android.R.id.list)
    protected ListView listView;
    private MedicationAdapter medicationAdapter;
    private static final String[] columns = {"overview"};
    private static final int[] to = new int[]{android.R.id.text1};

    private List<Medication> patientMedications = new ArrayList<Medication>();
    private final List<Medicament> allMedicaments = new ArrayList<Medicament>();
    private final List<Long> selectedMedicationsForDelete = new ArrayList<Long>();

    @InjectView(R.id.searchText)
    protected EditText searchText;
    protected String filter = "";

    @InjectView((R.id.add))
    protected Button addButton;

    @InjectView((R.id.delete))
    protected Button deleteButton;

    protected Patient patient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_doctor_medications_list, container, false);
        ButterKnife.inject(this, fragmentView);
        initMedicationAdapter();
        initMedicaments();
        checkDeleteButton();
        return fragmentView;
    }

    @OnClick(R.id.add)
    protected void add() {
        final Activity activity = getActivity();
        final MedicamentSvcApi medicamentSvcApi = MedicamentSvc.getInstance().init(activity);
        final List<Long> medicamentIdList = new ArrayList<Long>();
        final List<String> medicamentNameList = new ArrayList<String>();
        //check whether all medicaments are already in any medication
        for (Medicament medicament: allMedicaments) {
            boolean added = false;
            for (Medication medication: patientMedications) {
                if (medication.getMedicament().getId() == medicament.getId()) {
                    added = true;
                    break;
                }
            }
            if (!added) {
                medicamentIdList.add(medicament.getId());
                medicamentNameList.add(medicament.getName());
            }
        }
        if (medicamentIdList.isEmpty()) {
            Toast message = Toast.makeText(activity, R.string.doctor_medications_all_added, Toast.LENGTH_LONG);
            message.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.select_medicament));

            ListView modeList = new ListView(getActivity());
            String[] stringArray = new String[medicamentIdList.size()];
            medicamentNameList.toArray(stringArray);
            ArrayAdapter<String> modeAdapter =
                    new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            android.R.id.text1,
                            stringArray);
            modeList.setAdapter(modeAdapter);
            final PatientSvcApi patientSvcApi = PatientSvc.getInstance().init(getActivity());

            builder.setView(modeList);
            final Dialog dialog = builder.create();
            modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final long medicamentId = medicamentIdList.get(position);
                    final long patientId = DoctorUtils.getPatientId(activity);
                    CallableTask.invoke(new Callable<Patient>() {
                        @Override
                        public Patient call() throws Exception {
                            return patientSvcApi.addMedicamentForPatient(patientId, medicamentId);
                        }
                    }, new TaskCallback<Patient>() {
                        @Override
                        public void success(Patient data) {
                            initMedicationAdapter();
                            dialog.cancel();
                        }

                        @Override
                        public void error(Exception e) {
                            throw new RuntimeException("Could not add medicament with ID " + medicamentId);
                        }
                    });
                }
            });
            dialog.show();
        }
    }

    @OnClick(R.id.delete)
    protected void delete() {
        final Activity activity = getActivity();
        final PatientSvcApi patientSvcApi = PatientSvc.getInstance().init(activity);
        final long patientId = DoctorUtils.getPatientId(activity);
        for (final long medicationId: selectedMedicationsForDelete) {
            CallableTask.invoke(new Callable<Patient>() {
                @Override
                public Patient call() throws Exception {
                    return patientSvcApi.deleteMedicationForPatient(patientId, medicationId);
                }
            }, new TaskCallback<Patient>() {
                @Override
                public void success(Patient patient) {
                    DoctorMedicationFragment.this.patient = patient;
                    selectedMedicationsForDelete.clear();
                    initMedicationAdapter();
                }

                @Override
                public void error(Exception e) {

                }
            });
        }
    }

    protected void checkDeleteButton() {
        if (selectedMedicationsForDelete.isEmpty()) {
            deleteButton.setEnabled(false);
        } else {
            deleteButton.setEnabled(true);
        }
    }

    @OnTextChanged(R.id.searchText)
    protected void onTextChanged() {
        filter = searchText.getText().toString().toLowerCase();
        //TODO not the best solution to go always to the server, fix that later
        initMedicationAdapter();
    }

    protected void initMedicationAdapter() {
        final Activity activity = getActivity();
        final PatientSvcApi patientSvcApi = PatientSvc.getInstance().init(activity);
        final long patientId = DoctorUtils.getPatientId(activity);
        CallableTask.invoke(new Callable<Patient>() {
            @Override
            public Patient call() throws Exception {
                return patientSvcApi.findOne(patientId);
            }
        }, new TaskCallback<Patient>() {
            @Override
            public void success(Patient patient) {
                DoctorMedicationFragment.this.patient = patient;
                List<String> data = new ArrayList<String>();
                patientMedications.clear();
                for (Medication medication: patient.getMedications()) {
                    String medicamentName = medication.getMedicament().getName();
                    if (TextUtils.isEmpty(filter) || medicamentName.toLowerCase().contains(filter)) {
                        data.add(medicamentName);
                        patientMedications.add(medication);
                    }
                }
                String[] dataArray = new String[data.size()];
                data.toArray(dataArray);
                medicationAdapter = new MedicationAdapter(
                        activity,
                        R.layout.fragment_doctor_medication_list_item,
                        dataArray,
                        DoctorMedicationFragment.this);
                listView.setAdapter(medicationAdapter);
                //set listener because it seems butterknife's listener is overridden after the adapter is attached
                listView.setOnItemClickListener(DoctorMedicationFragment.this);
            }

            @Override
            public void error(Exception e) {

            }
        });
    }

    protected void initMedicaments() {
        final Activity activity = getActivity();
        final MedicamentSvcApi medicamentSvcApi = MedicamentSvc.getInstance().init(activity);
        CallableTask.invoke(new Callable<List<Medicament>>() {
            @Override
            public List<Medicament> call() throws Exception {
                return medicamentSvcApi.findAll();
            }
        }, new TaskCallback<List<Medicament>>() {
            @Override
            public void success(List<Medicament> data) {
                //check whether all medicaments are already in any medication
                for (Medicament medicament: data) {
                    allMedicaments.add(medicament);
                }
            }

            @Override
            public void error(Exception e) {

            }
        });
    }

    @OnItemClick(android.R.id.list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        updateSelection(position);
    }

    protected void updateSelection(int position) {
        Long medicationId = patientMedications.get(position).getId();
        if (medicationAdapter.invertCheckBox(position)) {
            selectedMedicationsForDelete.add(medicationId);
        } else {
            selectedMedicationsForDelete.remove(medicationId);
        }
        checkDeleteButton();
    }
}