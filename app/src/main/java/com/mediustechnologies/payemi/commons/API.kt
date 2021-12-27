package com.mediustechnologies.payemi.commons

import retrofit2.http.POST
import com.mediustechnologies.payemi.Models.sendOTPResponse
import com.mediustechnologies.payemi.Models.verifyOTPresponse
import retrofit2.http.GET
import com.mediustechnologies.payemi.Models.AllCashbacks
import com.mediustechnologies.payemi.Models.getCashback
import com.mediustechnologies.payemi.Models.billDetails
import com.mediustechnologies.payemi.Models.fetchBill
import retrofit2.Call
import retrofit2.http.Query

interface API {
    @POST("sendotp/")
    fun sendOTP(@Query("phone") phone: String?): Call<sendOTPResponse?>?

    @POST("checkotp/")
    fun checkOTP(
        @Query("phone") phone: String?,
        @Query("otp") otp: String?
    ): Call<verifyOTPresponse?>?

    @GET("allcashback/")
    fun getallcashback(@Query("profile_id") profile_id: String?): Call<List<AllCashbacks?>?>?

    @GET("getcashback/")
    fun getcashback(@Query("bill_id") bill_id: String?): Call<getCashback?>?

    @GET("getbilldetails/")
    fun getBillDetails(@Query("bill_id") bill_id: String?): Call<List<billDetails?>?>?

    @POST("billfetch/")
    fun fetchBill(
        @Query("Id_biller") Id_biller: String?,
        @Query("loanNumber") loanNumber: String?,
        @Query("mobile") mobile: String?
    ): Call<fetchBill?>?
}