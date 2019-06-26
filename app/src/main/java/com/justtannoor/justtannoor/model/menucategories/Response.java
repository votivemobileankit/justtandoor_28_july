
package com.justtannoor.justtannoor.model.menucategories;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("arabic_term_name")
    private String mArabicTermName;
    @SerializedName("term_id")
    private Long mTermId;
    @SerializedName("term_image")
    private String mTermImage;
    @SerializedName("term_name")
    private String mTermName;

    public String getArabicTermName() {
        return mArabicTermName;
    }

    public void setArabicTermName(String arabicTermName) {
        mArabicTermName = arabicTermName;
    }

    public Long getTermId() {
        return mTermId;
    }

    public void setTermId(Long termId) {
        mTermId = termId;
    }

    public String getTermImage() {
        return mTermImage;
    }

    public void setTermImage(String termImage) {
        mTermImage = termImage;
    }

    public String getTermName() {
        return mTermName;
    }

    public void setTermName(String termName) {
        mTermName = termName;
    }

}
