package com.justtannoor.justtannoor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by poonam on 5/26/2018.
 */

public class Foodtype {
    @SerializedName("foodname")
    @Expose
    private String foodname;
    @SerializedName("foodvalue")
    @Expose
    private List<String> foodvalue = null;

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public List<String> getFoodvalue() {
        return foodvalue;
    }

    public void setFoodvalue(List<String> foodvalue) {
        this.foodvalue = foodvalue;
    }

}
