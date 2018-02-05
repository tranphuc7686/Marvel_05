package com.framgia.mavel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * Created by Admin on 31/01/2018.
 */

public class FragmentListHero extends Fragment {
    private RecyclerviewAdaper mRecycleviewAdaper;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    public static ArrayList<HeroMarvel> dataHeroJson;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_listhero, container, false);
        mRecyclerView = view.findViewById(R.id.mListHero);
        mProgressBar = view.findViewById(R.id.progressLoadData);
        // create recyclerview
         new ProcessData().execute();






        return view;
    }
    class ProcessData extends AsyncTask<Void,Void,ArrayList<HeroMarvel>>{
        private SqliteHelper mSqliteHelper ;

        @Override
        protected ArrayList<HeroMarvel> doInBackground(Void... voids) {
            mSqliteHelper = new SqliteHelper((AppCompatActivity)getActivity());
            mSqliteHelper.creatDatabase();

            return  mSqliteHelper.getAllHero();
        }

        @Override
        protected void onPostExecute(ArrayList<HeroMarvel> heroMarvels) {
            super.onPostExecute(heroMarvels);
            // create recycleview
            mSqliteHelper.closeDatabase();
            mRecycleviewAdaper = new RecyclerviewAdaper(heroMarvels,getActivity().getBaseContext(),(AppCompatActivity)getActivity());



            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(), 2));
            mRecyclerView.setAdapter(mRecycleviewAdaper);
            mProgressBar.setVisibility(View.INVISIBLE);



        }
    }


}