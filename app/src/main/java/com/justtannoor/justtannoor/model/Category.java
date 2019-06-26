package com.justtannoor.justtannoor.model;

/**
 * Created by ADMIN on 4/7/2018.
 */
public class Category {

    public Category(String title, int image) {
        this.title = title;
        this.image = image;
    }

    private String title;

    public Category(int image) {
        this.image = image;
    }

    private int image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


}
