package org.aliensource.symptommanagement.android.doctor;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import org.aliensource.symptommanagement.android.AbstractFragment;
import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.checkin.CheckInUtils;
import org.aliensource.symptommanagement.android.main.MainUtils;
import org.aliensource.symptommanagement.client.service.CallableTask;
import org.aliensource.symptommanagement.client.service.PatientSvc;
import org.aliensource.symptommanagement.client.service.TaskCallback;
import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.InjectView;

/**
 * Created by ttruong on 07-Nov-14.
 */
public class DoctorFragment extends AbstractFragment<ViewPager> {

    @InjectView(R.id.tabs)
    protected PagerSlidingTabStrip tabs;

    @InjectView(R.id.pager)
    protected ViewPager pager;

    private DoctorTabsAdapter tabsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        final String username = MainUtils.getCredentials(getActivity()).username;
        final PatientSvcApi patientService = PatientSvc.getInstance().init(getActivity());
        final long patientId = DoctorUtils.getPatientId(getActivity());

        CallableTask.invoke(new Callable<Patient>() {
            @Override
            public Patient call() throws Exception {
                return patientService.findOne(patientId);
            }
        }, new TaskCallback<Patient>() {
            @Override
            public void success(Patient patient) {
                List<Medication> medications = new ArrayList<Medication>();
                medications.addAll(patient.getMedications());
                tabsAdapter = new DoctorTabsAdapter(getChildFragmentManager(), getActivity(), medications);
                pager.setAdapter(tabsAdapter);
                tabs.setViewPager(pager);
            }

            @Override
            public void error(Exception e) {
                throw new RuntimeException("Patient " + username + " not found!", e);
            }
        });
        String date = getArguments().getString(CheckInUtils.PREF_DATE);
        String time = getArguments().getString(CheckInUtils.PREF_TIME);
        String title = getResources().getString(R.string.check_in_title, date, time);
        getActivity().setTitle(title);
    }

}
