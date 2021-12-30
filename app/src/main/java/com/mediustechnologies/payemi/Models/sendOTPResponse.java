package com.mediustechnologies.payemi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class sendOTPResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("payload")
    @Expose
    private String payload;

    public boolean getNew_user() {
        return new_user;
    }

    public void setNew_user(boolean new_user) {
        this.new_user = new_user;
    }

    @SerializedName("new_user")
    @Expose
    private boolean new_user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
