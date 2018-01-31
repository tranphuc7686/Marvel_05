package com.framgia.mavel;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Admin on 31/01/2018.
 */

public class FragmentListHero extends Fragment {
    private RecyclerviewAdaper mRecycleviewAdaper;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_listhero, container, false);
        mRecyclerView = view.findViewById(R.id.mListHero);
        mProgressBar = view.findViewById(R.id.progressLoadData);
        // create recyclerview
        ProcessGetDataJson processGetDataJson = new ProcessGetDataJson(getContext());
        processGetDataJson.execute("http://gateway.marvel.com/v1/public/comics?ts=1&apikey=b97eaf6930abef3f4d3b6560be8d1957&hash=e543b1ad490af1740ad8c83fbaa55eb2");





        return view;
    }
    class ProcessGetDataJson extends AsyncTask<String,Void,String> {
        private Context mContext;


        public ProcessGetDataJson(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder duLieu=null;
            try {
                URL mURL = new URL(strings[0]);
                HttpURLConnection mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
                InputStream mInputStream = mHttpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(mInputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String dong = "";
                duLieu = new StringBuilder();
                while((dong = bufferedReader.readLine()) !=null){
                    duLieu.append(dong);


                }

                mInputStream.close();
                reader.close();
                bufferedReader.close();




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return duLieu.toString();





        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            ParseDataJson parseDataJson = new ParseDataJson();

            // create recycleview
            mRecycleviewAdaper = new RecyclerviewAdaper( parseDataJson.getData(s),mContext);



            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            mRecyclerView.setAdapter(mRecycleviewAdaper);
            mProgressBar.setVisibility(View.INVISIBLE);




        }
    }
}