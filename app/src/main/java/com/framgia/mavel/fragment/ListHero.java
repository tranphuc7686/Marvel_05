package com.framgia.mavel.fragment;

import android.annotation.SuppressLint;
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
 * Created by Admin on 23/02/2018.
 */

@SuppressLint("ValidFragment")
public class ListHero extends Fragment {
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private static RecyclerAdapter mRecycleviewAdaper;
    private AppCompatActivity mAppCompatActivity;
    private Context mContext;
    private static ArrayList<HeroMarvel> data;


    @SuppressLint("ValidFragment")
    public ListHero(AppCompatActivity mAppCompatActivity) {
        this.mAppCompatActivity = mAppCompatActivity;
    }

    public ListHero() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_list_hero, container, false);

        //anh xa
        mRecyclerView = view.findViewById(R.id.mListHero);
        mProgressBar = view.findViewById(R.id.progressLoadData);
        mContext = getActivity().getBaseContext();

        new ProcessData().execute();


        return view;
    }


    public BroadcastReceiver getmBroadcastReceiver() {
        return mBroadcastReceiver;
    }

    public void setmBroadcastReceiver(BroadcastReceiver mBroadcastReceiver) {
        this.mBroadcastReceiver = mBroadcastReceiver;
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(CharacterHeroActivity.ACTION_FILTERDATA)) {
                String temp = intent.getStringExtra("DataSearch");
                ArrayList<HeroMarvel> a = filterMethod(ListHero.data, temp);
                mRecycleviewAdaper.setFilter(a);
            }
            if (intent.getAction().equals(RecyclerAdapterFavHero.ACTION_REFESHICONLISTVIEW)) {

                mRecycleviewAdaper.setIconFav((HeroMarvel) intent.getParcelableExtra("HeroChange"));
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecycleviewAdaper.unstallReceiver();
    }

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
            data = heroMarvels;
            mSqliteHelper.closeDatabase();
            mRecycleviewAdaper = new RecyclerAdapter(heroMarvels, mContext, mAppCompatActivity); mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            mRecyclerView.setAdapter(mRecycleviewAdaper);
            mProgressBar.setVisibility(View.INVISIBLE);


        }
    }
}
