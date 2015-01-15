package app.cloud9.com.cloud9;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by shubhamkanodia on 15/01/15.
 * A common class for fetching Marks and Notice
 * data depending on mode
 * Cheatsheet
 * ----------------------------
 *  Constructor          :      JsonGetter(Context context, String url, String mode);
 *  Process Json         :      void parseJson();
 *  Get student's name   :      String getStudentName();
 *  Get student's usn    :      String getStudentUSN();
 *  Get subject list     :      ArrayList<String> getSubjectList() ;
 *  Get marks json       :      JSONObject getAllMarks();
 */
public class JsonGetter {
    String json_marks;
    String json_notice;
    String url;
    Context context;
    HttpClient client;
    String basicStudentInformation;
    JSONObject full_json;
    String getter_mode;

    SharedPreferences pref;
    SharedPreferences.Editor editor;


/* Constructor:
                Must be initialised with two arguments -
                1) Current activity context
                2)  mode : MODE_MARKS  or  MODE_NOTICE; */

    public JsonGetter(Context c, String m) {

        //Initialize variables
        context = c;
        full_json = null;
        getter_mode = m;

        //Set right URL automatically according to mode specified
        url = getter_mode == "MODE_MARKS" ? context.getResources().getString(R.string.marks_url) : context.getResources().getString(R.string.notice_url);

        //Initialize shared preference
        pref = context.getSharedPreferences("cache", 0);
        editor = pref.edit();


    }

    //Method to check if a user is online or offline
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Method for processing Json : MUST BE CALLED before any getters are called.
    public void parseJson() throws IOException, JSONException {

        if (isNetworkAvailable()) {

            Log.e("JsonGetter", "Network is available, trying to fetch data from server.");

            StringBuilder sb_url = new StringBuilder(url);
            HttpGet get = new HttpGet(url.toString());
            HttpResponse r = client.execute(get);

            int status = r.getStatusLine().getStatusCode();

            if (status == 200) {

                Log.e("JsonGetter", "Server is up, JSON fetched.");

                HttpEntity e = r.getEntity();
                String data = EntityUtils.toString(e);
                full_json = new JSONObject(data);

                //Refresh cache : shared preferences

                if (getter_mode == "MODE_MARKS") {
                    editor.putString("marks_cache", data); // Storing string
                } else {
                    editor.putString("notice_cache", data); // Storing string

                }


            } else {
                Log.e("JsonGetter", "Could not fetch json from server. Network Error: " + status);
            }
        }

        if (full_json == null) {
            Log.e("JsonGetter", "Network not available (Or server down) ! Trying to access shared preferences instead.");

            full_json = getter_mode == "MODE_MARKS" ?
                    new JSONObject(pref.getString("marks_cache", null)) : new JSONObject(pref.getString("marks_cache", null));
        }

    }

//Getters for Marks: parseJSON MUST BE CALLED prior to any of the below methods

    public String getStudentName() {
        try {
            return new JSONObject( full_json.getString("basic_info") ).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getStudentUSN(){
        try {
            return new JSONObject( full_json.getString("basic_info") ).getString("usn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getSubjectList() {
        ArrayList<String> subjectList  = new ArrayList<String>();

        try {

            Iterator keys = full_json.getJSONObject("marks").keys();
            int i = 0;

            while (keys.hasNext()) {
                // loop to get the dynamic key

                String currentDynamicKey = (String) keys.next();
                subjectList.add(i, currentDynamicKey);
                i++;
            }

            return subjectList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getAllMarks(){
        try {
            return full_json.getJSONObject("marks");
        }

        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    //TODO: Getters for Notice


}