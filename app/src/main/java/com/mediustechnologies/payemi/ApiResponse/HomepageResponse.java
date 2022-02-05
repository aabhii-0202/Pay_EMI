package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomepageResponse {

    @SerializedName("data")
    @Expose
    List<homePage> data;

    public List<homePage> getData() {
        return data;
    }

    public void setData(List<homePage> data) {
        this.data = data;
    }
}
