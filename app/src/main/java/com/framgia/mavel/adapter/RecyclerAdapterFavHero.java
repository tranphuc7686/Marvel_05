package com.framgia.mavel.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.mavel.R;
import com.framgia.mavel.activity.InformationHero;
import com.framgia.mavel.bean.HeroMarvel;
import com.framgia.mavel.fragment.FavHero;
import com.framgia.mavel.fragment.ListHero;
import com.framgia.mavel.model.SqliteHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 03/03/2018.
 */

public class RecyclerAdapterFavHero extends RecyclerView.Adapter<
        RecyclerAdapterFavHero.RecyclerViewHolder> {

    private ArrayList<HeroMarvel> data = new ArrayList<>();
    private Context context;
    private SqliteHelper mSqliteHelper;
    private AppCompatActivity mAppCompatActivity;
    private FavHero mFavHero;
    private ListHero mListHero;
    public static final String ACTION_REFESHLISTVIEW = "LOADLISTVIEW";
    public final static String ACTION_REFESHICONLISTVIEW = "refesflisftview";

    private ArrayList<String> idHeroFav;

    public RecyclerAdapterFavHero(ArrayList<HeroMarvel> data,
                                  Context context,
                                  AppCompatActivity appCompatActivity) {
        this.data = data;
        this.context = context;
        this.mAppCompatActivity = appCompatActivity;
        mSqliteHelper = new SqliteHelper(mAppCompatActivity);
        mSqliteHelper.creatDatabase();
        idHeroFav = mSqliteHelper.getFavHero();
        loadDataFav(idHeroFav);
        createBroadcastReceiver();

    }
    public void unstallReceiver() {
        mAppCompatActivity.unregisterReceiver(
                mListHero.getmBroadcastReceiver());
        mAppCompatActivity.unregisterReceiver(
                mFavHero.getmBroadcastReceiver());
    }

    private void loadDataFav(ArrayList<String> a) {

        for (int i = 0; i <= a.size() - 1; i++) {
            for (int j = 0; j <= this.data.size() - 1; j++)
            {
                if (this.data.get(j).getId().compareTo(a.get(i)) == 0) {
                    {
                        this.data.get(j).setIsFav(1);
                    }

                }
            }

        }

    }

    public void createBroadcastReceiver() {

        mFavHero = new FavHero();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_REFESHLISTVIEW);
        mAppCompatActivity.registerReceiver(mFavHero.getmBroadcastReceiver(), intentFilter);

        mListHero = new ListHero();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(ACTION_REFESHICONLISTVIEW);
        mAppCompatActivity.registerReceiver(mListHero.getmBroadcastReceiver(), intentFilter2);

    }

    @Override
    public RecyclerAdapterFavHero.RecyclerViewHolder
    onCreateViewHolder(
            ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_item_listhero, parent,
                false);


        return new RecyclerAdapterFavHero.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder,
                                 final int position) {
        holder.mNameOfHero.setText(data.get(position).getNameOfHero());
        Picasso.with(context)
                .load(data.get(position).getImageHero() + "/landscape_medium.jpg")
                .placeholder(R.mipmap.ic_erro_image)
                .into(holder.imageHero);
        if (data.get(position).getIsFav() == 1) {

            holder.favIcon.setVisibility(View.VISIBLE);
        } else {
            holder.favIcon.setVisibility(View.GONE);


        }

        holder.imageHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InformationHero.class);
                intent.putExtra("Hero", (Parcelable) data.get(position));
                view.getContext().startActivity(intent);
            }
        });

        holder.imageHero.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (data.get(position).getIsFav() == 1) {
                    mSqliteHelper.insertDontLikeHero(data.get(position).getId());
                    Toast.makeText(view.getContext(),
                            "Đã bỏ thích : " + data.get(position).getNameOfHero(),
                            Toast.LENGTH_SHORT).show();
                    //load lai listView FavHero
                    Intent intent = new Intent();
                    intent.setAction(RecyclerAdapter.ACTION_REFESHLISTVIEW);
                    mAppCompatActivity.sendBroadcast(intent);
                    //load lai listViewHero
                    Intent intent2 = new Intent();
                    intent2.putExtra("HeroChange", data.get(position));
                    intent2.setAction(ACTION_REFESHICONLISTVIEW);

                    mAppCompatActivity.sendBroadcast(intent2);


                    data.get(position).setIsFav(0);
                    idHeroFav.remove(data.get(position).getId());
                    loadDataFav(idHeroFav);
                    holder.favIcon.setVisibility(View.INVISIBLE);


                }


                return true;
            }
        });

        holder.isRecyclable();
    }

    public void setFilter(ArrayList<HeroMarvel> tempData) {
        this.data = new ArrayList<HeroMarvel>();
        this.data.addAll(tempData);

        notifyDataSetChanged();
    }

    public void setFavHero(ArrayList<HeroMarvel> tempData) {
        for (HeroMarvel temp : tempData) {
            temp.setIsFav(1);
        }
        this.data = new ArrayList<HeroMarvel>();
        this.data.addAll(tempData);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mNameOfHero;
        ImageView imageHero, favIcon;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mNameOfHero = (TextView) itemView.findViewById(R.id.nameOfHero);
            imageHero = (ImageView) itemView.findViewById(R.id.imageHero);
            favIcon = itemView.findViewById(R.id.favIcon);

        }
    }


}

