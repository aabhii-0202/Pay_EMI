package com.mediustechnologies.payemi.commons;

import com.mediustechnologies.payemi.DTO.billFetchDTO;
import com.mediustechnologies.payemi.activities.apiBody.*;
import com.mediustechnologies.payemi.ApiResponse.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;


public interface API {

    @POST("sendotp/")
    Call<sendOTPResponse> sendOTP (@Query("phone")String phone);

    @POST("checkotp/")
    Call<verifyOTPresponse> checkOTP (@Query("phone")String phone, @Query("otp")String otp);

    @GET("getbilldetails/")
    Call<List<billFetchDTO>> getBillDetails(@Header("Authorization") String token, @Query("bill_id") String bill_id);

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
    Call<List<homePage>> homepage (@Header("Authorization") String token,@Query("mobile")String mobile,@Query("profile")String id);

    @POST("refreshtoken/")
    Call<RefreshTokenResponse> refreshToken (@Body RefreshToken refreshToken);

    @GET("getcashback/")
    Call<getCashback> getCashback (@Query("bill_id")String bill_id);

    @POST("ScratchCard/")
    Call<RedeemScratchCard> redeemscratch (@Header("Authorization") String token,@Query("id")String id,@Query("bill_id")String bill_id);

    @GET("alltransactions/")
    Call<List<TransactionDetails>> allTransaction (@Header("Authorization") String token, @Query("id") String id, @Query("biller_id") String biller_id);


}
