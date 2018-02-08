package com.framgia.mavel.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.framgia.mavel.R;
import com.framgia.mavel.model.SqliteHelper;
import com.framgia.mavel.service.ParseDataJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private VideoView mVideoView;
    private Context mContext;
    private ProgressBar mProgressBar;
    private AppCompatActivity mAppCompatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create video intro
        mVideoView = findViewById(R.id.videoIntro);
        mProgressBar = findViewById(R.id.progressMainActivity);
        mProgressBar.setVisibility(View.INVISIBLE);

        mContext = this;
        mAppCompatActivity = this;
        Uri uriVideo = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.marveintro);
        mVideoView.setVideoURI(uriVideo);
        mVideoView.start();
        mVideoView.requestFocus();
        ProcessGetDataJson processGetDataJson = new ProcessGetDataJson(mContext);
        processGetDataJson.execute("http://gateway.marvel.com/v1/public/comics?ts=1&apikey=b97eaf6930abef3f4d3b6560be8d1957&hash=e543b1ad490af1740ad8c83fbaa55eb2");


    }

    @Override
    protected void onStart() {
        super.onStart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    class ProcessGetDataJson extends AsyncTask<String, Void, String> {
        public final Context mContext;


        public ProcessGetDataJson(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder duLieu = null;
            try {
                URL mURL = new URL(strings[0]);
                HttpURLConnection mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
                InputStream mInputStream = mHttpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(mInputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String dong = "";
                duLieu = new StringBuilder();
                while ((dong = bufferedReader.readLine()) != null) {
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
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {


                    mProgressBar.setVisibility(View.VISIBLE);
                    ParseDataJson parseDataJson=new ParseDataJson();

                    SqliteHelper mSqliteHelper=new SqliteHelper(mAppCompatActivity);
                    mSqliteHelper.creatDatabase();
                    mSqliteHelper.checkDataDatabase();
//                    mSqliteHelper.createTable();
                    mSqliteHelper.insertAllHero(parseDataJson.getData(s));
                    mSqliteHelper.closeDatabase();

                    Intent getActivityCharacter=new Intent(mContext,CharacterHeroActivity.class);
                    startActivity(getActivityCharacter);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mAppCompatActivity.finish();

                }
            });


        }

    }

}
