package app.cloud9.com.cloud9;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;
    String email;
    String personName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        personName = bundle.getString("name");


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        mGoogleApiClient.connect();


        Toolbar toolbar = (Toolbar) findViewById(R.id.c9_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), toolbar);
        mNavigationDrawerFragment.nav_email.setText(email);
        mNavigationDrawerFragment.nav_name.setText(personName);

        Context context = getApplicationContext();
        AppPrefs appPrefs = new AppPrefs(context);
        String usn = appPrefs.getUsn_saved();

        mNavigationDrawerFragment.nav_usn.setText(usn);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavigationDrawerFragment.selectItem(0);
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress && result.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(result.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
//        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();

        if (position == 1 && isNetworkAvailable()) {
            Intent i = new Intent(this, NoticeBoard.class);
            startActivity(i);
        }

        if (position != 0 && position == 1 && !isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "Please connect to the Internet", Toast.LENGTH_LONG).show();

        }

        if (position == 2) {
            Intent i = new Intent(this, AboutPage.class);
            startActivity(i);
        }
        if (position == 3) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Intent i = new Intent(this, LoginPage.class);
            startActivity(i);
        }
//        if (position == 4) {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
//                    + "/InSync Downloads/");
//            intent.setDataAndType(uri, "*/*");
//            startActivity(Intent.createChooser(intent, "Open folder"));
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            Uri uri = Uri.parse(Environment.getExternalStorageDirectory()
//                    + "/InSync Downloads/");
//            intent.setDataAndType(uri, "*/*");
//            startActivity(Intent.createChooser(intent, "Open folder"));
//        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("No", null).show();
        }
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                Bundle args = new Bundle();
                args.putString("email", email);
                fragment = new HomePage();
                fragment.setArguments(args);
                break;


            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onClick(View v) {

    }
}
