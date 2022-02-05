package com.mediustechnologies.payemi.commons;

import com.google.gson.JsonObject;
import com.mediustechnologies.payemi.DTO.billFetchDTO;
import com.mediustechnologies.payemi.activities.apiBody.*;
import com.mediustechnologies.payemi.ApiResponse.*;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface API {

    @POST("sendotp/")
    Call<sendOTPResponse> sendOTP (@Query("phone")String phone,@Query("source")String source);

    @POST("checkotp/")
    Call<verifyOTPresponse> checkOTP (@Query("phone")String phone, @Query("otp")String otp);

    @GET("getbilldetails/")
    Call<List<billFetchDTO>> getBillDetails(@Header("Authorization") String token, @Query("bill_id") String bill_id);

    @POST("billfetch/")
    Call<fetchBill> billfetch (@Header("Authorization") String token, @Query("biller_id")String Id_biller, @Query("mobile") String mobile, @Body JsonObject body);

    @GET("get-all-banks/")
    Call<banklistResponse> getAllBanks(@Header("Authorization")String token,@Query("loan_category_id")String loan_category);

    @GET("get-biller-by-bank/")
    Call<getBillerByBank> getBillerByBank (@Header("Authorization")String token, @Query("bank")String name);

    @GET("ifnew/")
    Call<ifNewUser> checkfornewUser(@Query("phone") String phone);

    @GET("inputparam-fields/")
    Call<inputParameterFeilds> inputparameterfeilds (@Header("Authorization") String token,@Query("biller_id")String billerId);

    @POST("homepage/")
    Call<List<homePage>> homepage (@Header("Authorization") String token,@Query("mobile")String mobile,@Query("profile")String id);

    @POST("refreshtoken/")
    Call<RefreshTokenResponse> refreshToken (@Body RefreshToken refreshToken);

    @GET("getcashback/")
    Call<getCashback> getCashback (@Header("Authorization") String token,@Query("bill_id")String bill_id,@Query("profile_id")String profile_id);

    @POST("ScratchCard/")
    Call<RedeemScratchCard> redeemscratch (@Header("Authorization") String token,@Query("id")String id,@Query("bill_id")String bill_id);

    @GET("alltransactions/")
    Call<List<TransactionDetails>> allTransaction (@Header("Authorization") String token, @Query("id") String id, @Query("biller_id") String biller_id);

    @GET("getloandetails/")
    Call<fetchBill> getLoanDetails(@Header("Authorization") String token,@Query("loan_id") String loan_id,@Query("id") String id);

    @GET("getLoanCategory/")
    Call<List<loancategory>> getLoanCategory(@Header("Authorization")String token);

    @POST("addMissingLoanData/")
    Call<String> addMissingInfo(@Header("Authorization") String token,
                                @Query("loan_acc_no")String loan_acc_no,
                                @Query("loan_type") String loan_type,
                                @Query("loan_amount") String loan_amount,
                                @Query("emi")String emi,
                                @Query("start_month")String month,
                                @Query("start_year") String year );


}
