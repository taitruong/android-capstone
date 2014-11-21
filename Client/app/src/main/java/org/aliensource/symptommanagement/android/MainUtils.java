package org.aliensource.symptommanagement.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by ttruong on 21-Nov-14.
 */
public final class MainUtils {

    private static final String SHARED_PREFERENCES_MAIN = "main-shared-preferences";

    public static final String PREF_REMINDERS = "reminders";
    public static final String PREF_MAIN_SERVER = "server";
    public static final String PREF_MAIN_USERNAME = "username";
    public static final String PREF_MAIN_PASSWORD = "password";

    //passed to main activity
    public static final String ARG_SERVER = "server";
    public static final String ARG_USERNAME = "username";
    public static final String ARG_PASSWORD = "password";

    public static final String ARG_LAYOUT = "layout";

    public static final String ARG_DATE = "date";

    public static final String ARG_TIME = "time";

    //pref utils
    public static SharedPreferences getPreferences(Activity activity) {
        return activity.getSharedPreferences(SHARED_PREFERENCES_MAIN, Context.MODE_PRIVATE);
    }

    public static String[] getCredentials(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(SHARED_PREFERENCES_MAIN, Context.MODE_PRIVATE);
        String server = prefs.getString(MainUtils.PREF_MAIN_SERVER, "");
        String username = prefs.getString(MainUtils.PREF_MAIN_USERNAME, "");
        String password = prefs.getString(MainUtils.PREF_MAIN_PASSWORD, "");
        return new String[] {server, username, password};
    }

    public static void saveCredentials(Activity activity, String server, String username, String password) {
        SharedPreferences prefs = getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(MainUtils.PREF_MAIN_SERVER, server);
        editor.putString(MainUtils.PREF_MAIN_USERNAME, username);
        editor.putString(MainUtils.PREF_MAIN_PASSWORD, password);
        editor.commit();
    }

}