package com.framgia.mavel;

/**
 * Created by Admin on 30/01/2018.
 */

public class HeroMarvel {
    private String nameOfHero,imageHero,descriptionOfHero ;

    public HeroMarvel() {

    }

    public HeroMarvel(String nameOfHero, String imageHero, String descriptionOfHero) {

        this.nameOfHero = nameOfHero;
        this.imageHero = imageHero;
        this.descriptionOfHero = descriptionOfHero;
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
