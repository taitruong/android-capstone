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
import org.aliensource.symptommanagement.android.R;

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
        tabSectionsAdapter = new TabSectionsAdapter(getChildFragmentManager(), titles);
        pager.setAdapter(tabSectionsAdapter);
        tabs.setViewPager(pager);

    }

}
