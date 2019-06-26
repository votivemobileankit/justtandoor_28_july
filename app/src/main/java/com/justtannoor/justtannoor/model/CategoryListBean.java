package com.justtannoor.justtannoor.model;

/**
 * Created by votive on 10/04/18.
 */

public class CategoryListBean {

    public CategoryListBean(String title, int image,String desc) {
        this.title = title;
        this.image = image;
        this.desc = desc;
    }

    private String title;
    private String desc;



    private int image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


}
