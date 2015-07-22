package com.parse.loginsample.layoutoverride;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by oliverlewis on 7/4/15.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    //private String tabTitles[] = new String[] { "Home", "Friends", "Swipe" };
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment_home tab1 = new Fragment_home();
                return tab1;
            case 1:
                fragment_profile tab2 = new fragment_profile();
                return tab2;
            case 2:
                fragment_swipe tab3 = new fragment_swipe();
                return tab3;
            default:
                return null;
        }
    }
}
