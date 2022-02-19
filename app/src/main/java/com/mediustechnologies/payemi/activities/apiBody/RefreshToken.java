package com.mediustechnologies.payemi.activities.apiBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshToken {

    @SerializedName("refresh")
    @Expose

    private String refreshtoken;

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }

    public RefreshToken(String refreshtoken){
        this.refreshtoken = refreshtoken;


    }

}
