package com.framgia.mavel.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.framgia.mavel.R;
import com.framgia.mavel.adapter.RecyclerAdapter;
import com.framgia.mavel.bean.HeroMarvel;
import com.framgia.mavel.model.SqliteHelper;

import java.util.ArrayList;

public class CharacterHeroActivity extends AppCompatActivity {

    private SqliteHelper mSqliteHelper;
    private Toolbar mToolbar;
    private RecyclerAdapter mRecycleviewAdaper;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private AppCompatActivity mAppCompatActivity;
    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_charater_hero);

        // Anh xa code
        mToolbar = findViewById(R.id.mToolbarHeroActi);
        mRecyclerView = findViewById(R.id.mListHero);
        mProgressBar = findViewById(R.id.progressLoadData);
        mAppCompatActivity = this;
        mContext = this;
        mDrawerLayout = findViewById(R.id.mDrawerLayout);

        //create database sqlite
        mSqliteHelper = new SqliteHelper(this);
        mSqliteHelper.creatDatabase();

        // create recyclerview
        new ProcessData().execute();

        //create toolbar
        Toolbar topToolBar = (Toolbar) findViewById(R.id.mToolbarHeroActi);
        setSupportActionBar(topToolBar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        topToolBar.setNavigationIcon(R.drawable.ic_menu_black_24dp);


        //Create Spinner brew
        createSpinner();


        //create navigation view
        createDrawerLayout();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listhero, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(item.getItemId() == R.id.btnExit ) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void createDrawerLayout() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                 mDrawerLayout,
                 R.string.open,
                 R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

    }

    public void createSpinner() {
        Spinner staticSpinner = (Spinner) findViewById(R.id.mSpinnerListHero);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.spinner_array,
                        android.R.layout.simple_spinner_item);


        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Apply the adapter to the spinner
        staticAdapter.setDropDownViewResource(R.layout.layout_items_spinner);

        staticSpinner.setAdapter(staticAdapter);
        staticSpinner.setSelection(0, true);
        View v = staticSpinner.getSelectedView();
        ((TextView) v).setTextColor(Color.WHITE);
    }

    class ProcessData extends AsyncTask<Void, Void, ArrayList<HeroMarvel>> {
        private SqliteHelper mSqliteHelper;

        @Override
        protected ArrayList<HeroMarvel> doInBackground(Void... voids) {
            mSqliteHelper = new SqliteHelper(mAppCompatActivity);
            mSqliteHelper.creatDatabase();

            return mSqliteHelper.getAllHero();
        }

        @Override
        protected void onPostExecute(ArrayList<HeroMarvel> heroMarvels) {
            super.onPostExecute(heroMarvels);
            // create recycleview

            mSqliteHelper.closeDatabase();
            mRecycleviewAdaper = new RecyclerAdapter(heroMarvels, mContext, mAppCompatActivity);


            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            mRecyclerView.setAdapter(mRecycleviewAdaper);
            mProgressBar.setVisibility(View.INVISIBLE);


        }
    }
}

