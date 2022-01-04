package com.mediustechnologies.payemi.commons;

import com.mediustechnologies.payemi.Models.billDetails;
import com.mediustechnologies.payemi.Models.fetchBill;
import com.mediustechnologies.payemi.Models.homePage;
import com.mediustechnologies.payemi.activities.apiBody.fetchBillBody;
import com.mediustechnologies.payemi.Models.getAllBanks;
import com.mediustechnologies.payemi.Models.ifNewUser;
import com.mediustechnologies.payemi.Models.inputParameterFeilds;
import com.mediustechnologies.payemi.Models.sendOTPResponse;
import com.mediustechnologies.payemi.Models.verifyOTPresponse;
import com.mediustechnologies.payemi.Models.bankSubItem;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("sendotp/")
    Call<sendOTPResponse> sendOTP (@Query("phone")String phone);

    @POST("checkotp/")
    Call<verifyOTPresponse> checkOTP (@Query("phone")String phone, @Query("otp")String otp);

    @GET("getbilldetails/")
    Call<List<billDetails>> getBillDetails(@Header("Authorization") String token,@Query("bill_id") String bill_id);

    @POST("billfetch/")
    Call<fetchBill> fetchBill (@Header("Authorization") String token, @Query("biller_id")String Id_biller, @Query("mobile") String mobile, @Body fetchBillBody body);

    @GET("get-all-banks/")
    Call<List<getAllBanks>> getAllBanks(@Header("Authorization")String token);

    @GET("get-biller-by-bank/")
    Call<List<bankSubItem>> getBillerByBank (@Header("Authorization")String token, @Query("bank")String name);

    @GET("ifnew/")
    Call<ifNewUser> checkfornewUser(@Header("Authorization")String token,@Query("phone") String phone);

    @GET("inputparam-fields/")
    Call<inputParameterFeilds> inputparameterfeilds (@Header("Authorization") String token,@Query("biller_id")String billerId);

    @POST("homepage/")
    Call<List<homePage>> homepage (@Header("Authorization") String token,@Query("mobile")String mobile);

}
