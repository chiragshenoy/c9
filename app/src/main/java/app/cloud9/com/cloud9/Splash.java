package app.cloud9.com.cloud9;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Chirag on 26-02-2015.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

//        Thread timer = new Thread() {
//            public void run() {
//                try {
//                    sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    Intent a = new Intent(getApplicationContext(), LoginPage.class);
//                    startActivity(a);
//                }
//
//            }
//        };
//        timer.start();
//    }

        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                Intent a = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(a);
            }
        });
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);


        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);

    }

}
