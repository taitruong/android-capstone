package org.aliensource.symptommanagement.android.checkin;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import org.aliensource.symptommanagement.android.AbstractFragment;
import org.aliensource.symptommanagement.android.R;
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
public class CheckInFragment extends AbstractFragment<ViewPager> {

    @InjectView(R.id.tabs)
    protected PagerSlidingTabStrip tabs;

    @InjectView(R.id.pager)
    protected ViewPager pager;

    private TabSectionsAdapter tabSectionsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        final String username = MainUtils.getCredentials(getActivity())[1];
        final PatientSvcApi patientService = PatientSvc.getInstance().init(getActivity());

        CallableTask.invoke(new Callable<List<Patient>>() {
            @Override
            public List<Patient> call() throws Exception {
                return patientService.findByUsername(username);
            }
        }, new TaskCallback<List<Patient>>() {
            @Override
            public void success(List<Patient> result) {
                List<Medication> medications = new ArrayList<Medication>();
                for (Patient patient : result) {
                    medications.addAll(patient.getMedications());
                }
                tabSectionsAdapter = new TabSectionsAdapter(getChildFragmentManager(), getActivity(), medications);
                pager.setAdapter(tabSectionsAdapter);
                tabs.setViewPager(pager);
            }

            @Override
            public void error(Exception e) {
                throw new RuntimeException("Patient " + username + " not found!");
            }
        });
        String date = getArguments().getString(CheckInUtils.PREF_DATE);
        String time = getArguments().getString(CheckInUtils.PREF_TIME);
        String title = getResources().getString(R.string.check_in_title, date, time);
        getActivity().setTitle(title);
    }

}
