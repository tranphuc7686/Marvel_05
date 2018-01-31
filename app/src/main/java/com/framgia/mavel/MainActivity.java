package com.framgia.mavel;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private VideoView mVideoView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create video intro
        mVideoView = findViewById(R.id.videoIntro);
        mContext = this;
        Uri uriVideo = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.marveintro);
        mVideoView.setVideoURI(uriVideo);
        mVideoView.start();
        mVideoView.requestFocus();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Intent getActivityCharacter = new Intent(mContext,CharacterHeroActivity.class);
                startActivity(getActivityCharacter);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

}
