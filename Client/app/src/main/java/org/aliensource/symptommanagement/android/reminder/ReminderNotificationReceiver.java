package org.aliensource.symptommanagement.android.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.aliensource.symptommanagement.android.main.MainActivity;
import org.aliensource.symptommanagement.android.R;

/**
 * Created by ttruong on 11-Nov-14.
 */
public class ReminderNotificationReceiver extends BroadcastReceiver {

    protected static final int ALARM_ID_OFFSET = 1;
    public static final String ARGS_ALARM_ID = "alarm_id";
    public static final String ARGS_ALARM_TIME = "alarm_time";

    // Notification Action Elements
    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;

    // Alarm vibration
    private final long[] mVibratePattern = { 0, 200, 200, 300 };

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationIntent = new Intent(context, MainActivity.class);
        mContentIntent = PendingIntent.getActivity(context, 0, mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String time = intent.getExtras().getString(ARGS_ALARM_TIME);
        String tickerText = context.getResources().getString(R.string.reminder_ticker_text, time);
        CharSequence notificationTitle = context.getResources().getString(R.string.reminder_notification_title, time);
        CharSequence notificationText = context.getResources().getString(R.string.reminder_notification_text, time);
        Notification.Builder builder = new Notification.Builder(context)
                .setTicker(tickerText)
                .setAutoCancel(true) //dismiss notification on user touch event
                .setSmallIcon(android.R.drawable.stat_sys_warning) //icon must be set, otherwise it is not shown
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setVibrate(mVibratePattern)
                .setContentIntent(mContentIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int alarmId = intent.getIntExtra(ARGS_ALARM_ID, 0);
        mNotificationManager.notify(ALARM_ID_OFFSET + alarmId, builder.build());
    }
}
