package org.aliensource.symptommanagement.android.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ttruong on 21-Nov-14.
 */
public abstract class BaseUtils {

    private static final String SHARED_PREFERENCES_MAIN = "main-shared-preferences";

    //pref utils
    public static SharedPreferences getPreferences(Activity activity) {
        return activity.getSharedPreferences(SHARED_PREFERENCES_MAIN, Context.MODE_PRIVATE);
    }

}