package org.aliensource.symptommanagement.android.reminder;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import org.aliensource.symptommanagement.DateTimeUtils;
import org.aliensource.symptommanagement.android.AbstractFragment;
import org.aliensource.symptommanagement.android.MainActivity;
import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.MainUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ttruong on 06-Nov-14.
 */
public class ReminderSettingsFragment extends AbstractFragment {

    @InjectView(R.id.reminderTime1)
    protected TextView reminder1;

    @InjectView(R.id.reminderTime2)
    protected TextView reminder2;

    @InjectView(R.id.reminderTime3)
    protected TextView reminder3;

    @InjectView(R.id.reminderTime4)
    protected TextView reminder4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        updateReminderTextViews(ReminderPreferencesUtils.getReminderAlarms(getActivity()));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected void updateReminderTextViews(Set<String> reminderAlarms) {

        //since the set is unsorted we needed to sort it by the time (of type long):
        //1. convert a list of reminder text into a list of reminder of type long
        //2. then sort this list
        ArrayList<Long> timeList = new ArrayList<Long>();
        Iterator<String> iterator = reminderAlarms.iterator();

        int[] hourAndMinute1 = DateTimeUtils.getHourAndMinute(iterator.next());
        long time1 = DateTimeUtils.getTime(hourAndMinute1[0], hourAndMinute1[1]);
        timeList.add(new Long(time1));

        int[] hourAndMinute2 = DateTimeUtils.getHourAndMinute(iterator.next());
        long time2 = DateTimeUtils.getTime(hourAndMinute2[0], hourAndMinute2[1]);
        timeList.add(new Long(time2));

        int[] hourAndMinute3 = DateTimeUtils.getHourAndMinute(iterator.next());
        long time3 = DateTimeUtils.getTime(hourAndMinute3[0], hourAndMinute3[1]);
        timeList.add(new Long(time3));

        int[] hourAndMinute4 = DateTimeUtils.getHourAndMinute(iterator.next());
        long time4 = DateTimeUtils.getTime(hourAndMinute4[0], hourAndMinute4[1]);
        timeList.add(new Long(time4));

        Collections.sort(timeList);
        //now add the sorted back to the Set
        reminderAlarms = new LinkedHashSet<String>();
        Iterator<Long> timeIterator = timeList.iterator();
        time1 = timeIterator.next();
        reminderAlarms.add(DateTimeUtils.getTimeAsString(time1).toString());

        time2 = timeIterator.next();
        reminderAlarms.add(DateTimeUtils.getTimeAsString(time2).toString());

        time3 = timeIterator.next();
        reminderAlarms.add(DateTimeUtils.getTimeAsString(time3).toString());

        time4 = timeIterator.next();
        reminderAlarms.add(DateTimeUtils.getTimeAsString(time4).toString());

        iterator = reminderAlarms.iterator();
        reminder1.setText(iterator.next());
        reminder2.setText(iterator.next());
        reminder3.setText(iterator.next());
        reminder4.setText(iterator.next());
    }

    @OnClick(R.id.reminderTime1)
    protected void listenerReminder1() {
        editReminder1(R.id.reminderTime1);
    }

    @OnClick(R.id.reminderTime2)
    protected void listenerReminder2() {
        editReminder1(R.id.reminderTime2);
    }

    @OnClick(R.id.reminderTime3)
    protected void listenerReminder3() {
        editReminder1(R.id.reminderTime3);
    }

    @OnClick(R.id.reminderTime4)
    protected void listenerReminder4() {
        editReminder1(R.id.reminderTime4);
    }

    protected void editReminder1(int reminderId) {
        // Create a new DatePickerFragment
        DialogFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putInt(MainUtils.ARG_TIME, reminderId);
        fragment.setArguments(args);
        fragment.setTargetFragment(this, 0);

        // Display DatePickerFragment
        fragment.show(getFragmentManager(), "TimePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        private TextView timeView;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            int id = getArguments().getInt(MainUtils.ARG_TIME);
            timeView = (TextView) getActivity().findViewById(id);

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
            ((ReminderSettingsFragment) getTargetFragment()).saveReminderPreferences(oldTime, newTime);

        }

    }

    private void saveReminderPreferences(String oldTime, String newTime) {
        Set<String> reminderAlarms = ReminderPreferencesUtils.getReminderAlarms(getActivity());
        //save only when new time is not already set
        if (!reminderAlarms.contains(newTime)) {
            reminderAlarms.remove(oldTime);
            reminderAlarms.add(newTime);

            ReminderPreferencesUtils.saveReminderAlarms(getActivity(), reminderAlarms);
            updateReminderTextViews(reminderAlarms);
            ((MainActivity) getActivity()).initAlarms();
        }

    }

}
