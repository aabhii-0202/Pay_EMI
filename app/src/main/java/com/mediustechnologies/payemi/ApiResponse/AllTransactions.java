package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllTransactions extends BaseApiResponse{

    @SerializedName("data")
    @Expose
    List<TransactionDetails> data;

    public List<TransactionDetails> getData() {
        return data;
    }

    public void setData(List<TransactionDetails> data) {
        this.data = data;
    }
}
