package org.aliensource.symptommanagement.android.patientslist;

import android.net.Uri;

/**
 * Created by ttruong on 27-Nov-14.
 *
 * This interface must be implemented by any activity that loads this fragment. When an
 * interaction occurs, such as touching an item from the ListView, these callbacks will
 * be invoked to communicate the event back to the activity.
 */
public interface OnPatientsInteractionListener {
    /**
     * Called when a patient is selected from the ListView.
     */
    public void onPatientSelected(long patientId);

}
