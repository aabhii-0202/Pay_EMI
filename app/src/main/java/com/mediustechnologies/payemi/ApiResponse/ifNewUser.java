package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ifNewUser extends BaseApiResponse{
    @SerializedName("new_user")
    @Expose
    private boolean new_user;


    public boolean getNew_user() {
        return new_user;
    }

    public void setNew_user(boolean new_user) {
        this.new_user = new_user;
    }
}
