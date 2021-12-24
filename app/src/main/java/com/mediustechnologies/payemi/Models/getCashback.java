package com.mediustechnologies.payemi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getCashback {
    @SerializedName("cashback_amount")
    @Expose
    private String cashback_amount;

    public String getCashback_amount() {
        return cashback_amount;
    }

    public void setCashback_amount(String cashback_amount) {
        this.cashback_amount = cashback_amount;
    }
}
