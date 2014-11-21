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

    int i = 0;

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                //Fragement for Android Tab
                return new Subject1();
            case 1:
                //Fragment for Ios Tab
                return new Subject2();
            case 2:
                return new Subject3();
            case 3:
                return new Subject4();
            case 4:
                return new Subject5();
            case 5:
                return new Subject6();

        }
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return i; //No of Tabs
    }

    public void get_number_of_subject(int x) {
        i = x;
    }
}