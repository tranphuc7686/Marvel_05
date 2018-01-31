package com.framgia.mavel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 31/01/2018.
 */

public class RecyclerviewAdaper extends RecyclerView.Adapter<RecyclerviewAdaper.RecyclerViewHolder>{

    private ArrayList<HeroMarvel> data = new ArrayList<>();
    private Context context;

    public RecyclerviewAdaper(ArrayList<HeroMarvel> data,Context context) {
        this.data = data;
        this.context= context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_item_listhero, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.isRecyclable();
        holder.mNameOfHero.setText(data.get(position).getNameOfHero());
        String path = data.get(position).getImageHero()+"/standard_fantastic.jpg";
        System.out.println(path);
        Picasso.with(context)
                .load(path)
                .placeholder(R.mipmap.ic_erro_image)
                .into(holder.imageHero);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mNameOfHero;
        ImageView imageHero;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mNameOfHero =(TextView) itemView.findViewById(R.id.nameOfHero);
            imageHero =(ImageView)itemView.findViewById(R.id.imageHero);
        }
    }
}

