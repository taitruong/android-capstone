package org.aliensource.symptommanagement.android;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ttruong on 06-Nov-14.
 */
public class ReminderSettingsFragment extends AbstractFragment {

    private static final String REMINDERS = "reminders";
    private static final Set<String> DEFAULT_REMINDERS;
    private static final String ARG_TEXT_VIEW = "text_view";

    static {
        DEFAULT_REMINDERS = new LinkedHashSet<String>();
        DEFAULT_REMINDERS.add(getTimeAsString(6,0).toString());
        DEFAULT_REMINDERS.add(getTimeAsString(12,0).toString());
        DEFAULT_REMINDERS.add(getTimeAsString(18,0).toString());
        DEFAULT_REMINDERS.add(getTimeAsString(0,0).toString());
    }

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

        prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        updateReminderTextView(getReminderPreferences());
        return view;
    }

    protected Set<String> getReminderPreferences() {
        //we have to create a NEW set
        //otherwise the preferences are saved only for the first time and than not anymore!!
        //see: http://stackoverflow.com/questions/12528836/shared-preferences-only-saved-first-time
        HashSet<String> reminders = new HashSet<String>(prefs.getStringSet(REMINDERS, DEFAULT_REMINDERS));
        return reminders;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected void updateReminderTextView(Set<String> reminders) {

        //since the set is unsorted we needed to sort it by the time (of type long):
        //1. convert a list of reminder text into a list of reminder of type long
        //2. then sort this list
        ArrayList<Long> timeList = new ArrayList<Long>();
        Iterator<String> iterator = reminders.iterator();

        int[] hourAndMinute1 = getHourAndMinute(iterator.next());
        long time1 = getTime(hourAndMinute1[0], hourAndMinute1[1]);
        timeList.add(new Long(time1));

        int[] hourAndMinute2 = getHourAndMinute(iterator.next());
        long time2 = getTime(hourAndMinute2[0], hourAndMinute2[1]);
        timeList.add(new Long(time2));

        int[] hourAndMinute3 = getHourAndMinute(iterator.next());
        long time3 = getTime(hourAndMinute3[0], hourAndMinute3[1]);
        timeList.add(new Long(time3));

        int[] hourAndMinute4 = getHourAndMinute(iterator.next());
        long time4 = getTime(hourAndMinute4[0], hourAndMinute4[1]);
        timeList.add(new Long(time4));

        Collections.sort(timeList);
        //now add the sorted back to the Set
        reminders = new LinkedHashSet<String>();
        Iterator<Long> timeIterator = timeList.iterator();
        time1 = timeIterator.next();
        reminders.add(getTimeAsString(time1).toString());

        time2 = timeIterator.next();
        reminders.add(getTimeAsString(time2).toString());

        time3 = timeIterator.next();
        reminders.add(getTimeAsString(time3).toString());

        time4 = timeIterator.next();
        reminders.add(getTimeAsString(time4).toString());

        iterator = reminders.iterator();
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

            int[] hourAndMinute = getHourAndMinute(timeView.getText());

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
            String newTime = getTimeAsString(hourOfDay, minute).toString();
            ((ReminderSettingsFragment) getTargetFragment()).saveReminderPreferences(oldTime, newTime);

        }

    }

    private static int[] getHourAndMinute(CharSequence timeS) {
        CharSequence hourOfDayS = timeS.subSequence(0, 2);
        CharSequence minuteS = timeS.subSequence(3, 5);

        int hourOfDay = Integer.parseInt(hourOfDayS.toString());
        int minute = Integer.parseInt(minuteS.toString());
        return new int[] {hourOfDay, minute};
    }

    // Prepends a "0" to 1-digit minutes
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private static CharSequence getTimeAsString(int hourOfDay, int minute) {
        return new StringBuilder().append(pad(hourOfDay)).append(":")
                .append(pad(minute));
    }

    private static CharSequence getTimeAsString(long time) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(time);
        int hourOfDay = cal.get(GregorianCalendar.HOUR_OF_DAY);
        int minute = cal.get(GregorianCalendar.MINUTE);
        return new StringBuilder().append(pad(hourOfDay)).append(":")
                .append(pad(minute));
    }

    private static long getTime(int hourOfDay, int minute) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(0);
        cal.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
        cal.set(GregorianCalendar.MINUTE, minute);
        return cal.getTimeInMillis();
    }

    protected void saveReminderPreferences(String oldTime, String newTime) {
        //something changed?
        Set<String> reminders = getReminderPreferences();
        if (!reminders.contains(newTime)) {
            reminders.remove(oldTime);
            reminders.add(newTime);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putStringSet(REMINDERS, reminders);
            editor.commit();
            updateReminderTextView(reminders);
        }

    }
}
