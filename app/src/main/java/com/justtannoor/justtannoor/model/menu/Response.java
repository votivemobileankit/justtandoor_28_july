
package com.justtannoor.justtannoor.model.menu;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("order_limit")
    private String mOrderLimit;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("product_arabic_title")
    private String mProductArabicTitle;
    @SerializedName("product_id")
    private Long mProductId;
    @SerializedName("product_image")
    private String mProductImage;
    @SerializedName("product_name")
    private String mProductName;
    @SerializedName("product_type")
    private String mProductType;
    @SerializedName("type")
    private Long mType;

    public String getOrderLimit() {
        return mOrderLimit;
    }

    public void setOrderLimit(String orderLimit) {
        mOrderLimit = orderLimit;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getProductArabicTitle() {
        return mProductArabicTitle;
    }

    public void setProductArabicTitle(String productArabicTitle) {
        mProductArabicTitle = productArabicTitle;
    }

    public Long getProductId() {
        return mProductId;
    }

    public void setProductId(Long productId) {
        mProductId = productId;
    }

    public String getProductImage() {
        return mProductImage;
    }

    public void setProductImage(String productImage) {
        mProductImage = productImage;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getProductType() {
        return mProductType;
    }

    public void setProductType(String productType) {
        mProductType = productType;
    }

    public Long getType() {
        return mType;
    }

    public void setType(Long type) {
        mType = type;
    }

}
