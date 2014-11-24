package org.aliensource.symptommanagement.android.checkin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.cloud.repository.Medication;

import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ttruong on 19-Nov-14.
 */
public class MedicationFragment extends BaseCheckInFragment {

    public static final String ARG_MEDICATION = "medication";

    @InjectView(R.id.choice1)
    protected RadioButton choice1;
    @InjectView(R.id.choice2)
    protected RadioButton choice2;
    @InjectView(R.id.title)
    protected TextView title;

    protected String getPrefPrefix() {
        return CheckInUtils.PREF_MEDICATION_PREFIX;
    }

    protected void initSelection(int selection) {
        boolean selected = false;
        if (selection == 0) {
            choice1.setChecked(true);
            selected = true;
        } else if (selection == 1) {
            choice2.setChecked(true);
            selected = true;
        }
        if (selected) {
            enableDateAndTime();
        }
    }

    @OnClick(value = {R.id.choice1, R.id.choice2})
    protected void updateSeverity() {
        enableDateAndTime();
        if (choice1.isChecked()) {
            saveSelection(0);
        } else if (choice2.isChecked()) {
            saveSelection(1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Medication medication = (Medication) getArguments().getSerializable(ARG_MEDICATION);

        String titleText = getResources().getString(
                R.string.check_in_medication_title,
                medication.getMedicament().getName());
        title.setText(titleText);
        return view;
    }
}