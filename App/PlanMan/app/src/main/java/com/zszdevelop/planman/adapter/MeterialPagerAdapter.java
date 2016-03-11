package com.zszdevelop.planman.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by zhangshengzhong on 16/3/11.
 */
public class MeterialPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments;
    public MeterialPagerAdapter(FragmentManager fm ,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position % 4) {
            case 0:
                return "Selection";
            case 1:
                return "Actualites";
            case 2:
                return "Professionnel";
            case 3:
                return "Divertissement";
        }
        return "";
    }
}
