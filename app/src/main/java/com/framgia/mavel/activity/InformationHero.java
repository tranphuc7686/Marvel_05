package com.framgia.mavel.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.mavel.R;
import com.framgia.mavel.bean.HeroMarvel;
import com.squareup.picasso.Picasso;

public class InformationHero extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mTextView;
    private Context mContext;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_hero);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //anh xa
        mImageView = findViewById(R.id.iv_detail);
        mTextView = findViewById(R.id.mTextView);
        mContext = getBaseContext();
        setInfomation(getHero());
    }

    private HeroMarvel getHero() {

        return getIntent().getParcelableExtra("Hero");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setInfomation(HeroMarvel a) {

        if (a.getDescriptionOfHero().equals("")) {

            this.mTextView.setText("  Dont have tescription for this Hero ");
        } else {
            this.mTextView.setText("  " + a.getDescriptionOfHero());
        }
        Picasso.with(mContext)
                .load(a.getImageHero() + "/landscape_xlarge.jpg")
                .placeholder(R.drawable.marvel)
                .into(this.mImageView);
        this.mToolbar.setTitle(a.getNameOfHero());

    }
}
