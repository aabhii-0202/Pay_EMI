package com.mediustechnologies.payemi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ifNewUser {
    @SerializedName("new_user")
    @Expose
    private String new_user;


    public String getNew_user() {
        return new_user;
    }

    public void setNew_user(String new_user) {
        this.new_user = new_user;
    }
}
