package com.ultra.pos.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ultra.pos.activity.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

public class DynamicAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList = new ArrayList<>();

    public DynamicAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titleLists) {
        super(fm);
        this.mFragmentList = fragments;
        this.mFragmentTitleList = titleLists;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
