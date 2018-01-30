package com.framgia.mavel.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.framgia.mavel.R;
import com.framgia.mavel.adapter.ViewPagerAdapter;
import com.framgia.mavel.fragment.FavHero;
import com.framgia.mavel.fragment.ListHero;
import com.framgia.mavel.model.SqliteHelper;

public class CharacterHeroActivity extends AppCompatActivity {

    private SqliteHelper mSqliteHelper;
    private Toolbar mToolbar;


    private AppCompatActivity mAppCompatActivity;
    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    public static ViewPager mViewPager;
    private ListHero mListHero;
    private FavHero mFavHero;
    public static final String ACTION_FILTERDATA = "FilterHero";
    private boolean checkIconFav;
    private Menu menu;
    private SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_charater_hero);

        // Anh xa code
        mToolbar = findViewById(R.id.mToolbarHeroActi);
        mViewPager = findViewById(R.id.mViewPaper);
        mAppCompatActivity = this;
        mContext = this;
        mDrawerLayout = findViewById(R.id.mDrawerLayout);


        //create database sqlite
        mSqliteHelper = new SqliteHelper(this);
        mSqliteHelper.creatDatabase();

        // create recyclerview


        //create toolbar
        Toolbar topToolBar = (Toolbar) findViewById(R.id.mToolbarHeroActi);

        setSupportActionBar(topToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        topToolBar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        //create navigation view
        createDrawerLayout();
        // create Viewpaper
        createViewPaper(mViewPager);
        //code change fragment
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem item = menu.findItem(R.id.btnFavActivity);

                if (position == 1) {
                    item.setIcon(R.drawable.hearts);
                }
                if (position == 0) {
                    item.setIcon(R.drawable.ic_favorite_black_24dp);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;


        getMenuInflater().inflate(R.menu.menu_listhero, menu);
        MenuItem item = menu.findItem(R.id.btnSeach);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);


        EditText editText = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        editText.setHintTextColor(getResources().getColor(R.color.white));
        editText.setTextColor(getResources().getColor(R.color.white));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // send kí tự
                Intent intent = new Intent();
                intent.putExtra("DataSearch", newText);
                intent.setAction(ACTION_FILTERDATA);
                sendBroadcast(intent);

                return false;
            }
        });

        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        //create Broadcast Receive
        createBroadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mListHero.getmBroadcastReceiver());
        unregisterReceiver(mFavHero.getmBroadcastReceiver());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.btnExit) {
            finish();


        }
        if (item.getItemId() == R.id.btnFavActivity) {
            if (checkIconFav == false) {
                item.setIcon(R.drawable.hearts);
                mViewPager.setCurrentItem(2, true);
                checkIconFav = true;
            } else {
                item.setIcon(R.drawable.ic_favorite_black_24dp);
                mViewPager.setCurrentItem(0, true);
                checkIconFav = false;
            }

        }


        return super.onOptionsItemSelected(item);
    }

    public void createViewPaper(ViewPager paper) {
        FragmentManager manager = getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(manager, this);
        paper.setAdapter(adapter);

    }

    public void createDrawerLayout() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.string.open,
                R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

    }

    public void createBroadcastReceiver() {
        mListHero = new ListHero();
        mFavHero = new FavHero();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_FILTERDATA);

        registerReceiver(mListHero.getmBroadcastReceiver(), intentFilter);
        registerReceiver(mFavHero.getmBroadcastReceiver(), intentFilter);
    }


}

