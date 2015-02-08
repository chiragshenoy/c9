package app.cloud9.com.cloud9;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class NoticeViewer extends ActionBarActivity {


    TextView notice_subject;
    TextView notice_body;
    ProgressDialog mProgressDialog;
    TextView attachments;
    DownloadTask downloadTask;
    String[] attachments_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_viewer);
        Bundle b = getIntent().getExtras();

        notice_subject = (TextView) findViewById(R.id.notice_subject);
        notice_body = (TextView) findViewById(R.id.notice_body);
        attachments = (TextView) findViewById(R.id.attachments);


        String subject = b.getString("Subject");
        String text = b.getString("Text");
        String path = b.getString("Path");

        //Stripping [ ]
        path = path.substring(1, path.length() - 1);

        //Case of no attachments
        if (path != "")
            attachments_array = path.split(",");

//        Toast.makeText(this, attachments_array[0] + " bobo " + attachments_array[1], Toast.LENGTH_LONG).show();

        notice_subject.setText(subject);
        notice_body.setText(text);

        attachments.setTextSize(20);
        attachments.setText("Click to download Attachments");

        //Stripping additional " " Not sure if required.
        for (int j = 0; j < attachments_array.length; j++) {
            if (attachments_array[j] != "")
                attachments_array[j] = attachments_array[j].substring(1, attachments_array[j].length() - 1);
        }


        attachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadTask = new DownloadTask(NoticeViewer.this);

                //HardCoded 2 links
                //downloadTask.execute("https://www.cl.cam.ac.uk/teaching/2001/DSAlgs/dsa.pdf","http://elearning.vtu.ac.in/17/e-Notes/10CS54/Unit1-KRA.pdf");
                downloadTask.execute(new String[]{"https://www.cl.cam.ac.uk/teaching/2001/DSAlgs/dsa.pdf", "http://farm1.static.flickr.com/114/298125983_0e4bf66782_b.jpg"});

                //Based on structure of path, extendable to the working version
                //downloadTask.execute(attachments_array);
            }
        });


// instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(NoticeViewer.this);
        mProgressDialog.setMessage("Downloading...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);

// execute this when the downloader must be fired


        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });
        //

    }

    //AsyncTask
// usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            for (int i = 0; i < sUrl.length; i++) {
                try {
                    String extension = "";

                    URL url = new URL(sUrl[i]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    // expect HTTP 200 OK, so we don't mistakenly save error report
                    // instead of the file
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        return "Server returned HTTP " + connection.getResponseCode()
                                + " " + connection.getResponseMessage();
                    }

                    // this will be useful to display download percentage
                    // might be -1: server did not report the length
                    int fileLength = connection.getContentLength();

                    if (url.toString().contains("doc")) {
                        extension = ".doc";
                    } else if (url.toString().contains(".pdf")) {
                        extension = ".pdf";
                    } else if (url.toString().contains(".jpg")) {
                        extension = ".jpg";
                    }

                    String root = Environment.getExternalStorageDirectory().toString();
                    File myDir = new File(root + "/Cloud9");
                    myDir.mkdirs();

                    String fname = "attachment" + i + extension;
                    File file = new File(myDir, fname);


                    // download the file
                    input = connection.getInputStream();

                    //Need to specify proper name
                    output = new FileOutputStream(file);

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        // allow canceling with back button
                        if (isCancelled()) {
                            input.close();
                            return null;
                        }
                        total += count;
                        // publishing the progress....
                        if (fileLength > 0) // only if total length is known
                            publishProgress((int) (total * 100 / fileLength));
                        output.write(data, 0, count);
                    }
                } catch (Exception e) {
                    return e.toString();
                } finally {
                    try {
                        if (output != null)
                            output.close();
                        if (input != null)
                            input.close();
                    } catch (IOException ignored) {
                    }

                    if (connection != null)
                        connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        }
    }
//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notice_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
