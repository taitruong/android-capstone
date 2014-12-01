package org.aliensource.symptommanagement.android.checkin;

import android.app.Activity;
import android.content.SharedPreferences;

import org.aliensource.symptommanagement.DateTimeUtils;
import org.aliensource.symptommanagement.android.main.BaseUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ttruong on 23-Nov-14.
 */
public final class CheckInUtils extends BaseUtils {

    public static final String ARG_PREF_SUFFIX = "prefSuffix";

    public static final String PREF_INIT_CHECKIN = "init-check-in";

    public static final int PREF_BOOLEAN_TRUE = 0;
    public static final int PREF_BOOLEAN_FALSE = -1;

    public static final String PREF_SYMPTOM_PREFIX = "symptom-";
    public static final String PREF_MEDICATION_PREFIX = "medication-";

    public static final String PREF_ID = "id";
    public static final String PREF_SELECTION = "selection";
    public static final String PREF_DATE_TIME = "date-time";
    public static final String PREF_DATE = "date";
    public static final String PREF_TIME = "time";

    public static void saveEditor(
            Activity activity, int prefSuffix, String prefix, long id, String date, String time, long dateTime, int selection) {
        SharedPreferences prefs = getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(prefix + PREF_ID + prefSuffix, id);
        editor.putLong(prefix + PREF_DATE_TIME + prefSuffix, dateTime);

        editor.putString(PREF_DATE, date);
        editor.putString(PREF_TIME, time);

        editor.putInt(prefix + PREF_SELECTION + prefSuffix, selection);
        editor.commit();
    }

    public static DataWrapper getEditor(
            Activity activity, String prefix, int prefSuffix) {
        DataWrapper result = new DataWrapper();
        SharedPreferences prefs = getPreferences(activity);
        result.id = prefs.getLong(prefix + PREF_ID + prefSuffix, -1);
        result.dateTime = prefs.getLong(prefix + PREF_DATE_TIME + prefSuffix, new GregorianCalendar().getTimeInMillis());

        result.selection = prefs.getInt(prefix + PREF_SELECTION + prefSuffix, -1);
        return result;
    }

    public static void saveSelection(Activity activity, int pos, String prefix, int selection) {
        SharedPreferences prefs = getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(prefix + PREF_SELECTION + pos, selection);
        editor.commit();
    }

    public static void saveDateTime(Activity activity, int prefSuffix, String prefix, String dateTimeS) throws ParseException {
        SharedPreferences prefs = getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        Date dateTime = DateTimeUtils.FORMAT_DDMMYYYY_HHMM.parse(dateTimeS);
        editor.putLong(prefix + PREF_DATE_TIME + prefSuffix, dateTime.getTime());

        String dateS = DateTimeUtils.FORMAT_DDMMYYYY.format(dateTime);
        String timeS = DateTimeUtils.FORMAT_HHMM.format(dateTime);
        editor.putString(PREF_DATE, dateS);
        editor.putString(PREF_TIME, timeS);

        editor.commit();
    }

    public static boolean initCheckIn(Activity activity) {
        SharedPreferences prefs = getPreferences(activity);
        return prefs.getBoolean(PREF_INIT_CHECKIN, true);
    }

    public static void resetCheckIn(Activity activity, boolean reset) {
        SharedPreferences prefs = getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREF_INIT_CHECKIN, reset);
        editor.commit();
    }

}
