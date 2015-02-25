package app.cloud9.com.cloud9;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
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
public class AboutPage extends ActionBarActivity implements View.OnTouchListener, SpringListener {

    ImageView shub;
    ImageView sou;
    ImageView chi;

    private WaveDrawable waveDrawable;
    private WaveDrawable waveDrawable1;
    private WaveDrawable waveDrawable2;

    private static double TENSION = 800;
    private static double DAMPER = 20; //friction

    private SpringSystem mSpringSystem;
    private Spring mSpring;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_about);

        shub = (ImageView) findViewById(R.id.shub_img);
        sou = (ImageView) findViewById(R.id.sou_img);
        chi = (ImageView) findViewById(R.id.chir_img);

        ShimmerTextView s = (ShimmerTextView) findViewById(R.id.shub_name);

        Shimmer shimmer = new Shimmer();
        shimmer.start(s);
        waveDrawable = new WaveDrawable(Color.parseColor("#8e44ad"), 500);
        shub.setBackgroundDrawable(waveDrawable);

        waveDrawable.setWaveInterpolator(new BounceInterpolator());
        waveDrawable.startAnimation();
        waveDrawable1 = new WaveDrawable(Color.parseColor("#8e44ad"), 500);
        sou.setBackgroundDrawable(waveDrawable1);
        waveDrawable1.setWaveInterpolator(new AccelerateDecelerateInterpolator());
        waveDrawable1.startAnimation();

        waveDrawable2 = new WaveDrawable(Color.parseColor("#8e44ad"), 500);
        chi.setBackgroundDrawable(waveDrawable2);
        waveDrawable2.setWaveInterpolator(new AnticipateOvershootInterpolator());
        waveDrawable2.startAnimation();

        shub.setOnTouchListener(this);
        mSpringSystem = SpringSystem.create();

        mSpring = mSpringSystem.createSpring();
        mSpring.addListener(this);

        SpringConfig config = new SpringConfig(TENSION, DAMPER);
        mSpring.setSpringConfig(config);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mSpring.setEndValue(1f);
                return true;
            case MotionEvent.ACTION_UP:
                mSpring.setEndValue(0f);
                return true;
        }

        return false;
    }

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
}
