package app.cloud9.com.cloud9;

/**
 * Created by chirag on 19/11/14.
 */

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class HomePage extends Fragment {

    Button get_marks;
    Button get_notice;
    ImageView loader;
    HttpClient client;
    String URL = "http://cloud9.cu.cc/api/chiragshenoy@gmail.com/mangoing";
//    String URL = "http://cloud9.cu.cc/api/";
//    final static String PASSWORD = "/mangoing";

//    String URL = "https://api.myjson.com/bins/347sv";

//    String URL = "https://api.myjson.com/bins/2l90b";

    //4th sem url
//    String URL = "https://api.myjson.com/bins/1u9hb";

    JSONObject json;
    TextView welcome;
    ArrayList<String> subjectList;
    String name = "";
    String usn = "";
    Bundle b;
    String string_marks;
    RelativeLayout relativeLayout;
    RelativeLayout loaderHoarding;
    Animation buttonPressAnim;
    Animation buttonReleaseAnim;
    AnimationSet welcomeAnim;
    Animation loaderPop;

    TransitionDrawable transition;
    Boolean started = false;
    String email;
    String personName;

    public HomePage() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        email = getActivity().getIntent().getStringExtra("email");
        personName = getActivity().getIntent().getStringExtra("name");


//        Toast.makeText(getActivity(), email, Toast.LENGTH_LONG).show();
//        Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();

        b = new Bundle();
        client = new DefaultHttpClient();
        subjectList = new ArrayList<String>();
        new Read().execute();

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        get_marks = (Button) rootView.findViewById(R.id.get_marks);
        get_notice = (Button) rootView.findViewById(R.id.get_notice);
        welcome = (TextView) rootView.findViewById(R.id.welcome);
        loaderHoarding = (RelativeLayout) rootView.findViewById(R.id.loader_hoarding);
        loader = (ImageView) rootView.findViewById(R.id.bms_loader);
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relative_layout);


        final Handler handler = new Handler();

        /*final Runnable r = new Runnable() {
            public void run() {
                if (!started) {
                    transition.startTransition(10000);
                    started = true;
                    handler.postDelayed(this, 10000);

                } else {
                    transition.reverseTransition(10000);
                    handler.postDelayed(this, 10000);
                    started = false;
                }
            }
        };

        handler.postDelayed(r, 0);
*/

        buttonPressAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.button_animation);
        buttonReleaseAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.button_release_animation);

        TranslateAnimation welcomeAnimTranslate = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0.0f,
                TranslateAnimation.ABSOLUTE, 0.0f,
                TranslateAnimation.ABSOLUTE, 40,
                TranslateAnimation.ABSOLUTE, 0.0f);

        final AlphaAnimation welcomeAnimFade = new AlphaAnimation(0.0f, 1.0f);

        welcomeAnim = new AnimationSet(true);
        welcomeAnim.addAnimation(welcomeAnimFade);
        welcomeAnim.addAnimation(welcomeAnimTranslate);
        welcomeAnim.setFillAfter(true);
        welcomeAnim.setDuration(900);
        welcomeAnim.setInterpolator(new DecelerateInterpolator());

        get_marks.setAnimation(welcomeAnim);
        get_notice.setAnimation(welcomeAnim);


        Animation rotateLoader = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_loader);
        //rotateLoader.setRepeatCount(Animation.INFINITE);
        //rotateLoader.setRepeatMode(Animation.REVERSE);
        loader.setDrawingCacheEnabled(true);
        loader.startAnimation(rotateLoader);

        loaderPop = AnimationUtils.loadAnimation(getActivity(), R.anim.loader_pop_fade);
        loaderPop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                welcome.setVisibility(View.VISIBLE);
                get_marks.setVisibility(View.VISIBLE);
                get_notice.setVisibility(View.VISIBLE);
                loaderHoarding.setVisibility(View.GONE);
                welcome.startAnimation(welcomeAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        welcome.setVisibility(View.GONE);
        get_marks.setVisibility(View.GONE);
        get_notice.setVisibility(View.GONE);


        get_marks.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    v.startAnimation(buttonPressAnim);

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(buttonReleaseAnim);

                }

                return false;
            }
        });

        get_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!string_marks.equals("[]")) {


                    Intent a = new Intent(getActivity(), Temp.class);
                    a.putExtra("array_list", subjectList);
                    a.putExtra("marks", string_marks);

                    startActivity(a);
                } else {
                    Toast.makeText(getActivity(), "Marks not entered", Toast.LENGTH_SHORT).show();

                }
            }
        });

        get_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    Intent b = new Intent(getActivity(), NoticeBoard.class);
                    //a.putExtra("array_list", subjectList);
                    //a.putExtra("marks", string_marks);
                    //Toast.makeText(getActivity(), "" + string_marks, Toast.LENGTH_SHORT).show();

                    startActivity(b);
                } else {
                    Toast.makeText(getActivity(), "Please connect to the Internet", Toast.LENGTH_LONG).show();

                }
            }
        });

        return rootView;
    }
    //end of oncreate


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Json Part
    public JSONObject get_entire_json() throws IOException, JSONException {

//        StringBuilder url = new StringBuilder(URL + email + PASSWORD);
        StringBuilder url = new StringBuilder(URL);
        HttpGet get = new HttpGet(url.toString());
        HttpResponse r = client.execute(get);

        int status = r.getStatusLine().getStatusCode();

        if (status == 200) {
            HttpEntity e = r.getEntity();
            String data = EntityUtils.toString(e);
            JSONObject full_json = new JSONObject(data);

            return full_json;
        } else {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            return null;

        }
    }

    public class Read extends AsyncTask<String, Integer, String> {
        /**
         * progress dialog to show user that the backup is processing.
         */


        int i = 0;

        //Only getting the list of the subjects and getting basic info
        @Override
        protected String doInBackground(String... params) {
            try {
                json = get_entire_json();
                String string_basic_info = json.getString("basic_info");
                JSONObject json_basic_info = new JSONObject(string_basic_info);

                //Get only marks Objects
                string_marks = json.getString("marks");
                b.putString("marks", string_marks);
                JSONObject json_marks = new JSONObject(string_marks);

                //Get names of the objects dynamically
                Iterator keys = json_marks.keys();

                while (keys.hasNext()) {
                    // loop to get the dynamic key

                    String currentDynamicKey = (String) keys.next();
                    subjectList.add(i, currentDynamicKey);
                    i++;
                }

                //Name Shortening
                for (String subjName : subjectList) {

                }
                name = json_basic_info.getString("name");
                usn = json_basic_info.getString("usn");

                Context context = getActivity();
                AppPrefs appPrefs = new AppPrefs(context);
                appPrefs.setUsn_saved(usn);

                b.putStringArrayList("names_of_subjects", subjectList);
                System.out.println(subjectList);

                //
                b.putString("all_marks", string_marks);

                return null;

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            welcome.setText("Welcome " + name + "!");
            //loader.clearAnimation();
            //loader.setVisibility(View.GONE);
            loader.setAnimation(loaderPop);


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }
}