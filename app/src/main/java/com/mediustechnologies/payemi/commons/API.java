package com.mediustechnologies.payemi.commons;

import com.mediustechnologies.payemi.Models.loginResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("sendotp/")
    Call<loginResponse> sendOTP (@Query("phone")String phone);

    @POST("checkotp/")
    Call<loginResponse> checkOTP (@Query("phone")String phone,@Query("otp")String otp);

}
