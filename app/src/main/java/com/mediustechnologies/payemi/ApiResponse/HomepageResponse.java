package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.HomepageDTO;

import java.util.List;

public class HomepageResponse extends BaseApiResponse{

    @SerializedName("data")
    @Expose
    List<HomepageDTO> data;

    @SerializedName("new_notification_count")
    @Expose
    int new_notification_count;

    public int getNew_notification_count() {
        return new_notification_count;
    }

    public void setNew_notification_count(int new_notification_count) {
        this.new_notification_count = new_notification_count;
    }

    public List<HomepageDTO> getData() {
        return data;
    }

    public void setData(List<HomepageDTO> data) {
        this.data = data;
    }
}
