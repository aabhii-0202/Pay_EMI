package com.mediustechnologies.payemi.Models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class AllCashbacks {
    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("cashback_amountcashback_amount")
    @Expose
    var cashback_amount: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null
}