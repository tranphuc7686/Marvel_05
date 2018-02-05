package com.framgia.mavel;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 31/01/2018.
 */

public class RecyclerviewAdaper extends RecyclerView.Adapter<RecyclerviewAdaper.RecyclerViewHolder>{

    private ArrayList<HeroMarvel> data = new ArrayList<>();
    private Context context;
    private SqliteHelper mSqliteHelper;
    private AppCompatActivity mAppCompatActivity;

    public RecyclerviewAdaper(ArrayList<HeroMarvel> data,Context context,AppCompatActivity appCompatActivity) {
        this.data = data;
        this.context= context;
        this.mAppCompatActivity = appCompatActivity;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_item_listhero, parent, false);
        mSqliteHelper = new SqliteHelper(mAppCompatActivity);
        mSqliteHelper.creatDatabase();
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.isRecyclable();
        if(data.get(position).getIsFav().compareTo("FAV")==0){
            holder.favIcon.setVisibility(View.VISIBLE);
        }
        holder.mNameOfHero.setText(data.get(position).getNameOfHero());
        String path = data.get(position).getImageHero()+"/standard_fantastic.jpg";

        Picasso.with(context)
                .load(path)
                .placeholder(R.mipmap.ic_erro_image)
                .into(holder.imageHero);
        holder.imageHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Da Click  : "+data.get(position).getNameOfHero(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.imageHero.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mSqliteHelper.insertLikeHero(data.get(position).getId());
                Toast.makeText(view.getContext(),"Đã thích : "+data.get(position).getNameOfHero(),Toast.LENGTH_SHORT).show();
                holder.favIcon.setVisibility(View.VISIBLE);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mNameOfHero;
        ImageView imageHero,favIcon;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mNameOfHero =(TextView) itemView.findViewById(R.id.nameOfHero);
            imageHero =(ImageView)itemView.findViewById(R.id.imageHero);
            favIcon = itemView.findViewById(R.id.favIcon);

        }
    }
}

