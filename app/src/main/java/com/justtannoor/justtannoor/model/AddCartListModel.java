package com.justtannoor.justtannoor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by poonam on 5/31/2018.
 */

public class AddCartListModel {



    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("counter")
    @Expose
    private CounterResponse counter;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("response")
    @Expose
    private List<AddCartListResponse> response = null;

  /*"discount_price": "2500.00",
   "total_offer_percentage": "10",
   "minimum_order_price": "1500.00",*/

    @SerializedName("total_offer_percentage")
    @Expose
    private int total_offer_percentage;


    @SerializedName("minimum_order_price")
    @Expose
    private int minimum_order_price;


    @SerializedName("discount_price")
    @Expose
    private int discount_price;

    public int getTotaldiscountprice() {
        return discount_price;
    }

    public void setTotaldiscountprice(int discount_price) {
        this.discount_price = discount_price;
    }

    public int getMinimumorderprice() {
        return minimum_order_price;
    }

    public void setMinimumorderprice(int minimum_order_price) {
        this.minimum_order_price = minimum_order_price;
    }


    public int getTotalofferpercentage() {
        return total_offer_percentage;
    }

    public void setTotalofferpercentage(int total_offer_percentage) {
        this.total_offer_percentage = total_offer_percentage;
    }





    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CounterResponse getCounter() {
        return counter;
    }

    public void setCounter(CounterResponse counter) {
        this.counter = counter;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<AddCartListResponse> getResponse() {
        return response;
    }

    public void setResponse(List<AddCartListResponse> response) {
        this.response = response;
    }
}


