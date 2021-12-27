package com.mediustechnologies.payemi.Models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class sendOTPResponse {
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("payload")
    @Expose
    var payload: String? = null
}