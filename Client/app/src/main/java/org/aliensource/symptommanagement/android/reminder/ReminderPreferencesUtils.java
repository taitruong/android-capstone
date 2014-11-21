package org.aliensource.symptommanagement.android.reminder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.aliensource.symptommanagement.DateTimeUtils;
import org.aliensource.symptommanagement.android.MainUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by ttruong on 11-Nov-14.
 */
public final class ReminderPreferencesUtils {

    private static final Set<String> DEFAULT_REMINDERS;

    static {
        DEFAULT_REMINDERS = new LinkedHashSet<String>();
        DEFAULT_REMINDERS.add(DateTimeUtils.getTimeAsString(6, 0).toString());
        DEFAULT_REMINDERS.add(DateTimeUtils.getTimeAsString(12, 0).toString());
        DEFAULT_REMINDERS.add(DateTimeUtils.getTimeAsString(18, 0).toString());
        DEFAULT_REMINDERS.add(DateTimeUtils.getTimeAsString(0,0).toString());
    }

    /////// preferences

    public static Set<String> getReminderAlarms(Activity activity) {
        SharedPreferences prefs = MainUtils.getPreferences(activity);
        //we have to create a NEW set
        //otherwise the preferences are saved only for the first time and than not anymore!!
        //see: http://stackoverflow.com/questions/12528836/shared-preferences-only-saved-first-time
        HashSet<String> reminders = new HashSet<String>(prefs.getStringSet(MainUtils.PREF_REMINDERS, DEFAULT_REMINDERS));
        return reminders;
    }

    public static void saveReminderAlarms(Activity activity, Set<String> reminders) {
        SharedPreferences prefs = MainUtils.getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(MainUtils.PREF_REMINDERS, reminders);
        editor.commit();
    }

}