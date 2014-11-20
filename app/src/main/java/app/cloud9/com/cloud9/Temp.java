package app.cloud9.com.cloud9;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Temp extends ActionBarActivity {
    ViewPager Tab;
    TabPageAdapter TabAdapter;
    ActionBar actionBar;

    TextView tv;
    String s = "";
    ArrayList<String> arr;
    ActionBar.Tab tab1, tab2, tab3;
    String all_marks_string;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;


    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workshops);
        Bundle bundle = getIntent().getExtras();

        all_marks_string = bundle.getString("marks");
        b = new Bundle();
        Toast.makeText(Temp.this, "" + all_marks_string, Toast.LENGTH_SHORT).show();
        b.putString("all_marks", all_marks_string);
        Day1 fragobj = new Day1();
        fragobj.setArguments(b);

        TabAdapter = new TabPageAdapter(getSupportFragmentManager());
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
        //Add New Tab
//        actionBar.addTab(actionBar.newTab().setText("Day 1 Workshops").setTabListener(tabListener));
//        actionBar.addTab(actionBar.newTab().setText("Day 2 Workshops").setTabListener(tabListener));
    }

    public String getAllMarks() {
        return all_marks_string;
    }

    public String getAllSubjects(){
        return null;
    }

}