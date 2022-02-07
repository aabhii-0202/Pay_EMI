package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getCashback extends BaseApiResponse{
    @SerializedName("cashback_amount")
    @Expose
    private String cashback_amount;

    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCashback_amount() {
        return cashback_amount;
    }

    public void setCashback_amount(String cashback_amount) {
        this.cashback_amount = cashback_amount;
    }
}
