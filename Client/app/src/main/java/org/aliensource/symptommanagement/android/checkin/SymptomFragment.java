package org.aliensource.symptommanagement.android.checkin;

import android.content.SharedPreferences;
import android.widget.RadioButton;

import org.aliensource.symptommanagement.android.R;

import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ttruong on 19-Nov-14.
 */
public class SymptomFragment extends BaseCheckInFragment {

    @InjectView(R.id.choice1)
    protected RadioButton choice1;
    @InjectView(R.id.choice2)
    protected RadioButton choice2;
    @InjectView(R.id.choice3)
    protected RadioButton choice3;

    protected String getPrefPrefix() {
        return CheckInUtils.PREF_SYMPTOM_PREFIX;
    }

    protected void updateSelection(int selection) {
        boolean selected = false;
        if (selection == 0) {
            choice1.setChecked(true);
            selected = true;
        } else if (selection == 1) {
            choice2.setChecked(true);
            selected = true;
        } else if (selection == 2) {
            choice3.setChecked(true);
            selected = true;
        }
        if (selected) {
            enableDateAndTime();
        }
    }

    @OnClick(value = {R.id.choice1, R.id.choice2, R.id.choice3})
    protected void updateSeverity() {
        enableDateAndTime();
        if (choice1.isChecked()) {
            saveSelection(0);
        } else if (choice2.isChecked()) {
            saveSelection(1);
        } else if (choice3.isChecked()) {
            saveSelection(2);
        }
    }

}