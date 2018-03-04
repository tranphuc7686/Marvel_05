package com.framgia.mavel.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
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


        // play video intro
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);

        mVideoView.setMediaController(mediaController);
        Uri uriVideo = Uri.parse("android.resource://"
                + getPackageName()
                + "/"
                + R.raw.marveintro);
        mVideoView.setVideoURI(uriVideo);
        mVideoView.start();
        mVideoView.requestFocus();

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                erroVideoIntro(mContext);

                return true;
            }
        });

        if (checkInternetConnection()) {
            ProcessGetDataJson processGetDataJson = new ProcessGetDataJson(mContext,
                    0);
            processGetDataJson.execute("http://gateway.marvel.com/v1/public/"
                    + "comics?ts=1&apikey=b97eaf6930abef3f4d3b6560be8d1957"
                    + "&hash=e543b1ad490af1740ad8c83fbaa55eb2");
        } else {
            Toast.makeText(mContext, "Kiem tra ket noi internet", Toast.LENGTH_SHORT)
                    .show();
            mAppCompatActivity.finish();
        }


    }

    private void erroVideoIntro(Context context) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Lỗi Thiết Bị.");
        builder1.setCancelable(true);


        builder1.setNegativeButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mVideoView.setVisibility(View.INVISIBLE);
                        mProgressBar.setVisibility(View.VISIBLE);
                        if (checkInternetConnection()) {

                            ProcessGetDataJson processGetDataJson = new ProcessGetDataJson(mContext,
                                    1);
                            processGetDataJson.execute(
                                    "http://gateway.marvel.com/v1/"
                                            + "public/comics?ts=1&apikey=b97eaf6930ab"
                                            + "ef3f4d3b6560be8d1957&hash=e543b1ad490af"
                                            + "1740ad8c83fbaa55eb2");
                        } else {
                            Toast.makeText(mContext, "Kiem tra ket noi internet",
                                    Toast.LENGTH_SHORT).show();
                            mAppCompatActivity.finish();
                        }
                    }
                });
        builder1.setPositiveButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private boolean checkInternetConnection() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI") &&ni.isConnected()) {
                haveConnectedWifi = true;
            }

            if (ni.getTypeName().equalsIgnoreCase("MOBILE") && ni.isConnected()) {
                    haveConnectedMobile = true;
            }

        }
        return haveConnectedWifi || haveConnectedMobile;

    }

    @Override
    protected void onStart() {
        super.onStart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    class ProcessGetDataJson extends AsyncTask<String, Void, String> {
        private Context mContext;
        private int checkErro;


        public ProcessGetDataJson(Context mContext, int codeCheckErro) {
            this.mContext = mContext;
            this.checkErro = codeCheckErro;
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

            } catch (IOException e) {

            }


            return duLieu.toString();


        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            if (checkErro == 0) {

                mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        ParseDataJson parseDataJson = new ParseDataJson();
                        SqliteHelper mSqliteHelper = new SqliteHelper(mAppCompatActivity);
                        mSqliteHelper.creatDatabase();
                        mSqliteHelper.createTable();
                        mSqliteHelper.checkDataDatabase();
                        mSqliteHelper.insertAllHero(parseDataJson.getData(s));
                        mSqliteHelper.closeDatabase();

                        Intent getActivityCharacter = new Intent(mContext,
                                CharacterHeroActivity.class);
                        startActivity(getActivityCharacter);
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                        mAppCompatActivity.finish();


                    }
                });
            } else {


                ParseDataJson parseDataJson = new ParseDataJson();
                SqliteHelper mSqliteHelper = new SqliteHelper(mAppCompatActivity);
                mSqliteHelper.creatDatabase();
                mSqliteHelper.createTable();
                mSqliteHelper.checkDataDatabase();
                mSqliteHelper.insertAllHero(parseDataJson.getData(s));
                mSqliteHelper.closeDatabase();

                Intent getActivityCharacter = new Intent(mContext, CharacterHeroActivity.class);
                startActivity(getActivityCharacter);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                mAppCompatActivity.finish();


            }


        }

    }

}
