package org.aliensource.symptommanagement.android.checkin;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.actionbarsherlock.app.SherlockFragment;

import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.main.MainUtils;
import org.aliensource.symptommanagement.cloud.repository.Medication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttruong on 19-Nov-14.
 */
public class TabSectionsAdapter extends FragmentPagerAdapter {

    private List<Medication> medications;
    private List<String> titles = new ArrayList<String>();

    public TabSectionsAdapter(FragmentManager fm, Activity activity, List<Medication> medications) {
        super(fm);
        this.medications = medications;
        titles.add(activity.getString(R.string.check_in_symptom1_tab_title));
        titles.add(activity.getString(R.string.check_in_symptom2_tab_title));
        for (Medication medication: medications) {
            titles.add(medication.getMedicament().getName());
        }
        titles.add(activity.getString(R.string.finish_check_in));
    }

    @Override
    public SherlockFragment getItem(int i) {
        Bundle args = new Bundle();
        if (i == titles.size() - 1) {
            SaveFragment fragment = new SaveFragment();
            args.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_check_in_save);
            //use the medication size as key suffix to get all medication data
            args.putInt(CheckInUtils.ARG_PREF_SUFFIX, medications.size() - 1);
            fragment.setArguments(args);
            return fragment;
        }
        switch (i) {
            case 0:
                SymptomFragment symptomFragment1 = new SymptomFragment();
                args.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_check_in_symptom1);
                args.putInt(CheckInUtils.ARG_PREF_SUFFIX, i);
                symptomFragment1.setArguments(args);
                return symptomFragment1;
            case 1:
                SymptomFragment symptomFragment2 = new SymptomFragment();
                args.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_check_in_symptom2);
                args.putInt(CheckInUtils.ARG_PREF_SUFFIX, i);
                symptomFragment2.setArguments(args);
                return symptomFragment2;
            default:
                MedicationFragment medicationFragment = new MedicationFragment();
                args.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_check_in_medication);
                args.putSerializable(MedicationFragment.ARG_MEDICATION, medications.get(i - 2));
                args.putInt(CheckInUtils.ARG_PREF_SUFFIX, i - 2);
                medicationFragment.setArguments(args);
                return medicationFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

}
