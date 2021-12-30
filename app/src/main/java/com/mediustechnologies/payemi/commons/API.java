package com.mediustechnologies.payemi.commons;

import com.mediustechnologies.payemi.Models.allCashbacks;
import com.mediustechnologies.payemi.Models.billDetails;
import com.mediustechnologies.payemi.Models.fetchBill;
import com.mediustechnologies.payemi.Models.getAllBanks;
import com.mediustechnologies.payemi.Models.getCashback;
import com.mediustechnologies.payemi.Models.ifNewUser;
import com.mediustechnologies.payemi.Models.sendOTPResponse;
import com.mediustechnologies.payemi.Models.verifyOTPresponse;
import com.mediustechnologies.payemi.recyclerItems.bankSubItem;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("sendotp/")
    Call<sendOTPResponse> sendOTP (@Query("phone")String phone);

    @POST("checkotp/")
    Call<verifyOTPresponse> checkOTP (@Query("phone")String phone, @Query("otp")String otp);

    @GET("allcashback/")
    Call<List<allCashbacks>> getallcashback(@Query("profile_id") String profile_id);

    @GET("getcashback/")
    Call<getCashback> getcashback(@Query("bill_id")String bill_id);

    @GET("getbilldetails/")
    Call<List<billDetails>> getBillDetails(@Query("bill_id") String bill_id);

    @POST("billfetch/")
    Call<fetchBill> fetchBill (@Query("Id_biller")String Id_biller,  @Query("loanNumber")String loanNumber, @Query("mobile") String mobile);

    @GET("get-all-banks/")
    Call<List<getAllBanks>> getAllBanks();

    @GET("get-biller-by-bank/")
    Call<List<bankSubItem>> getBillerByBank (@Query("bank")String name);

    @GET("ifnew/")
    Call<ifNewUser> checkfornewUser(@Query("phone") String phone);

}
