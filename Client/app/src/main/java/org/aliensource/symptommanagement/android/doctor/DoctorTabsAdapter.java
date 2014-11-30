package org.aliensource.symptommanagement.android.doctor;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.checkin.CheckInUtils;
import org.aliensource.symptommanagement.android.checkin.SymptomFragment;
import org.aliensource.symptommanagement.android.main.MainUtils;
import org.aliensource.symptommanagement.android.report.PatientReportFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttruong on 19-Nov-14.
 */
public class DoctorTabsAdapter extends FragmentPagerAdapter {

    private List<String> titles = new ArrayList<String>();

    public DoctorTabsAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        titles.add(activity.getString(R.string.doctor_medications_tab_title));
        titles.add(activity.getString(R.string.sore_throat));
        titles.add(activity.getString(R.string.eat_drink));
        titles.add("Lortab");
        titles.add("OxyContin");
    }

    @Override
    public Fragment getItem(int i) {
        Bundle args = new Bundle();
        switch (i) {
            case 0:
                Fragment fragment = new DoctorMedicationFragment();
                args.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_doctor_medications_list);
                args.putInt(CheckInUtils.ARG_PREF_SUFFIX, i);
                fragment.setArguments(args);
                return fragment;
            case 1:
                fragment = new PatientReportFragment();
                args.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_patient_report);
                args.putInt(CheckInUtils.ARG_PREF_SUFFIX, i);
                fragment.setArguments(args);
                return fragment;
            case 2:
                fragment = new PatientReportFragment();
                args.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_patient_report);
                args.putInt(CheckInUtils.ARG_PREF_SUFFIX, i);
                fragment.setArguments(args);
                return fragment;
            case 3:
                SymptomFragment symptomFragment2 = new SymptomFragment();
                args.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_check_in_symptom2);
                args.putInt(CheckInUtils.ARG_PREF_SUFFIX, i);
                symptomFragment2.setArguments(args);
                return symptomFragment2;
            default:
                symptomFragment2 = new SymptomFragment();
                args.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_check_in_symptom2);
                args.putInt(CheckInUtils.ARG_PREF_SUFFIX, i);
                symptomFragment2.setArguments(args);
                return symptomFragment2;
        }
    }

    protected boolean isSaveFragment(int pos) {
        return pos == titles.size() - 1;
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
