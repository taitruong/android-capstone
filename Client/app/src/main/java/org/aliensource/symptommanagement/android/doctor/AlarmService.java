package org.aliensource.symptommanagement.android.doctor;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import org.aliensource.symptommanagement.DateTimeUtils;
import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.main.CredentialsWrapper;
import org.aliensource.symptommanagement.android.main.MainActivity;
import org.aliensource.symptommanagement.android.main.MainUtils;
import org.aliensource.symptommanagement.client.service.AlarmSvc;
import org.aliensource.symptommanagement.client.service.CallableTask;
import org.aliensource.symptommanagement.client.service.TaskCallback;
import org.aliensource.symptommanagement.cloud.repository.Alarm;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.PatientAlarmDTO;
import org.aliensource.symptommanagement.cloud.service.AlarmSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by ttruong on 30-Nov-14.
 */
public class AlarmService extends IntentService {

    private long alarmId = -1;
    private static final String TAG = "AlarmService";
    private Map<Long, Long> patientToAlarmId = new HashMap<Long, Long>();

    // Alarm vibration
    private final long[] mVibratePattern = { 0, 200, 200, 300 };

    public AlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkAlarm();
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    private void checkAlarm() {
        SharedPreferences prefs =
                getSharedPreferences(MainUtils.SHARED_PREFERENCES_MAIN, Context.MODE_PRIVATE);
        final CredentialsWrapper data = new CredentialsWrapper();
        data.server = prefs.getString(MainUtils.PREF_MAIN_SERVER, "");
        data.username = prefs.getString(MainUtils.PREF_MAIN_USERNAME, "");
        data.password = prefs.getString(MainUtils.PREF_MAIN_PASSWORD, "");

        final AlarmSvcApi alarmSvcApi = AlarmSvc.getInstance().init(data.server, data.username, data.password);
        CallableTask.invoke(new Callable<List<PatientAlarmDTO>>() {
            @Override
            public List<PatientAlarmDTO> call() throws Exception {
                return alarmSvcApi.getPatientAlarmsByDoctorUserName(data.username);
            }
        }, new TaskCallback<List<PatientAlarmDTO>>() {
            @Override
            public void success(List<PatientAlarmDTO> alarms) {
                for (PatientAlarmDTO patientAlarmDTO: alarms) {
                    Patient patient = patientAlarmDTO.patient;
                    Alarm alarm = patientAlarmDTO.alarm;
                    Long alarmId = patientToAlarmId.get(patient.getId());
                    if (alarmId == null) {
                        patientToAlarmId.put(patient.getId(), alarm.getId());
                    }
                    if (alarmId != null &&
                            alarm.getId() == alarmId) {
                    } else {
                        Intent mNotificationIntent = new Intent(getApplicationContext(),
                                MainActivity.class);
                        PendingIntent mContentIntent =
                                PendingIntent.getActivity(
                                        getApplicationContext(),
                                        0,
                                        mNotificationIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                        patientToAlarmId.put(patient.getId(), alarm.getId());
                        Date startDate = new Date(alarm.getStart());
                        Date endDate = new Date(alarm.getEnd());
                        String startS = DateTimeUtils.FORMAT_HHMM.format(startDate);
                        String endS = DateTimeUtils.FORMAT_HHMM.format(endDate);
                        CharSequence title;
                        CharSequence detail;
                        if (ServiceUtils.SYMPTOM_TYPE_SORE_THROAT.equals(alarm.getSymptom().getType())) {
                            int severity = alarm.getSeverity();
                            CharSequence severityS;
                            switch (severity) {
                                case 0:
                                    severityS = getString(R.string.check_in_symptom1_value1);
                                    break;
                                case 1:
                                    severityS = getString(R.string.check_in_symptom1_value2);
                                    break;
                                default:
                                    severityS = getString(R.string.check_in_symptom1_value3);
                            }
                            title =
                                    getResources().getString(
                                            R.string.doctor_alarms_sore_throat_mouth_pain_title,
                                            severityS,
                                            startS,
                                            endS);
                            detail =
                                    getResources().getString(
                                            R.string.doctor_alarms_sore_throat_mouth_pain,
                                            patient.getLastName() + ", " + patient.getFirstName(),
                                            severityS.toString().toLowerCase());

                        } else {
                            int severity = alarm.getSeverity();
                            CharSequence severityS;
                            switch (severity) {
                                case 0:
                                    severityS = getString(R.string.check_in_symptom2_value1);
                                    break;
                                case 1:
                                    severityS = getString(R.string.check_in_symptom2_value2);
                                    break;
                                default:
                                    severityS = getString(R.string.check_in_symptom2_value3);
                            }
                            title =
                                    getResources().getString(
                                            R.string.doctor_alarms_eat_drink_title,
                                            startS,
                                            endS);
                            detail =
                                    getResources().getString(
                                            R.string.doctor_alarms_eat_drink,
                                            patient.getLastName() + ", " + patient.getFirstName()
                                            );

                        }
                        Notification.Builder builder = new Notification.Builder(getApplicationContext())
                                .setTicker(detail)
                                .setAutoCancel(true) //dismiss notification on user touch event
                                .setSmallIcon(android.R.drawable.stat_sys_warning) //icon must be set, otherwise it is not shown
                                .setContentTitle(title)
                                .setContentText(detail)
                                .setVibrate(mVibratePattern)
                                .setContentIntent(mContentIntent);

                        NotificationManager mNotificationManager =
                                (NotificationManager)
                                        getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(0, builder.build());

                    }
                }
            }

            @Override
            public void error(Exception e) {

            }
        });
    }

}
