package com.framgia.mavel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

public class CharacterHeroActivity extends AppCompatActivity {
    private ProgressBar progressLoadData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charater_hero);
        progressLoadData = findViewById(R.id.progressLoadData);
       Intent a = getIntent();
        Bundle b = a.getExtras();
        progressLoadData.setVisibility(View.INVISIBLE);


    }
}
