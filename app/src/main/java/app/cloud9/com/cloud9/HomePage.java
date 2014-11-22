package app.cloud9.com.cloud9;

/**
 * Created by chirag on 19/11/14.
 */

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    HttpClient client;
    final static String URL = "https://api.myjson.com/bins/4y3gb";
    JSONObject json;
    TextView welcome;
    ArrayList<String> subjectList;
    String name = "";
    String usn = "";
    Bundle b;
    String string_marks;
    RelativeLayout relativeLayout;

    TransitionDrawable transition;
    Boolean started = false;


    public HomePage() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = new Bundle();
        client = new DefaultHttpClient();
        subjectList = new ArrayList<String>();
        new Read().execute();

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        get_marks = (Button) rootView.findViewById(R.id.get_marks);
        welcome = (TextView) rootView.findViewById(R.id.welcome);
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relative_layout);

        transition = (TransitionDrawable) relativeLayout.getBackground();

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
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


        get_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), Temp.class);
                a.putExtra("array_list", subjectList);
                a.putExtra("marks", string_marks);
                //Toast.makeText(getActivity(), "" + string_marks, Toast.LENGTH_SHORT).show();

                startActivity(a);
            }
        });
        return rootView;
    }
    //end of oncreate


    //Json Part
    public JSONObject get_entire_json() throws IOException, JSONException {

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
        private ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

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
                name = json_basic_info.getString("name");
                usn = json_basic_info.getString("usn");

                b.putStringArrayList("names_of_subjects", subjectList);
                //
                b.putString("all_marks", string_marks);
                Subject1 fragobj = new Subject1();
                fragobj.setArguments(b);

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
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            welcome.setText(name + " " + usn);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }
}