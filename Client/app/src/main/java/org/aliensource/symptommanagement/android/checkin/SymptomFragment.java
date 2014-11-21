package org.aliensource.symptommanagement.android.checkin;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.actionbarsherlock.app.SherlockFragment;

import org.aliensource.symptommanagement.android.AbstractFragment;
import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.ViewUtils;
import org.aliensource.symptommanagement.android.reminder.ReminderPreferencesUtils;

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

    protected int severityLevel = -1;

    @OnClick(value = {R.id.choice1, R.id.choice2, R.id.choice3})
    protected void updateSeverity() {
        enableDateAndTime();
        if (choice1.isChecked()) {
            severityLevel = 0;
        } else if (choice2.isChecked()) {
            severityLevel = 1;
        } else if (choice3.isChecked()) {
            severityLevel = 2;
        }
    }

}