package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.ProfileInfoDataDTO;

import java.util.List;

public class ProfileInfoResponse extends BaseApiResponse{

    @SerializedName("data")
    @Expose
    private List<ProfileInfoDataDTO> data;

    public List<ProfileInfoDataDTO> getData() {
        return data;
    }

    public void setData(List<ProfileInfoDataDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProfileInfoResponse{" +
                "data=" + data +
                '}';
    }
}
