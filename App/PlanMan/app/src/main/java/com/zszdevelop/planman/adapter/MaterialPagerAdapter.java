package com.zszdevelop.planman.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by zhangshengzhong on 16/3/11.
 */
public class MaterialPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments;
    public MaterialPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
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
            return "计划 "+ (position+1);
    }


}
