package app.cloud9.com.cloud9;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Chirag on 26-02-2015.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent a = new Intent(getApplicationContext(), LoginPage.class);
                    startActivity(a);
                }

            }
        };
        timer.start();
    }
}
