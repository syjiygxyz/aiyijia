package com.ayj.aiyijia.adapter.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
public class CommonPagerAdapter extends FragmentStatePagerAdapter {

    List<String> titles = new ArrayList<>();

    List<Fragment> fragmentList;


    public CommonPagerAdapter(FragmentManager fm, List<String> titles , List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}