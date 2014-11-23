package org.aliensource.symptommanagement.android.checkin;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import org.aliensource.symptommanagement.android.AbstractFragment;
import org.aliensource.symptommanagement.android.CallableTask;
import org.aliensource.symptommanagement.android.PatientSvc;
import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.TaskCallback;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.dto.PatientDTO;
import org.aliensource.symptommanagement.cloud.repository.dto.SpringDataRestDTO;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;

import java.util.Collection;
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
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] titles = {
                getString(R.string.check_in_symptom1_tab_title),
                getString(R.string.check_in_symptom2_tab_title),
                "Medication"};
        final PatientSvcApi patientService = PatientSvc.init(getActivity());

        CallableTask.invoke(new Callable<SpringDataRestDTO<PatientDTO>>() {
            @Override
            public SpringDataRestDTO<PatientDTO> call() throws Exception {
                return patientService.findAll();
            }
        }, new TaskCallback<SpringDataRestDTO<PatientDTO>>() {
            @Override
            public void success(SpringDataRestDTO<PatientDTO> result) {
            }

            @Override
            public void error(Exception e) {

            }
        });

        tabSectionsAdapter = new TabSectionsAdapter(getChildFragmentManager(), titles);
        pager.setAdapter(tabSectionsAdapter);
        tabs.setViewPager(pager);

    }

}
