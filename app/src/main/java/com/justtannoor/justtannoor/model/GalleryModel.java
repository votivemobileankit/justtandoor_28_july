package com.justtannoor.justtannoor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by poonam on 5/11/2018.
 */

public class GalleryModel implements Serializable{
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
    private List<GalleryResponse> response = null;

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

    public List<GalleryResponse> getResponse() {
        return response;
    }

    public void setResponse(List<GalleryResponse> response) {
        this.response = response;
    }


}
