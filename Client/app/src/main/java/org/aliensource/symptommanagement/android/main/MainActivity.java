package org.aliensource.symptommanagement.android.main;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import org.aliensource.symptommanagement.DateTimeUtils;
import org.aliensource.symptommanagement.android.LoginScreenActivity;
import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.checkin.CheckInFragment;
import org.aliensource.symptommanagement.android.checkin.CheckInUtils;
import org.aliensource.symptommanagement.android.doctor.DoctorFragment;
import org.aliensource.symptommanagement.android.patientslist.OnPatientsInteractionListener;
import org.aliensource.symptommanagement.android.patientslist.PatientsListFragment;
import org.aliensource.symptommanagement.android.reminder.AlarmNotificationReceiver;
import org.aliensource.symptommanagement.android.reminder.ReminderPreferencesUtils;
import org.aliensource.symptommanagement.android.reminder.ReminderSettingsFragment;
import org.aliensource.symptommanagement.android.report.PatientReportFragment;
import org.aliensource.symptommanagement.client.service.CallableTask;
import org.aliensource.symptommanagement.client.service.PatientSvc;
import org.aliensource.symptommanagement.client.service.SecuritySvc;
import org.aliensource.symptommanagement.client.service.SymptomSvc;
import org.aliensource.symptommanagement.client.service.TaskCallback;
import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.SecurityService;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

public class MainActivity extends SherlockFragmentActivity implements OnPatientsInteractionListener {

    @InjectView(R.id.main_layout)
    protected DrawerLayout mDrawerLayout;

    @InjectView(R.id.main_menu)
    protected ListView mainMenuList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    //workaround: initialize with empty array, will be set in initMenus() later anyway
    private String[] menuTitles = {""};
    //fragment array must be in the same order/position as for menuTitles array
    private Fragment[] fragments;

    private Map<String, PendingIntent> mReminderNotificationReceiverPendingIntentMap = new LinkedHashMap<String, PendingIntent>();

    private AlarmManager mAlarmManager;
    private int alarmId = 0;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        View layout = (View) findViewById(R.id.main_layout);

		ButterKnife.inject(this);

        Bundle args = getIntent().getExtras();

        initDrawerMenu(savedInstanceState);

        initAlarms();
    }

    private void initDrawerMenu(Bundle savedInstanceState) {
        mTitle = mDrawerTitle = getTitle();

        mTitle = mDrawerTitle = getTitle();
        initMenus();

        // set up the drawer's list view with items and click listener
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                R.layout.drawer_list_item, menuTitles);
        mainMenuList.setAdapter(adapter);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together to the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_action_view_as_list,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO - not ideal to always call initMenus but I don't know yet how to remove the init logic in the onCreate()-method
        initMenus();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mainMenuList)) {
                mDrawerLayout.closeDrawer(mainMenuList);
            } else {
                mDrawerLayout.openDrawer(mainMenuList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnItemClick(R.id.main_menu)
    protected void selectItem(int position) {
        if (position == menuTitles.length - 1) {
            logout();
            return;
        }
        GregorianCalendar checkInTime = new GregorianCalendar();
        String date = DateTimeUtils.FORMAT_DDMMYYYY.format(checkInTime.getTime());
        String time = DateTimeUtils.FORMAT_HHMM.format(checkInTime.getTime());
        Bundle args = fragments[position].getArguments();
        args.putString(CheckInUtils.PREF_DATE, date);
        args.putString(CheckInUtils.PREF_TIME, time);

        // update the main content by replacing fragments
        if (fragments[position] instanceof CheckInFragment
                && CheckInUtils.initCheckIn(this)) {
            System.out.println(">>>> init check-in");
            initCheckIn(checkInTime, date, time);
        }
        showFragment(fragments[position]);
        // update selected item and title, then close the drawer
        mainMenuList.setItemChecked(position, true);
        setTitle(menuTitles[position]);
        mDrawerLayout.closeDrawer(mainMenuList);
    }

    protected void logout() {
        MainUtils.removeCredentials(this);
        startActivity(new Intent(MainActivity.this,
                LoginScreenActivity.class));
    }

    protected void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Initializes the selections, date, and times in the Check-In tabs.
     * This allows to remember so that the user can continue later on
     */
    public void initCheckIn(final GregorianCalendar checkInTime, final String date, final String time) {
        //save default values in preferences
        //use by default current  time for checkin time, symptom time and intake time

        //symptom values
        final Activity activity = this;
        initSymptom(0, date, time, checkInTime.getTimeInMillis(), ServiceUtils.SYMPTOM_TYPE_SORE_THROAT);
        initSymptom(1, date, time, checkInTime.getTimeInMillis(), ServiceUtils.SYMPTOM_TYPE_EAT_DRINK);

        final PatientSvcApi patientService = PatientSvc.getInstance().init(this);
        final String username = MainUtils.getCredentials(this).username;
        //initialize intake time
        CallableTask.invoke(new Callable<Patient>() {
            @Override
            public Patient call() throws Exception {
                return patientService.findByUsername(username);
            }
        }, new TaskCallback<Patient>() {
            @Override
            public void success(Patient patient) {
                List<Medication> medications = new ArrayList<Medication>();
                medications.addAll(patient.getMedications());
                int prefSuffix = 0;
                for (Medication medication: medications) {
                    CheckInUtils.saveEditor(
                            activity, prefSuffix,
                            CheckInUtils.PREF_MEDICATION_PREFIX, medication.getId(), date, time, checkInTime.getTimeInMillis(), -1);
                    prefSuffix++;
                }
            }

            @Override
            public void error(Exception e) {
                throw new RuntimeException("Patient " + username + " not found!");
            }
        });
        CheckInUtils.resetCheckIn(this, false);
    }

    protected void initSymptom(
            final int prefSuffix,
            final String date,
            final String time,
            final long dateTime,
            final String type) {
        final SymptomSvcApi api = SymptomSvc.getInstance().init(this);
        final Activity activity = this;
        CallableTask.invoke(new Callable<List<Symptom>>() {
            @Override
            public List<Symptom> call() throws Exception {
                return api.findByType(type);
            }
        }, new TaskCallback<List<Symptom>>() {
            @Override
            public void success(List<Symptom> result) {
                Symptom symptom = result.get(0);
                CheckInUtils.saveEditor(
                        activity, prefSuffix,
                        CheckInUtils.PREF_SYMPTOM_PREFIX,
                        symptom.getId(), date, time, dateTime, -1);
            }

            @Override
            public void error(Exception e) {
                throw new RuntimeException("Symptom " + type + " not found!");
            }
        });
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
	protected void onResume() {
		super.onResume();
	}

    private void initMenus() {
        final SecurityService svc = SecuritySvc.getInstance().init(this);
        if (svc != null) {
            CallableTask.invoke(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return svc.hasRole(SecurityService.ROLE_DOCTOR);
                }
            }, new TaskCallback<Boolean>() {

                @Override
                public void success(Boolean isDoctor) {
                    String logout = getString(R.string.logout);
                    if (isDoctor) {
                        String menu1 = getString(R.string.patient_list);
                        menuTitles = new String[]{menu1, logout};

                        Fragment fragment1 = new PatientsListFragment();
                        Bundle args1 = new Bundle();
                        args1.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_patients_list);
                        fragment1.setArguments(args1);

                        Fragment fragment2 = new PatientReportFragment();
                        Bundle args2 = new Bundle();
                        args2.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_patient_report);
                        fragment2.setArguments(args2);

                        fragments = new Fragment[]{fragment1, fragment2};
                    } else {
                        String menu1 = getString(R.string.check_in);
                        String menu2 = getString(R.string.reminder_settings);
                        menuTitles = new String[]{menu1, menu2, logout};

                        Fragment fragment1 = new CheckInFragment();
                        Bundle args1 = new Bundle();
                        args1.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_check_in);
                        fragment1.setArguments(args1);

                        Fragment fragment2 = new ReminderSettingsFragment();
                        Bundle args2 = new Bundle();
                        args2.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_reminder_settings);
                        fragment2.setArguments(args2);

                        fragments = new Fragment[]{fragment1, fragment2};
                    }

                    // set up the drawer's list view with items and click listener
                    mainMenuList.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                            R.layout.drawer_list_item, menuTitles));
                }

                @Override
                public void error(Exception e) {
                    Toast.makeText(
                            MainActivity.this,
                            "Unable to create menus. " + e.getMessage() ,
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(MainActivity.this,
                            LoginScreenActivity.class));
                }
            });
        }
    }

    /**
     * Also called by {@link ReminderSettingsFragment#saveReminderPreferences(String, String)}.
     */
    public void initAlarms() {
        final SecurityService svc = SecuritySvc.getInstance().init(this);
        if (svc != null) {
            CallableTask.invoke(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return svc.hasRole(SecurityService.ROLE_PATIENT);
                }
            }, new TaskCallback<Boolean>() {

                @Override
                public void success(Boolean isPatient) {
                    if (isPatient) {
                        createAlarms();
                    }
                }

                @Override
                public void error(Exception e) {
                    Toast.makeText(
                            MainActivity.this,
                            "Unable to initialize alarms",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void createAlarms() {
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        SharedPreferences prefs = MainUtils.getPreferences(this);
        Set<String> reminderAlarms = ReminderPreferencesUtils.getReminderAlarms(this);

        //cancel old alarms
        if (!mReminderNotificationReceiverPendingIntentMap.isEmpty()) {
            for (PendingIntent alarm: mReminderNotificationReceiverPendingIntentMap.values()) {
                mAlarmManager.cancel(alarm);
            }
            mReminderNotificationReceiverPendingIntentMap.clear();
        }

        GregorianCalendar now = new GregorianCalendar();

        for (String reminderTime: reminderAlarms) {
            int[] hourAndMinute = DateTimeUtils.getHourAndMinute(reminderTime);
            //always create a new calendar
            GregorianCalendar cal = new GregorianCalendar();
            //it is only a matter of milliseconds, but use the same time for creation ;)
            cal.setTimeInMillis(now.getTimeInMillis());
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.HOUR_OF_DAY, hourAndMinute[0]);
            cal.set(Calendar.MINUTE, hourAndMinute[1]);
            // in case the reminderTime time the date needs to move to the next day
            //otherwise the alarm notification is shown right away
            String hourAndMinuteS = DateTimeUtils.FORMAT_HHMM.format(cal.getTime());
            if (cal.before(now)) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                Toast.makeText(
                        this,
                        getString(R.string.next_reminder_tomorrow, hourAndMinuteS),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(
                        this,
                        getString(R.string.next_reminder_today, hourAndMinuteS),
                        Toast.LENGTH_SHORT).show();
            }

            //create the intent for the AlarmNotificationReceiver and then wrap it in a PendingIntent
            Intent mAlarmNotificationReceiverIntent = new Intent(MainActivity.this, AlarmNotificationReceiver.class);

            //the id must be passed to the receiver and defined in the PendingIntent
            alarmId++;
            mAlarmNotificationReceiverIntent.putExtra(AlarmNotificationReceiver.ARGS_ALARM_ID, alarmId);
            mAlarmNotificationReceiverIntent.putExtra(AlarmNotificationReceiver.ARGS_ALARM_TIME, reminderTime);
            PendingIntent mAlarmNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
                    MainActivity.this,
                    alarmId, //this ID must be unique for each PendingIntent, otherwise only the last is set as an alarm
                    mAlarmNotificationReceiverIntent,
                    //set flag otherwiese when an alarm is replace the old extra bundle is not replaced!
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    mAlarmNotificationReceiverPendingIntent);

            mReminderNotificationReceiverPendingIntentMap.put(reminderTime, mAlarmNotificationReceiverPendingIntent);
        }
    }

    protected void updateReminderNotification(String oldTime, String newTime) {
        PendingIntent mReminderNotificationReceiverPendingIntent = mReminderNotificationReceiverPendingIntentMap.remove(oldTime);

    }

    @Override
    public void onPatientSelected() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");

        ListView modeList = new ListView(this);
        String[] stringArray = new String[] {getString(R.string.doctor_medications_tab_title),
        getString(R.string.patient_report)};
        ArrayAdapter<String> modeAdapter =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        stringArray);
        modeList.setAdapter(modeAdapter);

        builder.setView(modeList);
        final Dialog dialog = builder.create();
        modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    Bundle args = new Bundle();
                    args.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_patient_report);
                    Fragment fragment = new PatientReportFragment();
                    fragment.setArguments(args);
                    showFragment(fragment);
                } else {
                    Bundle args = new Bundle();
                    args.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_doctor);
                    Fragment fragment = new DoctorFragment();
                    fragment.setArguments(args);
                    showFragment(fragment);
                }
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}