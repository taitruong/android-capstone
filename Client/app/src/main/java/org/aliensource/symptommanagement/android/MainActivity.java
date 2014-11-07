package org.aliensource.symptommanagement.android;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.aliensource.symptommanagement.cloud.repository.Video;
import org.aliensource.symptommanagement.cloud.service.SecurityService;
import org.aliensource.symptommanagement.cloud.service.VideoSvcApi;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity {

    @InjectView(R.id.drawer_layout)
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

	@InjectView(R.id.videoList)
	protected ListView videoList_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.inject(this);

        mTitle = mDrawerTitle = getTitle();
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
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //TODO - not ideal to always call initMenus but I don't know yet how to remove the init logic in the onCreate()-method
        initMenus();
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mainMenuList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, fragments[position])
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
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
	protected void onResume() {
		super.onResume();
		
		refreshVideos();
	}

    private void initMenus() {
        final SecurityService svc = VideoSvc.getSecurityServiceOrShowLogin(this);
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
                        args1.putInt(AbstractFragment.ARG_LAYOUT, R.layout.fragment_patient_list);
                        fragment1.setArguments(args1);

                        AbstractFragment fragment2 = new PatientReportFragment();
                        Bundle args2 = new Bundle();
                        args2.putInt(AbstractFragment.ARG_LAYOUT, R.layout.fragment_patient_report);
                        fragment2.setArguments(args2);

                        fragments = new AbstractFragment[] {fragment1, fragment2};
                    } else {
                        String menu1 = getString(R.string.check_in);
                        String menu2 = getString(R.string.reminder_settings);
                        menuTitles = new String[]{menu1, menu2};

                        AbstractFragment fragment1 = new CheckInFragment();
                        Bundle args1 = new Bundle();
                        args1.putInt(AbstractFragment.ARG_LAYOUT, R.layout.fragment_check_in);
                        fragment1.setArguments(args1);

                        AbstractFragment fragment2 = new ReminderSettingsFragment();
                        Bundle args2 = new Bundle();
                        args2.putInt(AbstractFragment.ARG_LAYOUT, R.layout.fragment_reminder_settings);
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

	private void refreshVideos() {
		final VideoSvcApi svc = VideoSvc.getOrShowLogin(this);

		if (svc != null) {
			CallableTask.invoke(new Callable<Collection<Video>>() {

				@Override
				public Collection<Video> call() throws Exception {
					return svc.getVideoList();
				}
			}, new TaskCallback<Collection<Video>>() {

				@Override
				public void success(Collection<Video> result) {
					List<String> names = new ArrayList<String>();
					for (Video v : result) {
						names.add(v.getName());
					}
					videoList_.setAdapter(new ArrayAdapter<String>(
							MainActivity.this,
							android.R.layout.simple_list_item_1, names));
				}

				@Override
				public void error(Exception e) {
					Toast.makeText(
							MainActivity.this,
							"Unable to fetch the video list, please login again.",
							Toast.LENGTH_SHORT).show();

					startActivity(new Intent(MainActivity.this,
							LoginScreenActivity.class));
				}
			});
		}
	}

}
