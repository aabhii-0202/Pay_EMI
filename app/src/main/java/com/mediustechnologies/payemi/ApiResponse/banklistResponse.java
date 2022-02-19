package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class banklistResponse extends BaseApiResponse{

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<getAllBanks> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public List<getAllBanks> getData() {
        return data;
    }

    public void setData(List<getAllBanks> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "banklistResponse{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
