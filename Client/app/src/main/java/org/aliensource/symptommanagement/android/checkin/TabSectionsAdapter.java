package org.aliensource.symptommanagement.android.checkin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Created by ttruong on 19-Nov-14.
 */
public class TabSectionsAdapter extends FragmentPagerAdapter {
    public TabSectionsAdapter(FragmentManager fm) {
        super(fm);
    }

    private final String[] TITLES = { "Symptom", "Medication"};

    @Override
    public SherlockFragment getItem(int i) {
        Bundle args = new Bundle();
        switch (i) {
            case 0:
                SymptomFragment symptomFragment = new SymptomFragment();
                symptomFragment.setArguments(args);
                return symptomFragment;
            default:
                MedicationFragment medicationFragment = new MedicationFragment();
                medicationFragment.setArguments(args);
                return medicationFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

}
