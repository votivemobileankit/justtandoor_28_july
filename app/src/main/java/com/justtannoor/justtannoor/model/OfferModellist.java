package com.justtannoor.justtannoor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by poonam on 5/17/2018.
 */

public class OfferModellist {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("offerimages")
    @Expose
    private String offerimages;
    @SerializedName("response")
    @Expose
    private List<OfferModelResponse> response = null;

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

    public String getOfferimages() {
        return offerimages;
    }

    public void setOfferimages(String offerimages) {
        this.offerimages = offerimages;
    }

    public List<OfferModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<OfferModelResponse> response) {
        this.response = response;
    }

}