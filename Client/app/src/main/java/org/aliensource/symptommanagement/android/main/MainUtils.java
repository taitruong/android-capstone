package org.aliensource.symptommanagement.android.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ttruong on 21-Nov-14.
 */
public final class MainUtils extends BaseUtils {

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

    public static CredentialsWrapper getCredentials(Activity activity) {
        SharedPreferences prefs = getPreferences(activity);
        CredentialsWrapper data = new CredentialsWrapper();
        data.server = prefs.getString(MainUtils.PREF_MAIN_SERVER, "");
        data.username = prefs.getString(MainUtils.PREF_MAIN_USERNAME, "");
        data.password = prefs.getString(MainUtils.PREF_MAIN_PASSWORD, "");
        return data;
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