package com.mediustechnologies.payemi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class verifyOTPresponse {

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @SerializedName("otp")
    @Expose
    private String otp;


}
