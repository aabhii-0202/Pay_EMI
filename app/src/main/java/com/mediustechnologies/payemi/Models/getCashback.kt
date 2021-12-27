package com.mediustechnologies.payemi.Models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class getCashback {
    @SerializedName("cashback_amount")
    @Expose
    var cashback_amount: String? = null
}