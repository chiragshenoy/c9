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
                return new Subject1();
            case 1:
                return new Subject2();
            case 2:
                return new Subject3();
            case 3:
                return new Subject4();
            case 4:
                return new Subject5();
            case 5:
                return new Subject6();
            case 6:
                return new Subject7();
            case 7:
                return new Subject8();
            case 8:
                return new Subject9();
            case 9:
                return new Subject10();

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