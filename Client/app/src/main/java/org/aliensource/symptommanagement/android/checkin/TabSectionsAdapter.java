package org.aliensource.symptommanagement.android.checkin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.actionbarsherlock.app.SherlockFragment;

import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.ViewUtils;

/**
 * Created by ttruong on 19-Nov-14.
 */
public class TabSectionsAdapter extends FragmentPagerAdapter {
    private String[] titles;

    public TabSectionsAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public SherlockFragment getItem(int i) {
        Bundle args = new Bundle();
        switch (i) {
            case 0:
                SymptomFragment symptomFragment1 = new SymptomFragment();
                args.putInt(ViewUtils.ARG_LAYOUT, R.layout.fragment_check_in_symptom1);
                symptomFragment1.setArguments(args);
                return symptomFragment1;
            case 1:
                SymptomFragment symptomFragment2 = new SymptomFragment();
                args.putInt(ViewUtils.ARG_LAYOUT, R.layout.fragment_check_in_symptom2);
                symptomFragment2.setArguments(args);
                return symptomFragment2;
            default:
                MedicationFragment medicationFragment = new MedicationFragment();
                args.putInt(ViewUtils.ARG_LAYOUT, R.layout.fragment_check_in_medication);
                medicationFragment.setArguments(args);
                return medicationFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

}
