package com.wolf.nniroula.creditrecorder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.activity.CreditApplication;
import com.wolf.nniroula.creditrecorder.fragment.AboutFragment;
import com.wolf.nniroula.creditrecorder.fragment.HelpFragment;


public class HelpFragmentAdapter extends FragmentStatePagerAdapter {

    private int position = 0;

    public HelpFragmentAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    public HelpFragmentAdapter(android.support.v4.app.FragmentManager fm, int position) {
        super(fm);
        this.position = position;
    }

    @Override
    public Fragment getItem(int position) {
        switch (this.position) {
            case 0: return HelpFragment.newInstance();
            case 1: return AboutFragment.newInstance();
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
            case 0: return CreditApplication.getAppContext().getResources().getString(R.string.app_name);
            case 1: return CreditApplication.getAppContext().getResources().getString(R.string.about);
        }
        return "";
    }
}
