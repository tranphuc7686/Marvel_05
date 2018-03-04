package com.framgia.mavel.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.framgia.mavel.R;
import com.framgia.mavel.activity.CharacterHeroActivity;
import com.framgia.mavel.adapter.RecyclerAdapter;
import com.framgia.mavel.adapter.RecyclerAdapterFavHero;
import com.framgia.mavel.bean.HeroMarvel;
import com.framgia.mavel.model.SqliteHelper;

import java.util.ArrayList;

/**
 * Created by Admin on 22/02/2018.
 */

public class FavHero extends Fragment {
    private AppCompatActivity mAppCompatActivity;
    private static RecyclerView mRecyclerView;
    private Context mContext;
    private static RecyclerAdapterFavHero mRecycleviewAdaper;
    private ProgressBar mProgressBar;
    private static ArrayList<HeroMarvel> data;
    private static SqliteHelper mSqliteHelper;
    private ListHero mListHero;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_fav_hero, container, false);

        //anh xa
        mAppCompatActivity = ((AppCompatActivity) getActivity());
        mContext = getContext();
        mRecyclerView = view.findViewById(R.id.mListFavHero);
        mProgressBar = view.findViewById(R.id.progressLoadFavHero);
        new ProcessGetFavHeroData().execute();


        return view;
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(CharacterHeroActivity.ACTION_FILTERDATA)) {
                String temp = intent.getStringExtra("DataSearch");

                ArrayList<HeroMarvel> a = filterMethod(FavHero.data, temp);
                mRecycleviewAdaper.setFilter(a);
            }
            if (intent.getAction().equals(RecyclerAdapter.ACTION_REFESHLISTVIEW)) {
                mSqliteHelper.creatDatabase();

                mRecycleviewAdaper.setFavHero(mSqliteHelper.getAllFavHero());

            }
        }
    };

    private ArrayList<HeroMarvel> filterMethod(ArrayList<HeroMarvel> data, String query) {
        ArrayList<HeroMarvel> resulf = new ArrayList<HeroMarvel>();

        for (HeroMarvel temp : data) {
            String text = temp.getNameOfHero().toLowerCase();
            if (text.startsWith(query)) {

                resulf.add(temp);
            }
        }
        return resulf;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecycleviewAdaper.unstallReceiver();
    }

    public BroadcastReceiver getmBroadcastReceiver() {
        return mBroadcastReceiver;
    }

    class ProcessGetFavHeroData extends AsyncTask<Void, Void, ArrayList<HeroMarvel>> {


        @Override
        protected ArrayList<HeroMarvel> doInBackground(Void... voids) {
            mSqliteHelper = new SqliteHelper(mAppCompatActivity);
            mSqliteHelper.creatDatabase();

            return mSqliteHelper.getAllFavHero();
        }

        @Override
        protected void onPostExecute(ArrayList<HeroMarvel> heroMarvels) {
            super.onPostExecute(heroMarvels);
            // create recycleview
            data = heroMarvels;
            mSqliteHelper.closeDatabase();
            mRecycleviewAdaper = new RecyclerAdapterFavHero(heroMarvels,
                    mContext, mAppCompatActivity);
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            mRecyclerView.setAdapter(mRecycleviewAdaper);
            mProgressBar.setVisibility(View.INVISIBLE);
            mRecycleviewAdaper.setFavHero(heroMarvels);


        }
    }

}
