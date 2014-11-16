package app.cloud9.com.cloud9;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

/**
 * Created by chirag on 15/11/14.
 */
public class Marks extends Activity {

    HttpClient client;
    final static String URL = "http://dev.isharath.com/data";
    JSONObject json;
    TextView welcome;
    ListView subjects_listview;
    private ArrayAdapter<String> listAdapter;
    ArrayList<String> subjectList;
    ArrayList<String> marks_to_be_passed;
    String name = "";
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marks);
        b = new Bundle();
        welcome = (TextView) findViewById(R.id.welcome);

        client = new DefaultHttpClient();
        new Read().execute();

        subjects_listview = (ListView) findViewById(R.id.list);


        subjectList = new ArrayList<String>();
        listAdapter = new ArrayAdapter<String>(this, R.layout.subject_row, subjectList);

        subjects_listview.setAdapter(listAdapter);

        subjects_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent a = new Intent(Marks.this, DisplayMarksAndAttendance.class);
                b.putString("subject_name", subjectList.get(position));
                a.putExtras(b);
                startActivity(a);
            }
        });
    }


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
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            return null;

        }
    }

    public class Read extends AsyncTask<String, Integer, String> {
        /**
         * progress dialog to show user that the backup is processing.
         */
        private ProgressDialog dialog = new ProgressDialog(Marks.this);

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
                String string_marks = json.getString("marks");
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
            welcome.setText("Welcome " + name.toUpperCase());
            listAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

}

