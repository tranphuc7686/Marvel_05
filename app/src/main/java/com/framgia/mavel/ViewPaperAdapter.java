package com.framgia.mavel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Admin on 31/01/2018.
 */

public class ViewPaperAdapter extends FragmentStatePagerAdapter {

    public ViewPaperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
            {
                frag = new FragmentListHero();

                break;
            }
            case 1:
            {
                frag = new FragmentFavHero();
                break;
            }

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
        switch (position){
            case 0:
                title = "Hero Marvel";
                break;
            case 1:
                title = "Favorite Hero";
                break;

        }
        return title;
    }


}
