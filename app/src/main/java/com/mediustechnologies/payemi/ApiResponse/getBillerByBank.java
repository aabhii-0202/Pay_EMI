package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getBillerByBank {


    @SerializedName("data")
    @Expose
    private List<bankSubItem> data;

    public List<bankSubItem> getData() {
        return data;
    }

    public void setData(List<bankSubItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "getBillerByBank{" +
                "data=" + data +
                '}';
    }
}
