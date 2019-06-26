package com.justtannoor.justtannoor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by poonam on 5/26/2018.
 */

public class Cdata {

    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_price")
    @Expose
    private String productPrice;

    @SerializedName("order_limit")
    @Expose
    private int orderlimit;

    @SerializedName("limit_counter")
    @Expose
    private int limitcounter;

    @SerializedName("product_arabic_title")
    @Expose
    private String productArabicTitle;


    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }


    public int getOrderLimit() {
        return orderlimit;
    }

    public void setOrderLimit(int orderlimit) {
        this.orderlimit = orderlimit;
    }

    public int getLimitCounter() {
        return limitcounter;
    }

    public void setLimitCounter(int limitcounter) {
        this.limitcounter = limitcounter;
    }

    public String getProductArabicTitle() {
        return productArabicTitle;
    }

    public void setProductArabicTitle(String productArabicTitle) {
        this.productArabicTitle = productArabicTitle;
    }
}
