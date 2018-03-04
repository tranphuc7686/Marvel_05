package com.framgia.mavel.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 30/01/2018.
 */

public class HeroMarvel implements Parcelable {
    private String id, nameOfHero, imageHero, descriptionOfHero;
    private int isFav;

    public HeroMarvel() {

    }

    public HeroMarvel(String id,
             String nameOfHero,
             String imageHero,
            String descriptionOfHero) {
        this.id = id;
        this.nameOfHero = nameOfHero;
        this.imageHero = imageHero;
        this.descriptionOfHero = descriptionOfHero;
    }

    public HeroMarvel(String id, String nameOfHero,
                      String imageHero
            , String descriptionOfHero,
                      int isFav) {
        this.id = id;
        this.nameOfHero = nameOfHero;
        this.imageHero = imageHero;
        this.descriptionOfHero = descriptionOfHero;
        this.isFav = isFav;
    }

    protected HeroMarvel(Parcel in) {
        id = in.readString();
        nameOfHero = in.readString();
        imageHero = in.readString();
        descriptionOfHero = in.readString();
        isFav = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nameOfHero);
        dest.writeString(imageHero);
        dest.writeString(descriptionOfHero);
        dest.writeInt(isFav);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HeroMarvel> CREATOR = new Creator<HeroMarvel>() {
        @Override
        public HeroMarvel createFromParcel(Parcel in) {
            return new HeroMarvel(in);
        }

        @Override
        public HeroMarvel[] newArray(int size) {
            return new HeroMarvel[size];
        }
    };

    public int getIsFav() {
        return isFav;
    }

    public void setIsFav(int isFav) {
        this.isFav = isFav;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getNameOfHero() {

        return nameOfHero;
    }

    public void setNameOfHero(String nameOfHero) {
        this.nameOfHero = nameOfHero;
    }

    public String getImageHero() {
        return imageHero;
    }

    public void setImageHero(String imageHero) {
        this.imageHero = imageHero;
    }

    public String getDescriptionOfHero() {
        return descriptionOfHero;
    }

    public void setDescriptionOfHero(String descriptionOfHero) {
        this.descriptionOfHero = descriptionOfHero;
    }
}
