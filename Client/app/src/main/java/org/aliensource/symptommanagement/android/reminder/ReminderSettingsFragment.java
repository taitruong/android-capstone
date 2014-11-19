package org.aliensource.symptommanagement.android.reminder;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import org.aliensource.symptommanagement.android.AbstractFragment;
import org.aliensource.symptommanagement.android.MainActivity;
import org.aliensource.symptommanagement.android.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ttruong on 06-Nov-14.
 */
public class ReminderSettingsFragment extends AbstractFragment {

    private static final String ARG_TEXT_VIEW = "text_view";

    @InjectView(R.id.reminderTime1)
    protected TextView reminder1;

    @InjectView(R.id.reminderTime2)
    protected TextView reminder2;

    @InjectView(R.id.reminderTime3)
    protected TextView reminder3;

    @InjectView(R.id.reminderTime4)
    protected TextView reminder4;

    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, view);

        prefs = ReminderPreferencesUtils.getPreferences(getActivity());
        updateReminderTextViews(ReminderPreferencesUtils.getReminderAlarms(prefs));
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

        int[] hourAndMinute1 = ReminderPreferencesUtils.getHourAndMinute(iterator.next());
        long time1 = ReminderPreferencesUtils.getTime(hourAndMinute1[0], hourAndMinute1[1]);
        timeList.add(new Long(time1));

        int[] hourAndMinute2 = ReminderPreferencesUtils.getHourAndMinute(iterator.next());
        long time2 = ReminderPreferencesUtils.getTime(hourAndMinute2[0], hourAndMinute2[1]);
        timeList.add(new Long(time2));

        int[] hourAndMinute3 = ReminderPreferencesUtils.getHourAndMinute(iterator.next());
        long time3 = ReminderPreferencesUtils.getTime(hourAndMinute3[0], hourAndMinute3[1]);
        timeList.add(new Long(time3));

        int[] hourAndMinute4 = ReminderPreferencesUtils.getHourAndMinute(iterator.next());
        long time4 = ReminderPreferencesUtils.getTime(hourAndMinute4[0], hourAndMinute4[1]);
        timeList.add(new Long(time4));

        Collections.sort(timeList);
        //now add the sorted back to the Set
        reminderAlarms = new LinkedHashSet<String>();
        Iterator<Long> timeIterator = timeList.iterator();
        time1 = timeIterator.next();
        reminderAlarms.add(ReminderPreferencesUtils.getTimeAsString(time1).toString());

        time2 = timeIterator.next();
        reminderAlarms.add(ReminderPreferencesUtils.getTimeAsString(time2).toString());

        time3 = timeIterator.next();
        reminderAlarms.add(ReminderPreferencesUtils.getTimeAsString(time3).toString());

        time4 = timeIterator.next();
        reminderAlarms.add(ReminderPreferencesUtils.getTimeAsString(time4).toString());

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
        args.putInt(ARG_TEXT_VIEW, reminderId);
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

            int id = getArguments().getInt(ARG_TEXT_VIEW);
            timeView = (TextView) getActivity().findViewById(id);

            int[] hourAndMinute = ReminderPreferencesUtils.getHourAndMinute(timeView.getText());

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(
                    getActivity(),
                    this,
                    hourAndMinute[0],
                    hourAndMinute[1],
                    false);

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
            String newTime = ReminderPreferencesUtils.getTimeAsString(hourOfDay, minute).toString();
            ((ReminderSettingsFragment) getTargetFragment()).saveReminderPreferences(oldTime, newTime);

        }

    }

    private void saveReminderPreferences(String oldTime, String newTime) {
        Set<String> reminderAlarms = ReminderPreferencesUtils.getReminderAlarms(prefs);
        //save only when new time is not already set
        if (!reminderAlarms.contains(newTime)) {
            reminderAlarms.remove(oldTime);
            reminderAlarms.add(newTime);

            ReminderPreferencesUtils.saveReminderPreferences(prefs, reminderAlarms);
            updateReminderTextViews(reminderAlarms);
            ((MainActivity) getActivity()).initAlarms();
        }

    }

}
