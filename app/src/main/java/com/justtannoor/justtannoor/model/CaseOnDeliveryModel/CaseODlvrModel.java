
package com.justtannoor.justtannoor.model.CaseOnDeliveryModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CaseODlvrModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private Response mResponse;
    @SerializedName("status")
    private String mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response response) {
        mResponse = response;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
