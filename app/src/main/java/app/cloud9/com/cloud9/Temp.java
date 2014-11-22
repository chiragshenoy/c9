package app.cloud9.com.cloud9;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
                    }
                });
        Tab.setAdapter(TabAdapter);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9C27B0")));
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

    public String getAllMarks() {
        return all_marks_string;
    }

    //Return title of current
    public String getPageTitle(int position) {

        return arr.get(position);

    }

}