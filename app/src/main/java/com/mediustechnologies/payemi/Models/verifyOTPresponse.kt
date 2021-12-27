package com.mediustechnologies.payemi.Models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class verifyOTPresponse {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("otp")
    @Expose
    var otp: String? = null
}