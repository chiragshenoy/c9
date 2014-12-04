package app.cloud9.com.cloud9;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.Random;

public class Temp extends ActionBarActivity {

    ActionBar actionBar;

    //    String s = "";
    ArrayList<String> arr;
    String all_marks_string;

    Bundle b;

    private final Handler handler = new Handler();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private Drawable oldBackground = null;
    private int currentColor = 0xFF009688;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workshops);

        toolbar = (Toolbar) findViewById(R.id.c9_toolbar); //Appcompat support for a sexier action bar
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);

        Bundle bundle = getIntent().getExtras();
        all_marks_string = bundle.getString("marks");
        Bundle c = getIntent().getExtras();

        final String[] color_list = getResources().getStringArray(R.array.subjectMainColors);
        final String[] accent_list = getResources().getStringArray(R.array.subjectAccentColors);
        if (c != null) {
            arr = (ArrayList<String>) c.getStringArrayList("array_list");
        }


        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setBackground(new ColorDrawable(Color.parseColor(color_list[0])));
        tabs.setIndicatorColor(Color.parseColor(accent_list[0]));
        tabs.setIndicatorColor(0xFFFFFFFF);
        tabs.setTextColor(0xAAFFFFFF);
        tabs.setDividerColor(Color.argb(0, 0, 0, 0));
        tabs.setAllCaps(true);
        tabs.setIndicatorHeight(2);
        tabs.setIndicatorColor(Color.argb(20, 255, 255, 255));
        tabs.setUnderlineColor(Color.argb(0, 0, 0, 0));
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);


        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int i) {
                int color = Color.parseColor(color_list[i]);
                changeColor(color);
                tabs.setBackground(new ColorDrawable(Color.parseColor(color_list[i])));
                //tabs.setIndicatorColor(Color.parseColor(accent_list[i]));
                tabs.setTextColor(0xFFFFFFFF);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

                tabs.setIndicatorHeight(2);

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
        });


        changeColor(currentColor);

        //Supported only for Lolipop and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(0xFF00695C);

        b = new Bundle();
        b.putString("all_marks", all_marks_string);

        final Random r = new Random();
        r.nextInt();

        actionBar = getSupportActionBar();


        Bundle b = getIntent().getExtras();

        if (b != null) {
            arr = (ArrayList<String>) b.getStringArrayList("array_list");
        }

    }


    public String getAllMarks() {
        return all_marks_string;
    }

    //Return title of current
    public String getPageTitle(int position) {

        return arr.get(position);

    }


    public int statusbarColorchanger(int color) {

        switch (color) {
            case 0xFF009688:
                return 0xFF00796B;
            case 0xFF00BCD4:
                return 0xFF00ACC1; //Note this change
            case 0xFF2196F3:
                return 0xFF1976D2;
            case 0xFF3F51B5:
                return 0xFF303F9F;
            case 0xFF673AB7:
                return 0xFF512DA8;
            case 0xFF9C27B0:
                return 0xFF7B1FA2;
        }

        return 0;
    }

    private void changeColor(int newColor) {

        System.out.println("trying to change color to :" + newColor);

        // change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(newColor);
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});

            if (oldBackground == null) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ld.setCallback(drawableCallback);
                } else {
                    toolbar.setBackgroundDrawable(new ColorDrawable(newColor));
                }

            } else {

                TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});

                // workaround for broken ActionBarContainer drawable handling on
                // pre-API 17 builds
                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    td.setCallback(drawableCallback);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int new_status_color = statusbarColorchanger(newColor);
                    getWindow().setStatusBarColor(new_status_color);

                    //getWindow().setStatusBarColor(newColor);
                    toolbar.setBackgroundDrawable(new ColorDrawable(newColor));
                } else {
                    //getWindow().setStatusBarColor(newColor);
                    toolbar.setBackgroundDrawable(new ColorDrawable(newColor));
                }

                td.startTransition(200); //action bar transitions but the hoarding changes color abruptly
                //td.startTransition(0); //maybe figure out a  better method to do this

            }

            oldBackground = ld;

            // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler

//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            getSupportActionBar().setDisplayShowTitleEnabled(true);

        }

        currentColor = newColor;

    }

    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            toolbar.setBackgroundDrawable(who);
            //getSupportActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return arr.get(position);
        }

        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Fragment getItem(int i) {

            SubjectFragment s = new SubjectFragment();
            Bundle args = new Bundle();
            args.putInt("index", i);
            s.setArguments(args);
            return s;
        }

    }
}
