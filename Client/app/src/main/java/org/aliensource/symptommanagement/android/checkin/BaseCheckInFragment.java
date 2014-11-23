package org.aliensource.symptommanagement.android.checkin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import org.aliensource.symptommanagement.DateTimeUtils;
import org.aliensource.symptommanagement.android.AbstractFragment;
import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.reminder.ReminderPreferencesUtils;

import java.text.ParseException;
import java.util.Calendar;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ttruong on 19-Nov-14.
 */
public class BaseCheckInFragment extends AbstractFragment<View> {

    @InjectView(R.id.date_time_title)
    protected TextView dateTimeTitle;
    @InjectView(R.id.date)
    protected TextView date;
    @InjectView(R.id.time)
    protected TextView time;

    protected int severityLevel = -1;

    protected void enableDateAndTime() {
        dateTimeTitle.setVisibility(View.VISIBLE);
        date.setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.time)
    protected void showTimeDialog() {
        // Create a new DatePickerFragment
        DialogFragment fragment = new TimePickerFragment();
        fragment.setTargetFragment(this, 0);

        // Display DatePickerFragment
        fragment.show(getFragmentManager(), "TimePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        private TextView timeView;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            timeView = (TextView) getActivity().findViewById(R.id.time);

            int[] hourAndMinute = DateTimeUtils.getHourAndMinute(timeView.getText());

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(
                    getActivity(),
                    this,
                    hourAndMinute[0],
                    hourAndMinute[1],
                    true);

        }

        /** Update the time String in the TextView
         *
         * @param view
         * @param hourOfDay
         * @param minute
         */
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //save changes into preferences
            String oldTime = timeView.getText().toString();
            String newTime = DateTimeUtils.getTimeAsString(hourOfDay, minute).toString();
            timeView.setText(newTime);
        }

    }

    @OnClick(R.id.date)
    protected void showDateDialog() {
        // Create a new DatePickerFragment
        DialogFragment fragment = new DatePickerFragment();
        fragment.setTargetFragment(this, 0);

        // Display DatePickerFragment
        fragment.show(getFragmentManager(), "TimePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener{

        private TextView dateView;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            dateView = (TextView) getActivity().findViewById(R.id.date);

            try {
                Calendar cal = DateTimeUtils.getDate(dateView.getText().toString());

                // Create a new instance of TimePickerDialog and return it
                return new DatePickerDialog(
                        getActivity(),
                        this,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
            //save changes into preferences
            String oldTime = dateView.getText().toString();

            try {
                String newTime = DateTimeUtils.getDate(year, month, dayOfMonth);
                dateView.setText(newTime);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}