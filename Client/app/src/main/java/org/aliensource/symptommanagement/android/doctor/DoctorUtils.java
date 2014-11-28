package org.aliensource.symptommanagement.android.doctor;

import android.app.Activity;
import android.content.SharedPreferences;

import org.aliensource.symptommanagement.android.main.BaseUtils;

/**
 * Created by ttruong on 28-Nov-14.
 */
public class DoctorUtils extends BaseUtils {

    public static final String PREF_PATIENT_ID = "patient-id";

    public static void savePatientId(Activity activity, long patientId) {
        SharedPreferences prefs = getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(PREF_PATIENT_ID, patientId);
        editor.commit();
    }

    public static long getPatientId(Activity activity) {
        SharedPreferences prefs = getPreferences(activity);
        return prefs.getLong(PREF_PATIENT_ID, -1);
    }
}
