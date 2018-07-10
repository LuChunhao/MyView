package com.example.myview.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class CommonTabPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> list;
    private Context context;

    public CommonTabPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> list, Context context) {
        super(fm);
        if (list==null||list.isEmpty()){
            throw new ExceptionInInitializerError("list can't be null or empty");
        }
        this.fragmentList = fragmentList;
        this.list = list;
        this.context = context;
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
        return list.get(position);
    }



}
