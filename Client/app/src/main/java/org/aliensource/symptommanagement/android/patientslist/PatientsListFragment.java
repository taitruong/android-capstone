package org.aliensource.symptommanagement.android.patientslist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.main.MainUtils;
import org.aliensource.symptommanagement.client.service.CallableTask;
import org.aliensource.symptommanagement.client.service.PatientSvc;
import org.aliensource.symptommanagement.client.service.TaskCallback;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

/**
 * Created by ttruong on 27-Nov-14.
 * More to learn on contacts list here: https://developer.android.com/training/contacts-provider/index.html
 * Extending from ListFragment allows to show either ListView or empty TextView. Otherwise both are overlapped.
 */
public class PatientsListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    protected View fragmentView;

    @InjectView(android.R.id.list)
    protected ListView patientsList;
    private SimpleAdapter patientAdapter;
    private static final String[] columns = {"overview", "details"};
    private static final int[] to = new int[]{android.R.id.text1, android.R.id.text2};

    private List<Long> patientIds = new ArrayList<Long>();

    @InjectView(R.id.searchText)
    protected EditText searchText;
    protected String filter = "";

    // Contact selected listener that allows the activity holding this fragment to be notified of
    // a contact being selected
    private OnPatientsInteractionListener mOnPatientsInteractionListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_patients_list, container, false);
        ButterKnife.inject(this, fragmentView);
        initAdapter();
        return fragmentView;
    }

    @OnTextChanged(R.id.searchText)
    protected void onTextChanged() {
        filter = searchText.getText().toString();
        //TODO not the best solution to go always to the server, fix that later
        initAdapter();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            // Assign callback listener which the holding activity must implement. This is used
            // so that when a contact item is interacted with (selected by the user) the holding
            // activity will be notified and can take further action such as populating the contact
            // detail pane (if in multi-pane layout) or starting a new activity with the contact
            // details (single pane layout).
            mOnPatientsInteractionListener = (OnPatientsInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnContactsInteractionListener");
        }
    }

    protected void initAdapter() {
        final Activity activity = getActivity();
        final PatientSvcApi patientSvcApi = PatientSvc.getInstance().init(activity);
        final String username = MainUtils.getCredentials(activity).username;
        final String filter = this.filter;
        CallableTask.invoke(new Callable<List<Patient>>() {
            @Override
            public List<Patient> call() throws Exception {
                return patientSvcApi.findByDoctorUsernameAndFilter(username, filter);
            }
        }, new TaskCallback<List<Patient>>() {
            @Override
            public void success(List<Patient> patients) {
                List<Map<String, String>> data = new ArrayList<Map<String, String>>();
                patientIds.clear();
                patientAdapter = new SimpleAdapter(activity, data, R.layout.patient_list_item, columns, to);
                for (Patient patient : patients) {
                    Map<String, String> row = new HashMap<String, String>();
                    row.put(columns[0], patient.getFirstName() + " " + patient.getLastName());
                    row.put(columns[1], patient.getMedicalRecordNumber());
                    data.add(row);
                    patientIds.add(patient.getId());
                }
                patientsList.setAdapter(patientAdapter);
                //set listener because it seems butterknife's listener is overridden after the adapter is attached
                patientsList.setOnItemClickListener(PatientsListFragment.this);
            }

            @Override
            public void error(Exception e) {

            }
        });
    }

    @OnItemClick(android.R.id.list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(">>>> " + patientIds.get(position));
        mOnPatientsInteractionListener.onPatientSelected(patientIds.get(position));
    }
}
