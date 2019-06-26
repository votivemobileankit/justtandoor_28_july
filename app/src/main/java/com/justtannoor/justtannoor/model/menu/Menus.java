
package com.justtannoor.justtannoor.model.menu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Menus {

    @SerializedName("limit_counter")
    private Long mLimitCounter;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<Response> mResponse;
    @SerializedName("sliderimage")
    private List<String> mSliderimage;
    @SerializedName("status")
    private String mStatus;

    public Long getLimitCounter() {
        return mLimitCounter;
    }

    public void setLimitCounter(Long limitCounter) {
        mLimitCounter = limitCounter;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Response> getResponse() {
        return mResponse;
    }

    public void setResponse(List<Response> response) {
        mResponse = response;
    }

    public List<String> getSliderimage() {
        return mSliderimage;
    }

    public void setSliderimage(List<String> sliderimage) {
        mSliderimage = sliderimage;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
