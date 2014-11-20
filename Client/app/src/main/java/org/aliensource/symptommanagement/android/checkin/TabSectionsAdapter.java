package org.aliensource.symptommanagement.android.checkin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ttruong on 19-Nov-14.
 */
public class TabSectionsAdapter extends FragmentPagerAdapter {
    public TabSectionsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
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
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Section " + (position + 1);
    }
}
