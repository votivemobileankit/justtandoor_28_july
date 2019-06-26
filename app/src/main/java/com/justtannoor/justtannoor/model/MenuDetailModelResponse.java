package com.justtannoor.justtannoor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by poonam on 5/26/2018.
 */

public class MenuDetailModelResponse {
    @SerializedName("cdata")
    @Expose
    private Cdata cdata;
    @SerializedName("foodtype")
    @Expose
    private List<Foodtype> foodtype = null;
    @SerializedName("includeditems")
    @Expose
    private List<String> includeditems = null;

    public Cdata getCdata() {
        return cdata;
    }

    public void setCdata(Cdata cdata) {
        this.cdata = cdata;
    }

    public List<Foodtype> getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(List<Foodtype> foodtype) {
        this.foodtype = foodtype;
    }

    public List<String> getIncludeditems() {
        return includeditems;
    }

    public void setIncludeditems(List<String> includeditems) {
        this.includeditems = includeditems;
    }


}
