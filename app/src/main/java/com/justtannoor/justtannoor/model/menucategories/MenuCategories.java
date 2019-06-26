
package com.justtannoor.justtannoor.model.menucategories;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MenuCategories {

    @SerializedName("background_image")
    private String mBackgroundImage;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private List<Response> mResponse;
    @SerializedName("status")
    private String mStatus;

    public String getBackgroundImage() {
        return mBackgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        mBackgroundImage = backgroundImage;
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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
