package app.cloud9.com.cloud9;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.plus.PlusClient;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private PlusClient mPlusClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPlusClient = new PlusClient.Builder(this, this, this).build();


//        Toolbar toolbar = (Toolbar) findViewById(R.id.c9_toolbar); //Appcompat support for a sexier action bar
//        toolbar.setNavigationIcon(R.drawable.ic_drawer);
//        toolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(R.string.app_name);
//        toolbar.setNavigationIcon(R.drawable.ic_drawer);
//
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.c9_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), toolbar);

//        mTitle = mDrawerTitle = getTitle();
//
//
//        // load slide menu items
//        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
//
//        // nav drawer icons from resources
//        navMenuIcons = getResources()
//                .obtainTypedArray(R.array.nav_drawer_icons);
//
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
//
//        navDrawerItems = new ArrayList<NavDrawerItem>();
//
//        // adding nav drawer items to array
//        // Home
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
//        // Find People
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
//        // Photos
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
//        // Communities, Will add a counter here
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
//
//
//        // Recycle the typed array
//        navMenuIcons.recycle();
//
//        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
//
//        // setting the nav drawer list adapter
//        adapter = new NavDrawerListAdapter(getApplicationContext(),
//                navDrawerItems);
//        mDrawerList.setAdapter(adapter);
////
////        // enabling action bar app icon and behaving it as toggle button
////        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFC107")));
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////        getSupportActionBar().setHomeButtonEnabled(true);
//
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                R.drawable.ic_drawer, //nav menu toggle icon
//                R.string.app_name, // nav drawer open - description for accessibility
//                R.string.app_name // nav drawer close - description for accessibility
//        ) {
//            public void onDrawerClosed(View view) {
//                getSupportActionBar().setTitle(mTitle);
//                getSupportActionBar();
//                // calling onPrepareOptionsMenu() to show action bar icons
//                invalidateOptionsMenu();
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                getSupportActionBar().setTitle(mDrawerTitle);
//                // calling onPrepareOptionsMenu() to hide action bar icons
//                invalidateOptionsMenu();
//            }
//        };
//        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
        if (position == 5) {
            mPlusClient.disconnect();
            Intent i = new Intent(this, LoginPage.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
//
//    /**
//     * Slide menu item click listener
//     */
//    private class SlideMenuClickListener implements
//            ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position,
//                                long id) {
//            // display view for selected nav drawer item
//            displayView(position);
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_activity_actions, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // toggle nav drawer on selecting action bar app icon/title
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        // Handle action bar actions click
//        switch (item.getItemId()) {
//            case R.id.action_refresh:
//                displayView(0);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    /**
//     * Called when invalidateOptionsMenu() is triggered
//     */
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // if nav drawer is opened, hide the action items
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
////        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomePage();
                break;
            case 1:
                fragment = new AboutPage();
                break;
//            case 2:
//                fragment = new PhotosFragment();
//                break;
//            case 3:
//                fragment = new CommunityFragment();
//                break;
//            case 4:
//                fragment = new PagesFragment();
//                break;
//            case 5:
//                fragment = new WhatsHotFragment();
//                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }
//
//            // update selected item and title, then close the drawer
////            mDrawerList.setItemChecked(position, true);
////            mDrawerList.setSelection(position);
////            setTitle(navMenuTitles[position]);
////            mDrawerLayout.closeDrawer(mDrawerList);
//        } else {
//            // error in creating fragment
//            Log.e("MainActivity", "Error in creating fragment");
//        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

//    @Override
//    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getSupportActionBar().setTitle(mTitle);
//    }

}

/**
 * When using the ActionBarDrawerToggle, you must call it during
 * onPostCreate() and onConfigurationChanged()...
 */

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // Pass any configuration change to the drawer toggls
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
//}