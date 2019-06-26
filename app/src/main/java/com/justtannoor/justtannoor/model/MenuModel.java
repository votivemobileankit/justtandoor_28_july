package com.justtannoor.justtannoor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by poonam on 5/26/2018.
 */

public class MenuModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sliderimage")
    @Expose
    private List<String> sliderimage = null;
    @SerializedName("response")
    @Expose
    private List<MenuModelResponse> response = null;

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

    public List<String> getSliderimage() {
        return sliderimage;
    }

    public void setSliderimage(List<String> sliderimage) {
        this.sliderimage = sliderimage;
    }

    public List<MenuModelResponse> getResponse() {
        return response;
    }

    public void setResponse(List<MenuModelResponse> response) {
        this.response = response;
    }

}
