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

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ttruong on 19-Nov-14.
 */
public class MedicationFragment extends BaseCheckInFragment {

    @InjectView(R.id.choice1)
    protected RadioButton choice1;
    @InjectView(R.id.choice2)
    protected RadioButton choice2;

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

}