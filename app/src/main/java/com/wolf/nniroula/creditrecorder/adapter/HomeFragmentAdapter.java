package com.wolf.nniroula.creditrecorder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wolf.nniroula.creditrecorder.fragment.AddNewFragment;
import com.wolf.nniroula.creditrecorder.fragment.HomeFragment;
import com.wolf.nniroula.creditrecorder.fragment.ListFragment;
import com.wolf.nniroula.creditrecorder.model.RecordManager;


public class HomeFragmentAdapter extends FragmentStatePagerAdapter {

    private int position = 0;

    public HomeFragmentAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    public HomeFragmentAdapter(android.support.v4.app.FragmentManager fm, int position) {
        super(fm);
        this.position = position;
        RecordManager.initRecords();

    }

    @Override
    public Fragment getItem(int position) {
        switch (this.position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return ListFragment.newInstance();
            case 2:
                return AddNewFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (this.position) {
            case 0:
                return "SUMMARY";
            case 1:
                return "CREDITS LIST";
            case 2:
                return "ADD NEW";
        }
        return "";
    }
}
