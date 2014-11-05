package org.aliensource.symptommanagement.android;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.aliensource.symptommanagement.cloud.repository.Video;
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
    private String[] menuTitles;

	@InjectView(R.id.videoList)
	protected ListView videoList_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.inject(this);

        mTitle = mDrawerTitle = getTitle();
        menuTitles = getResources().getStringArray(R.array.menu_array);

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mainMenuList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, menuTitles));
        mainMenuList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
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

        if (savedInstanceState == null) {
            selectItem(0);
        }

    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
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
