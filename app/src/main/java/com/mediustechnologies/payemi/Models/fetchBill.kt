package com.mediustechnologies.payemi.Models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.mediustechnologies.payemi.DTO.fetchBillDTO

class fetchBill {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("payload")
    @Expose
    var payload: fetchBillDTO? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}