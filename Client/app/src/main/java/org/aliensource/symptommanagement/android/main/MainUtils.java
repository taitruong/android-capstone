package org.aliensource.symptommanagement.android.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.aliensource.symptommanagement.android.LoginScreenActivity;

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
        //go to login in case credentials are partially corrupt
        if (TextUtils.isEmpty(data.server)
                || TextUtils.isEmpty(data.password)
                || TextUtils.isEmpty(data.username)) {
            removeCredentials(activity);
            Intent intent = new Intent(activity, LoginScreenActivity.class);
            activity.startActivity(intent);
        }
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

    public static void removeCredentials(Activity activity) {
        SharedPreferences prefs = getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(MainUtils.PREF_MAIN_SERVER);
        editor.remove(MainUtils.PREF_MAIN_USERNAME);
        editor.remove(MainUtils.PREF_MAIN_PASSWORD);
        editor.commit();
    }

}