package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.TransactionSearchDataDTO;

import java.util.List;

public class TransactionSearchResponse extends BaseApiResponse{

    @SerializedName("data")
    @Expose
    List<TransactionSearchDataDTO> data;

    public List<TransactionSearchDataDTO> getData() {
        return data;
    }

    public void setData(List<TransactionSearchDataDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TransactionSearchResponse{" +
                "data=" + data +
                '}';
    }
}
