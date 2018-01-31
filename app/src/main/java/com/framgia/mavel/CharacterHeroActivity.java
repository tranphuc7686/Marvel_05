package com.framgia.mavel;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class CharacterHeroActivity extends AppCompatActivity {
    private ProgressBar progressLoadData;
    private Context mContext;

    private ViewPager mPager;
    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charater_hero);

        // Anh xa code

        mPager = findViewById(R.id.mViewPaper);
        mTabLayout = findViewById(R.id.mTabHost);
        //create Viewpaper and tabhost
        FragmentManager manager = getSupportFragmentManager();
        ViewPaperAdapter adapter = new ViewPaperAdapter(manager);
        mPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setTabsFromPagerAdapter(adapter);
        setupTabIcons(mTabLayout);
    }
    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_android_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_assignment_black_24dp);

    }



    }

