package com.justtannoor.justtannoor.model;

/**
 * Created by ADMIN on 4/13/2018.
 */

public class PackageModel {


    private String name,validity,freeList,FeaturedList,price;


    public PackageModel(String name, String validity, String freeList, String featuredList, String price) {
        this.name = name;
        this.validity = validity;
        this.freeList = freeList;
        FeaturedList = featuredList;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public void setFreeList(String freeList) {
        this.freeList = freeList;
    }

    public void setFeaturedList(String featuredList) {
        FeaturedList = featuredList;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getValidity() {
        return validity;
    }

    public String getFreeList() {
        return freeList;
    }

    public String getFeaturedList() {
        return FeaturedList;
    }

    public String getPrice() {
        return price;
    }



}
