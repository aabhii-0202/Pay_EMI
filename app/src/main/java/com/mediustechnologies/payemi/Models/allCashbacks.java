package com.mediustechnologies.payemi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class allCashbacks {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("cashback_amountcashback_amount")
    @Expose
    private String cashback_amount;

    @SerializedName("status")
    @Expose
    private String status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCashback_amount() {
        return cashback_amount;
    }

    public void setCashback_amount(String cashback_amount) {
        this.cashback_amount = cashback_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
