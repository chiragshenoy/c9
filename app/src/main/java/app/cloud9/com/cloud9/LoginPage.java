package app.cloud9.com.cloud9;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class LoginPage extends Activity implements OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
//
//    private static final String TAG = "SignInTestActivity";
//
//    // A magic number we will use to know that our sign-in error
//    // resolution activity has completed.
//    private static final int OUR_REQUEST_CODE = 49404;
//
//    // The core Google+ client.
//    private PlusClient mPlusClient;
//
//    // A flag to stop multiple dialogues appearing for the user.
//    private boolean mResolveOnFail;
//
//    // We can store the connection result from a failed connect()
//    // attempt in order to make the application feel a bit more
//    // responsive for the user.
//    private ConnectionResult mConnectionResult;
//
//    // A progress dialog to display when the user is connecting in
//    // case there is a delay in any of the dialogs being ready.


    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;

    private ProgressDialog mConnectionProgressDialog;
    TextView welcome;
    String email;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        welcome = (TextView) findViewById(R.id.welcome);


        // Connect our sign in, sign out and disconnect buttons.
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // Configure the ProgressDialog that will be shown if there is a
        // delay in presenting the user with the next sign in step.
        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Signing in...");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                mGoogleApiClient.connect();

                break;
//            case R.id.sign_out_button:
//                Log.v(TAG, "Tapped sign out");
//                // We only want to sign out if we're connected.
//                if (mPlusClient.isConnected()) {
//                    // Clear the default account in order to allow the user
//                    // to potentially choose a different account from the
//                    // account chooser.
//                    mPlusClient.clearDefaultAccount();
//
//                    // Disconnect from Google Play Services, then reconnect in
//                    // order to restart the process from scratch.
//                    mPlusClient.disconnect();
//                    mPlusClient.connect();
//
//                    // Hide the sign out buttons, show the sign in button.
//                    findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//                    findViewById(R.id.sign_out_button)
//                            .setVisibility(View.INVISIBLE);
//                    welcome.setText("Please Log In to proceed");
//                }
//                break;
            default:
                // Unknown id.
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

    public void onConnected(Bundle connectionHint) {
        // We've resolved any connection errors.  mGoogleApiClient can be used to
        // access Google APIs on behalf of the user.
        getProfileInformation();
        Intent a = new Intent(this, MainActivity.class);
        a.putExtra("email", email);
        a.putExtra("name", name);
        startActivity(a);
        mConnectionProgressDialog.dismiss();

        // Hide the sign in button, show the sign out buttons.
        findViewById(R.id.sign_in_button).setVisibility(View.INVISIBLE);
    }


    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                name = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                Log.e(" ", "Name: " + name + ", plusProfile: "
                                + personGooglePlusProfile + ", email: " + email
                );
//
//                txtName.setText(personName);
//                txtEmail.setText(email);

                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
//                personPhotoUrl = personPhotoUrl.substring(0,
//                        personPhotoUrl.length() - 2)
//                        + PROFILE_PIC_SIZE;
//
//                new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Background Async task to load user profile picture from url
     */
//    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public LoadProfileImage(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }
    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }
}


/**
 * A helper method to flip the mResolveOnFail flag and start the resolution
 * of the ConnenctionResult from the failed connect() call.

 }
 }
 }**/