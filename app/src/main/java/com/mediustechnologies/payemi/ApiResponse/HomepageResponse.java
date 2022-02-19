package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.HomepageDTO;

import java.util.List;

public class HomepageResponse extends BaseApiResponse{

    @SerializedName("data")
    @Expose
    List<HomepageDTO> data;

    public List<HomepageDTO> getData() {
        return data;
    }

    public void setData(List<HomepageDTO> data) {
        this.data = data;
    }
}
