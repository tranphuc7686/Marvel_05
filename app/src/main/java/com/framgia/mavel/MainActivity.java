package com.framgia.mavel;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create video intro
        mVideoView = findViewById(R.id.videoIntro);
        Uri uriVideo = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.marveintro);
        mVideoView.setVideoURI(uriVideo);
        mVideoView.start();
        ProcessGetDataJson processGetDataJson = new ProcessGetDataJson(this);
        processGetDataJson.execute("http://gateway.marvel.com/v1/public/comics?ts=1&apikey=b97eaf6930abef3f4d3b6560be8d1957&hash=e543b1ad490af1740ad8c83fbaa55eb2");


//







    }

    @Override
    protected void onStart() {
        super.onStart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    class ProcessGetDataJson extends AsyncTask<String,Void,String>{
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
            System.out.println(s);
            Intent pushDataJson = new Intent(MainActivity.this,CharacterHeroActivity.class);
            pushDataJson.putExtra("DataJson",s);
            startActivity(pushDataJson);



        }
    }
}
