package org.aliensource.symptommanagement.android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import org.aliensource.symptommanagement.DateTimeUtils;
import org.aliensource.symptommanagement.android.checkin.CheckInFragment;
import org.aliensource.symptommanagement.android.patient.PatientListFragment;
import org.aliensource.symptommanagement.android.patient.PatientReportFragment;
import org.aliensource.symptommanagement.android.reminder.AlarmNotificationReceiver;
import org.aliensource.symptommanagement.android.reminder.ReminderPreferencesUtils;
import org.aliensource.symptommanagement.android.reminder.ReminderSettingsFragment;
import org.aliensource.symptommanagement.cloud.service.SecurityService;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends SherlockFragmentActivity {

    protected String username;

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
    private AbstractFragment[] fragments;

    private Map<String, PendingIntent> mReminderNotificationReceiverPendingIntentMap = new LinkedHashMap<String, PendingIntent>();

    private AlarmManager mAlarmManager;
    private int alarmId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        //TODO: adjust to check background for patient or doctor user
        View layout = (View) findViewById(R.id.main_layout);
        layout.setBackgroundColor(getResources().getColor(R.color.PatientBackground));

		ButterKnife.inject(this);

        Bundle args = getIntent().getExtras();
        username = args.getString(MainUtils.ARG_USERNAME);

        initDrawerMenu(savedInstanceState);

        initAlarms();
    }

    private void initDrawerMenu(Bundle savedInstanceState) {
        mTitle = mDrawerTitle = getTitle();

        mTitle = mDrawerTitle = getTitle();
        initMenus();

        // set up the drawer's list view with items and click listener
        mainMenuList.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                R.layout.drawer_list_item, menuTitles));
        mainMenuList.setOnItemClickListener(new DrawerItemClickListener());

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

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.content_frame, fragments[position])
                .commit();

        // update selected item and title, then close the drawer
        mainMenuList.setItemChecked(position, true);
        setTitle(menuTitles[position]);
        mDrawerLayout.closeDrawer(mainMenuList);
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
        final SecurityService svc = SecuritySvc.getSecurityServiceOrShowLogin(this);
        if (svc != null) {
            CallableTask.invoke(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return svc.hasRole(SecurityService.ROLE_DOCTOR);
                }
            }, new TaskCallback<Boolean>() {

                @Override
                public void success(Boolean isDoctor) {
                    if (isDoctor) {
                        String menu1 = getString(R.string.patient_list);
                        String menu2 = getString(R.string.patient_report);
                        menuTitles = new String[]{menu1, menu2};

                        AbstractFragment fragment1 = new PatientListFragment();
                        Bundle args1 = new Bundle();
                        args1.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_patient_list);
                        fragment1.setArguments(args1);

                        AbstractFragment fragment2 = new PatientReportFragment();
                        Bundle args2 = new Bundle();
                        args2.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_patient_report);
                        fragment2.setArguments(args2);

                        fragments = new AbstractFragment[] {fragment1, fragment2};
                    } else {
                        String menu1 = getString(R.string.check_in);
                        String menu2 = getString(R.string.reminder_settings);
                        menuTitles = new String[]{menu1, menu2};

                        AbstractFragment fragment1 = new CheckInFragment();
                        Bundle args1 = new Bundle();
                        args1.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_check_in);
                        fragment1.setArguments(args1);

                        AbstractFragment fragment2 = new ReminderSettingsFragment();
                        Bundle args2 = new Bundle();
                        args2.putInt(MainUtils.ARG_LAYOUT, R.layout.fragment_reminder_settings);
                        fragment2.setArguments(args2);

                        fragments = new AbstractFragment[] {fragment1, fragment2};
                    }

                    // set up the drawer's list view with items and click listener
                    mainMenuList.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                            R.layout.drawer_list_item, menuTitles));
                }

                @Override
                public void error(Exception e) {
                    Toast.makeText(
                            MainActivity.this,
                            "Unable to login.",
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(MainActivity.this,
                            LoginScreenActivity.class));
                }
            });
        }
    }

    public void initAlarms() {
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        SharedPreferences prefs = MainUtils.getPreferences(this);
        Set<String> reminderAlarms = ReminderPreferencesUtils.getReminderAlarms(this);

        //cancel old alarms
        if (!mReminderNotificationReceiverPendingIntentMap.isEmpty()) {
            // System.out.println(">>>>> remove old alarms <<<<<");
            for (PendingIntent alarm: mReminderNotificationReceiverPendingIntentMap.values()) {
                mAlarmManager.cancel(alarm);
            }
            mReminderNotificationReceiverPendingIntentMap.clear();
        }

        GregorianCalendar now = new GregorianCalendar();

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:sss");
        for (String reminderTime: reminderAlarms) {
            int[] hourAndMinute = DateTimeUtils.getHourAndMinute(reminderTime);
            //always create a new calendar
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTimeInMillis(now.getTimeInMillis());
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.HOUR_OF_DAY, hourAndMinute[0]);
            cal.set(Calendar.MINUTE, hourAndMinute[1]);
            // in case the reminderTime time the date needs to move to the next day
            //otherwise the alarm notification is shown right away
            String calS = formatter.format(new Date(cal.getTimeInMillis()));
            String nowS = formatter.format(new Date(now.getTimeInMillis()));
            // System.out.println(">>> " + cal.before(now) + ": " + calS + " before " + nowS);
            if (cal.before(now)) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
            // System.out.println(">>> alarm at: " + reminderTime + " - " + formatter.format(new Date(cal.getTimeInMillis())));

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

}