package app.cloud9.com.cloud9;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Temp extends ActionBarActivity {
    ViewPager Tab;
    TabPageAdapter TabAdapter;
    ActionBar actionBar;

    TextView tv;
    String s = "";
    ArrayList<String> arr;
    String all_marks_string;

    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workshops);
        Bundle bundle = getIntent().getExtras();
        all_marks_string = bundle.getString("marks");

        b = new Bundle();
        b.putString("all_marks", all_marks_string);

        ArrayList<String> color = null;
        final String[] color_list = {"#ffff00", "#ffff00", "#ffffff", "#00ff00", "#ff99aa", "#ff00ff", "#000000"};
        final Random r = new Random();
        r.nextInt();

        Bundle c = getIntent().getExtras();

        if (c != null) {
            arr = (ArrayList<String>) c.getStringArrayList("array_list");
        }


        Subject1 fragobj = new Subject1();
        fragobj.setArguments(b);

        TabAdapter = new TabPageAdapter(getSupportFragmentManager());
        TabAdapter.get_number_of_subject(arr.size());

        Tab = (ViewPager) findViewById(R.id.pager);

        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        //actionBar = getSupportActionBar();
                        getSupportActionBar().setSelectedNavigationItem(position);
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color_list[r.nextInt(7)])));
                    }

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                });
        Tab.setAdapter(TabAdapter);


        actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(a)));

        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {


            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
                Tab.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

            }
        };


        Bundle b = getIntent().getExtras();

        if (b != null) {
            arr = (ArrayList<String>) b.getStringArrayList("array_list");
        }

        for (int i = 0; i < arr.size(); i++)
            s += arr.get(i) + "";


        for (int i = 0; i < arr.size(); i++) {
            actionBar.addTab(actionBar.newTab().setText("" + arr.get(i)).setTabListener(tabListener));
        }

    }

    static int blendColors(int from, int to, float ratio) {
        final float inverseRation = 1f - ratio;
        final float r = Color.red(from) * ratio + Color.red(to) * inverseRation;
        final float g = Color.green(from) * ratio + Color.green(to) * inverseRation;
        final float b = Color.blue(from) * ratio + Color.blue(to) * inverseRation;
        return Color.rgb((int) r, (int) g, (int) b);
    }

    public String getAllMarks() {
        return all_marks_string;
    }

    //Return title of current
    public String getPageTitle(int position) {

        return arr.get(position);

    }

}