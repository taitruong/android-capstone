package org.aliensource.symptommanagement.android.checkin;

import android.app.Activity;
import android.content.SharedPreferences;

import org.aliensource.symptommanagement.DateTimeUtils;
import org.aliensource.symptommanagement.android.main.BaseUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ttruong on 23-Nov-14.
 */
public final class CheckInUtils extends BaseUtils {

    public static final String ARG_POS = "pos";

    public static final String PREF_INIT_CHECKIN = "init-checkin";

    public static final String PREF_SYMPTOM_PREFIX = "symptom-";
    public static final String PREF_MEDICATION_PREFIX = "medication-";

    public static final String PREF_ID = "id";
    public static final String PREF_SELECTION = "selection";
    public static final String PREF_DATE_TIME = "date-time";
    public static final String PREF_DATE = "date";
    public static final String PREF_TIME = "time";

    public static void saveEditor(Activity activity, int pos, String prefix, long id, String date, String time, int selection) {
        SharedPreferences prefs = getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREF_INIT_CHECKIN, true);
        editor.putLong(prefix + PREF_ID + pos, id);
        editor.putString(prefix + PREF_DATE_TIME + pos, date + " " + time);

        editor.putString(PREF_DATE, date);
        editor.putString(PREF_TIME, time);

        editor.putInt(prefix + PREF_SELECTION + pos, selection);
        editor.commit();
    }

    public static void saveSelection(Activity activity, int pos, String prefix, int selection) {
        SharedPreferences prefs = getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(prefix + PREF_SELECTION + pos, selection);
        editor.commit();
    }

    public static void saveDateTime(Activity activity, int pos, String prefix, String dateTimeS) throws ParseException {
        SharedPreferences prefs = getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(prefix + PREF_DATE_TIME + pos, dateTimeS);

        Date dateTime = DateTimeUtils.FORMAT_DDMMYYYY_HHMM.parse(dateTimeS);
        String dateS = DateTimeUtils.FORMAT_DDMMYYYY.format(dateTime);
        String timeS = DateTimeUtils.FORMAT_HHMM.format(dateTime);
        editor.putString(PREF_DATE, dateS);
        editor.putString(PREF_TIME, timeS);

        editor.commit();
    }

    public static boolean initCheckIn(Activity activity) {
        SharedPreferences prefs = getPreferences(activity);
        return prefs.getBoolean(PREF_INIT_CHECKIN, false);
    }

}
