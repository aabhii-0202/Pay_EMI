package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.GetBillerByBankDTO;

import java.util.List;

public class getBillerByBank extends BaseApiResponse{


    @SerializedName("data")
    @Expose
    private List<GetBillerByBankDTO> data;

    public List<GetBillerByBankDTO> getData() {
        return data;
    }

    public void setData(List<GetBillerByBankDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "getBillerByBank{" +
                "data=" + data +
                '}';
    }
}
