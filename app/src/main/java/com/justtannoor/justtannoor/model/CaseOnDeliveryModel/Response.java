
package com.justtannoor.justtannoor.model.CaseOnDeliveryModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Response {

    @SerializedName("admin_email_address")
    private String mAdminEmailAddress;
    @SerializedName("admin_message")
    private String mAdminMessage;
    @SerializedName("admin_mobile_number")
    private String mAdminMobileNumber;

    public String getAdminEmailAddress() {
        return mAdminEmailAddress;
    }

    public void setAdminEmailAddress(String adminEmailAddress) {
        mAdminEmailAddress = adminEmailAddress;
    }

    public String getAdminMessage() {
        return mAdminMessage;
    }

    public void setAdminMessage(String adminMessage) {
        mAdminMessage = adminMessage;
    }

    public String getAdminMobileNumber() {
        return mAdminMobileNumber;
    }

    public void setAdminMobileNumber(String adminMobileNumber) {
        mAdminMobileNumber = adminMobileNumber;
    }

}
