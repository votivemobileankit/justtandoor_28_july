package com.justtannoor.justtannoor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by poonam on 5/26/2018.
 */

public class MenuDetailModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("counter")
    @Expose
    private CounterResponse counter;
    @SerializedName("response")
    @Expose
    private MenuDetailModelResponse response;

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

    public MenuDetailModelResponse getResponse() {
        return response;
    }

    public void setResponse(MenuDetailModelResponse response) {
        this.response = response;
    }

}