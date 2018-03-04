package com.framgia.mavel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.framgia.mavel.fragment.FavHero;
import com.framgia.mavel.fragment.ListHero;

/**
 * Created by Admin on 23/02/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private AppCompatActivity mAppCompatActivity;

    public ViewPagerAdapter(FragmentManager fm, AppCompatActivity mAppCompatActivity) {
        super(fm);
        this.mAppCompatActivity = mAppCompatActivity;

    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:

                frag = new ListHero(mAppCompatActivity);
                break;
            case 1:
                frag = new FavHero();
                break;

        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Hero";
                break;
            case 1:
                title = "Favior Hero";
                break;

        }

        return title;
    }

    @Override
    public int getItemPosition(Object object) {
// POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }

}