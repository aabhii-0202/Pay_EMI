package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.billFetchDTO;

import java.util.List;

public class GetBillDetailsResponse {

    @SerializedName("data")
    @Expose
    private List<billFetchDTO> data;

    public List<billFetchDTO> getData() {
        return data;
    }

    public void setData(List<billFetchDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetBillDetailsResponse{" +
                "data=" + data +
                '}';
    }
}
