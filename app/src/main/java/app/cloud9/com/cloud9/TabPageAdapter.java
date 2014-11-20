package app.cloud9.com.cloud9;

/**
 * Created by chirag on 20/11/14.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Chirag on 06-11-2014.
 */
public class TabPageAdapter extends FragmentStatePagerAdapter {
    public TabPageAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                //Fragement for Android Tab
                return new Day1();
            case 1:
                //Fragment for Ios Tab
                return new Day2();
        }
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 2; //No of Tabs
    }
}