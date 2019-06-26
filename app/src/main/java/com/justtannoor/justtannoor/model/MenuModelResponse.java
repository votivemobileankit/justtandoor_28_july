package com.justtannoor.justtannoor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by poonam on 5/26/2018.
 */

public class MenuModelResponse {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("type")
    @Expose
    private Integer type;

    @SerializedName("number_of_peaces")
    @Expose
    private String numberofPeaces;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getNumberofPeaces() {
        return numberofPeaces;
    }

    public void setNumberofPeaces(String numberofPeaces) {
        this.numberofPeaces = numberofPeaces;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}



