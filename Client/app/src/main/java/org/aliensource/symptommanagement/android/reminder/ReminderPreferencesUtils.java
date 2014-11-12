package org.aliensource.symptommanagement.android.reminder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by ttruong on 11-Nov-14.
 */
public final class ReminderPreferencesUtils {

    protected static final String PREFERENCES_KEY_REMINDERS = "reminders";

    private static final Set<String> DEFAULT_REMINDERS;

    static {
        DEFAULT_REMINDERS = new LinkedHashSet<String>();
        DEFAULT_REMINDERS.add(getTimeAsString(6,0).toString());
        DEFAULT_REMINDERS.add(getTimeAsString(12,0).toString());
        DEFAULT_REMINDERS.add(getTimeAsString(18,0).toString());
        DEFAULT_REMINDERS.add(getTimeAsString(0,0).toString());
    }

    /////// preferences

    public static SharedPreferences getPreferences(Activity activity) {
        return activity.getPreferences(Context.MODE_PRIVATE);
    }

    public static Set<String> getReminderAlarms(SharedPreferences prefs) {
        //we have to create a NEW set
        //otherwise the preferences are saved only for the first time and than not anymore!!
        //see: http://stackoverflow.com/questions/12528836/shared-preferences-only-saved-first-time
        HashSet<String> reminders = new HashSet<String>(prefs.getStringSet(PREFERENCES_KEY_REMINDERS, DEFAULT_REMINDERS));
        return reminders;
    }

    public static void saveReminderPreferences(SharedPreferences prefs, Set<String> reminders) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putStringSet(PREFERENCES_KEY_REMINDERS, reminders);
            editor.commit();
    }

    /////// time manipulation
    public static CharSequence getTimeAsString(int hourOfDay, int minute) {
        return new StringBuilder().append(pad(hourOfDay)).append(":")
                .append(pad(minute));
    }

    public static CharSequence getTimeAsString(long time) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(time);
        int hourOfDay = cal.get(GregorianCalendar.HOUR_OF_DAY);
        int minute = cal.get(GregorianCalendar.MINUTE);
        return new StringBuilder().append(pad(hourOfDay)).append(":")
                .append(pad(minute));
    }

    // Prepends a "0" to 1-digit minutes
    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public static int[] getHourAndMinute(CharSequence timeS) {
        CharSequence hourOfDayS = timeS.subSequence(0, 2);
        CharSequence minuteS = timeS.subSequence(3, 5);

        int hourOfDay = Integer.parseInt(hourOfDayS.toString());
        int minute = Integer.parseInt(minuteS.toString());
        return new int[] {hourOfDay, minute};
    }

    public static long getTime(int hourOfDay, int minute) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(0);
        cal.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
        cal.set(GregorianCalendar.MINUTE, minute);
        return cal.getTimeInMillis();
    }


}
