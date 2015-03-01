package app.cloud9.com.cloud9;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import me.alexrs.wavedrawable.WaveDrawable;

/**
 * Created by chirag on 19/11/14.
 */
public class AboutPage extends ActionBarActivity {

    ImageView shub;
    ImageView sou;
    ImageView chi;

    private WaveDrawable waveDrawable;
    private WaveDrawable waveDrawable1;
    private WaveDrawable waveDrawable2;

    private static double TENSION = 800;
    private static double DAMPER = 20; //friction

    private SpringSystem mSpringSystem0;
    private Spring mSpring0;

    private SpringSystem mSpringSystem1;
    private Spring mSpring1;

    private SpringSystem mSpringSystem2;
    private Spring mSpring2;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_about);


        Toolbar toolbar = (Toolbar) findViewById(R.id.c9_toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Meet the team");
//        toolbar.setTitleTextColor(Color.WHITE);

        shub = (ImageView) findViewById(R.id.shub_img);
        sou = (ImageView) findViewById(R.id.sou_img);
        chi = (ImageView) findViewById(R.id.chir_img);

        final ShimmerTextView s = (ShimmerTextView) findViewById(R.id.shub_name);
        final ShimmerTextView s1 = (ShimmerTextView) findViewById(R.id.sou_name);
        final ShimmerTextView s2 = (ShimmerTextView) findViewById(R.id.chir_name);


        final Shimmer shimmer = new Shimmer();
        final Shimmer shimmer1 = new Shimmer();
        final Shimmer shimmer2 = new Shimmer();

        waveDrawable = new WaveDrawable(Color.parseColor("#8e44ad"), 75);
        shub.setBackgroundDrawable(waveDrawable);
        waveDrawable.setWaveInterpolator(new BounceInterpolator());
        waveDrawable.startAnimation();

        waveDrawable1 = new WaveDrawable(Color.parseColor("#8e44ad"), 75);
        sou.setBackgroundDrawable(waveDrawable1);
        waveDrawable1.setWaveInterpolator(new AccelerateDecelerateInterpolator());
        waveDrawable1.startAnimation();

        waveDrawable2 = new WaveDrawable(Color.parseColor("#8e44ad"), 75);
        chi.setBackgroundDrawable(waveDrawable2);
        waveDrawable2.setWaveInterpolator(new BounceInterpolator());
        waveDrawable2.startAnimation();

        //Shubham
        mSpringSystem0 = SpringSystem.create();
        mSpring0 = mSpringSystem0.createSpring();
        mSpring0.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);

                shub.setScaleX(scale);
                shub.setScaleY(scale);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });

        SpringConfig config = new SpringConfig(TENSION, DAMPER);
        mSpring0.setSpringConfig(config);

        shub.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                shimmer.start(s);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mSpring0.setEndValue(1f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mSpring0.setEndValue(0f);
                        return true;
                }

                return false;
            }
        });

        //Shubham end

        //SOu start
        mSpringSystem1 = SpringSystem.create();
        mSpring1 = mSpringSystem1.createSpring();
        mSpring1.setSpringConfig(config);

        sou.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                shimmer1.start(s1);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mSpring1.setEndValue(1f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mSpring1.setEndValue(0f);
                        return true;
                }

                return false;
            }
        });

        mSpring1.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);

                sou.setScaleX(scale);
                sou.setScaleY(scale);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });

        mSpring1.setSpringConfig(config);


        //

        //Chi start
        mSpringSystem2 = SpringSystem.create();
        mSpring2 = mSpringSystem2.createSpring();
        mSpring2.setSpringConfig(config);

        chi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                shimmer2.start(s2);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mSpring2.setEndValue(1f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mSpring2.setEndValue(0f);
                        return true;
                }

                return false;
            }
        });

        mSpring2.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);

                chi.setScaleX(scale);
                chi.setScaleY(scale);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });

        mSpring2.setSpringConfig(config);


        //chi end


//        shub.setOnTouchListener(this);
//        mSpringSystem = SpringSystem.create();
//
//        mSpring = mSpringSystem.createSpring();
//        mSpring.addListener(this);
//
//        SpringConfig config = new SpringConfig(TENSION, DAMPER);
//        mSpring.setSpringConfig(config);


    }


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mSpring.setEndValue(1f);
//                return true;
//            case MotionEvent.ACTION_UP:
//                mSpring.setEndValue(0f);
//                return true;
//        }
//
//        return false;
//    }

//    @Override
//    public void onSpringUpdate(Spring spring) {
//        float value = (float) spring.getCurrentValue();
//        float scale = 1f - (value * 0.5f);
//
//        shub.setScaleX(scale);
//        shub.setScaleY(scale);
//    }
//
//    @Override
//    public void onSpringAtRest(Spring spring) {
//
//    }
//
//    @Override
//    public void onSpringActivate(Spring spring) {
//
//    }
//
//    @Override
//    public void onSpringEndStateChange(Spring spring) {
//
//    }
}
