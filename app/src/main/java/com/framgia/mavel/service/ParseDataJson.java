package com.framgia.mavel.service;

import com.framgia.mavel.bean.HeroMarvel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 31/01/2018.
 */

public class ParseDataJson {
    public ArrayList<HeroMarvel> getData(String duLieu) {
        ArrayList<HeroMarvel> arrData = new ArrayList<HeroMarvel>();

        try {
            JSONObject mJsonObject = new JSONObject(duLieu);
            JSONObject dataObject = mJsonObject.getJSONObject("data");
            JSONArray arrDataJson = dataObject.getJSONArray("results");


            for (int i = 0; i < arrDataJson.length(); i++) {


                JSONObject mJsonObjectTemp = arrDataJson.getJSONObject(i);
                JSONArray getTextObjects = mJsonObjectTemp.getJSONArray("textObjects");
                String descriptionOfHero = "";
                if (getTextObjects.length() != 0) {
                    JSONObject getTextObject = getTextObjects.getJSONObject(0);

                    descriptionOfHero = getTextObject.getString("text");

                }


                JSONObject pathJsonObject = mJsonObjectTemp.getJSONObject("thumbnail");

                arrData.add(new HeroMarvel(mJsonObjectTemp.getString("id"),
                        mJsonObjectTemp.getString("title"),
                        pathJsonObject.getString("path"),
                        descriptionOfHero));


            }


        } catch (JSONException e) {

        }


        return arrData;

    }


}
