package app.cloud9.com.cloud9;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by chirag on 18/11/14.
 */
public class HomePage extends ActionBarActivity {

    DrawerLayout drawerLayout;
    ListView listView;
    String[] nav_items;
    ActionBarDrawerToggle drawerListener;
    android.support.v7.app.ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        nav_items = new String[]{"Home", "Contact Us", "About"};

        actionbar = getSupportActionBar();

        listView = (ListView) findViewById(R.id.listview);

        listView.setAdapter(new ArrayAdapter(this, R.layout.subject_row, nav_items));

        //Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        //Listener Ends

        drawerListener = new android.support.v4.app.ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_opened, R.string.drawer_closed) {

            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(HomePage.this, "Opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }
        };


        drawerLayout.setDrawerListener(drawerListener);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    public void selectItem(int position) {
        listView.setItemChecked(position, true);
        set_the_title(nav_items[position]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    private void set_the_title(String nav_item) {
        getSupportActionBar().setTitle(nav_item);
    }

}
