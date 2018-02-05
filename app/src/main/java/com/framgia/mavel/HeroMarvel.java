package com.framgia.mavel;

/**
 * Created by Admin on 30/01/2018.
 */

public class HeroMarvel {
    private String id,nameOfHero,imageHero,descriptionOfHero,isFav ;

    public HeroMarvel() {

    }

    public HeroMarvel(String id, String nameOfHero, String imageHero, String descriptionOfHero) {
        this.id = id;
        this.nameOfHero = nameOfHero;
        this.imageHero = imageHero;
        this.descriptionOfHero = descriptionOfHero;
    }

    public String getIsFav() {

        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HeroMarvel(String id, String nameOfHero, String imageHero, String descriptionOfHero,String isFav ) {

        this.id = id;
        this.nameOfHero = nameOfHero;
        this.imageHero = imageHero;
        this.descriptionOfHero = descriptionOfHero;
        this.isFav = isFav;
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
