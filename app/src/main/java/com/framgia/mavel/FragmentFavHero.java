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

public class FragmentFavHero extends Fragment {

    public static RecyclerviewAdaper mRecycleviewAdaper;
    private RecyclerView mRecyclerView;
    public static ProgressBar mProgressBar;

    private SqliteHelper mSqliteHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_favhero, container, false);

        mRecyclerView = view.findViewById(R.id.mListHeroFav);
        mProgressBar = view.findViewById(R.id.progressLoadDataFav);

        mSqliteHelper = new SqliteHelper((AppCompatActivity)getActivity());

        new ProcessFavHeroData().execute();





        return view;
    }
    class ProcessFavHeroData extends AsyncTask<Void,Void,ArrayList<HeroMarvel>> {


        @Override
        protected ArrayList<HeroMarvel> doInBackground(Void... voids) {

            mSqliteHelper.creatDatabase();
            return  mSqliteHelper.getFavHero();
        }

        @Override
        protected void onPostExecute(ArrayList<HeroMarvel> heroMarvels) {
            super.onPostExecute(heroMarvels);
            // create recycleview

            mRecycleviewAdaper = new RecyclerviewAdaper(heroMarvels,getActivity().getBaseContext(),(AppCompatActivity)getActivity());



            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getBaseContext(), 2));
            mRecyclerView.setAdapter(mRecycleviewAdaper);
            mProgressBar.setVisibility(View.INVISIBLE);
            mSqliteHelper.closeDatabase();



        }
    }

}