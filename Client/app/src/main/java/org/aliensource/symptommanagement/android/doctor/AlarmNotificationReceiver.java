package org.aliensource.symptommanagement.android.doctor;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.main.MainActivity;

/**
 * Created by ttruong on 11-Nov-14.
 */
public class AlarmNotificationReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context,AlarmService.class);
        context.startService(serviceIntent);
    }
}
