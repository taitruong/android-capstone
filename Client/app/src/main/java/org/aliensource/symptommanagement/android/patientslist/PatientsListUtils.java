package org.aliensource.symptommanagement.android.patientslist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.ContactsContract;

import org.aliensource.symptommanagement.android.main.BaseUtils;

/**
 * Created by ttruong on 27-Nov-14.
 */
public class PatientsListUtils extends BaseUtils {

    public static final String PREFS_PATIENT_ID = "patient-id";

    /*
     * Defines an array that contains column names to move from
     * the Cursor to the ListView.
     */
    @SuppressLint("InlinedApi")
    public final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };

    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    public final static int[] TO_IDS = {
            android.R.id.text1
    };

    @SuppressLint("InlinedApi")
    public static final String[] PROJECTION = {
        /*
         * The detail data row ID. To make a ListView work,
         * this column is required.
         */
        ContactsContract.Data._ID,
        // The primary display name
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Data.DISPLAY_NAME_PRIMARY :
                ContactsContract.Data.DISPLAY_NAME,
        // The contact's _ID, to construct a content URI
        ContactsContract.Data.CONTACT_ID,
        // The contact's LOOKUP_KEY, to construct a content URI
        ContactsContract.Data.LOOKUP_KEY //(a permanent link to the contact
    };

    // The column index for the _ID column
    public static final int CONTACT_ID_INDEX = 0;
    // The column index for the LOOKUP_KEY column
    public static final int LOOKUP_KEY_INDEX = 1;

    public static void savePatientId(Activity activity, long id) {
        SharedPreferences prefs = getPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(PREFS_PATIENT_ID, id);
    }

    /**
     *
     * @param activity
     * @return -1 in case no patient has been set/selected yet
     */
    public static long getPatientId(Activity activity) {
        SharedPreferences prefs = getPreferences(activity);
        return prefs.getLong(PREFS_PATIENT_ID, -1);
    }
}
