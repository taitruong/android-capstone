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

    protected boolean taken;

    @OnClick(value = {R.id.choice1, R.id.choice2})
    protected void updateSeverity() {
        enableDateAndTime();
        if (choice1.isChecked()) {
            taken = true;
        } else if (choice2.isChecked()) {
            taken = false;
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