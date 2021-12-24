package com.mediustechnologies.payemi.commons;

import com.mediustechnologies.payemi.Models.allCashbacks;
import com.mediustechnologies.payemi.Models.billDetails;
import com.mediustechnologies.payemi.Models.getCashback;
import com.mediustechnologies.payemi.Models.loginResponse;
import com.mediustechnologies.payemi.Models.verifyOTPresponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("sendotp/")
    Call<loginResponse> sendOTP (@Query("phone")String phone);

    @POST("checkotp/")
    Call<verifyOTPresponse> checkOTP (@Query("phone")String phone, @Query("otp")String otp);

    @GET("allcashback/")
    Call<allCashbacks> getallcashback(@Query("profile_id")String profile_id);

    @GET("getcashback/")
    Call<getCashback> getcashback(@Query("bill_id")String bill_id);

    @GET("getbilldetails/")
    Call<billDetails> getBillDetails(@Query("bill_id")String bill_id);

}
