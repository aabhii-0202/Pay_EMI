package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.HomepageDTO;
import com.mediustechnologies.payemi.DTO.ShowNotificationDTO;

import java.util.List;

public class ShowNotificationResponse extends BaseApiResponse{
    @SerializedName("data")
    @Expose
    List<ShowNotificationDTO> data;

    public List<ShowNotificationDTO> getData() {
        return data;
    }

    public void setData(List<ShowNotificationDTO> data) {
        this.data = data;
    }
}
