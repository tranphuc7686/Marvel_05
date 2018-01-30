package com.framgia.mavel.service;

import android.widget.Filter;

import com.framgia.mavel.adapter.RecyclerAdapter;
import com.framgia.mavel.bean.HeroMarvel;

import java.util.ArrayList;

/**
 * Created by Admin on 21/02/2018.
 */

public class CusFilter extends Filter {
    private final RecyclerAdapter adapter;
    private ArrayList<HeroMarvel> originalList;
    private final ArrayList<HeroMarvel> filteredList;



    public CusFilter(RecyclerAdapter adapter, ArrayList<HeroMarvel> originalList) {
        super();
        this.adapter = adapter;
        this.originalList = new ArrayList<>(originalList);
        this.filteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        filteredList.clear();
        final FilterResults results = new FilterResults();

        if (charSequence.length() == 0) {
            filteredList.addAll(originalList);

        } else {

            final String filterPattern = charSequence.toString().toLowerCase().trim();

            for (HeroMarvel item : originalList) {

                if (item.getNameOfHero().toLowerCase().contains(filterPattern)) {

                    System.out.println(item.getNameOfHero());
                    filteredList.add(item);
                }
            }
            System.out.println("--------------------------------------------------");
        }

        results.values = filteredList;
        results.count = filteredList.size();
        adapter.notifyDataSetChanged();
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//        originalList.clear();
//        originalList = (ArrayList<HeroMarvel>)filterResults.values;
        System.out.println(" Run away");
        adapter.notifyDataSetChanged();
    }
}
