package com.mediustechnologies.payemi.commons;

import android.graphics.Bitmap;
import android.util.Pair;

import androidx.annotation.CallSuper;

import com.google.gson.JsonObject;
import com.mediustechnologies.payemi.DTO.billFetchDTO;
import com.mediustechnologies.payemi.activities.apiBody.*;
import com.mediustechnologies.payemi.ApiResponse.*;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface API {

    @POST("sendotp/")
    Call<sendOTPResponse> sendOTP (@Query("phone")String phone,
                                   @Query("source")String source);

    @POST("resendotp/")
    Call<sendOTPResponse> resendOTP (@Query("phone")String phone,
                                   @Query("source")String source);

    @POST("checkotp/")
    Call<verifyOTPresponse> checkOTP (@Query("phone")String phone,
                                      @Query("otp")String otp);

    @GET("getbilldetails/")
    Call<GetBillDetailsResponse> getBillDetails(@Header("Authorization") String token,
                                                @Query("bill_id") String bill_id);

    @POST("billfetch/")
    Call<fetchBill> billfetch (@Header("Authorization") String token,
                               @Query("id")String Id,
                               @Query("biller_id")String Id_biller,
                               @Query("mobile") String mobile,
                               @Body JsonObject body);

    @GET("get-all-banks/")
    Call<banklistResponse> getAllBanks(@Header("Authorization")String token,
                                       @Query("loan_category_id")String loan_category);

    @GET("get-biller-by-bank/")
    Call<getBillerByBank> getBillerByBank (@Header("Authorization")String token,
                                           @Query("bank")String name);

    @GET("ifnew/")
    Call<ifNewUser> checkfornewUser(@Query("phone") String phone);

    @GET("inputparam-fields/")
    Call<inputParameterFeilds> inputparameterfeilds (@Header("Authorization") String token,
                                                     @Query("biller_id")String billerId);

    @POST("homepage/")
    Call<HomepageResponse> homepage (@Header("Authorization") String token,
                                     @Query("mobile")String mobile,
                                     @Query("profile")String id);

    @POST("refreshtoken/")
    Call<RefreshTokenResponse> refreshToken (@Body RefreshToken refreshToken);

    @GET("getcashback/")
    Call<getCashback> getCashback (@Header("Authorization") String token,
                                   @Query("bill_id")String bill_id,
                                   @Query("profile_id")String profile_id);

    @POST("ScratchCard/")
    Call<RedeemScratchCard> redeemscratch (@Header("Authorization") String token,
                                           @Query("id")String id,
                                           @Query("bill_id")String bill_id);

    @GET("alltransactions/")
    Call<AllTransactions> allTransaction (@Header("Authorization") String token,
                                          @Query("id") String id,
                                          @Query("biller_id") String biller_id);

    @GET("getloandetails/")
    Call<fetchBill> getLoanDetails(@Header("Authorization") String token,
                                   @Query("loan_id") String loan_id,
                                   @Query("id") String id);

    @GET("getLoanCategory/")
    Call<List<loancategory>> getLoanCategory(@Header("Authorization")String token);

    @POST("addMissingLoanData/")
    Call<AddMissingCategoryResponse> addMissingInfo(@Header("Authorization") String token,
                                @Query("loan_acc_no")String loan_acc_no,
                                @Query("loan_type") String loan_type,
                                @Query("loan_amount") String loan_amount,
                                @Query("emi")String emi,
                                @Query("start_month")String month,
                                @Query("start_year") String year );

    @POST("registerComplaint/")
    Call<RegisterComplaintResponse> registerComplaint(@Header("Authorization") String token,
                                                      @Query("complaint_disposition")String complaint_disposition,
                                                      @Query("complaint_description")String complaint_description,
                                                      @Query("complaint_type")String complaint_type,
                                                      @Query("transaction_ref_id")String transaction_ref_id );


    @POST("trackComplaint/")
    Call<RegisterComplaintResponse> trackComplaint(@Header("Authorization") String token,
                                                   @Query("comp_id") String comp_id,
                                                   @Query("comp_type") String comp_type);


    @POST("transactionSearch/")
    Call<TransactionSearchResponse> transactionSearchwithMobile(@Header("Authorization") String token,
                                                                @Query("mobile_no") String mobile_no,
                                                                @Query("start_date") String start_date,
                                                                @Query("end_date") String end_date);


    @POST("transactionSearch/")
    Call<TransactionSearchResponse> transactionSearchWithRefId(@Header("Authorization") String token,
                                                               @Query("transaction_ref_id") String transaction_ref_id);

    @GET("profileInfo/")
    Call <ProfileInfoResponse> profileInfo(@Header("Authorization") String token,
                                           @Query("phone_number") String phone_number);

    @Multipart
    @POST("profileInfo/")
    Call<ProfileInfoResponse> updateProfilePic(@Header("Authorization") String token,
                                                @Query("phone_number") String phone_number,
                                                @Part MultipartBody.Part image);

    @POST("profileInfo/")
    Call<ProfileInfoResponse> updateProfileInfo(@Header("Authorization") String token,
                                                @Query("phone_number") String phone_number,
                                                @Body JsonObject body);

    @GET("getPaymentReceiptPDFNoauth/")
    Call<DownloadBillResponse> download (@Header("Authorization") String token,@Query("bill_id") String bill_id);

    @POST("CreateOrder-noauth/")
    Call<CreateOrderIdResponse> getRazorpayOrderId (@Header("Authorization") String token,
                                                    @Query("id") String id,
                                                    @Query("bill_id") String bill_id,
                                                    @Query("amount") int amount,
                                                    @Query("notes") String notes
    );

    @GET("shownotification/")
    Call<ShowNotificationResponse> showNotificatoin (
            @Header("Authorization") String token,
            @Query("phone_number") String phone_number
    );

    @POST("clearAllNotification/")
    Call<BaseApiResponse> clearAllNotification(@Header("Authorization") String token,
                                               @Body HashMap<String,List<String>> ids
                                               );

    @POST("seenNotification/")
    Call<BaseApiResponse> seenNotification(@Header("Authorization") String token,
                                           @Body HashMap<String,List<String>> ids
                                           );

    @GET("getHelpCategory/")
    Call<GetHelpCatagoryResponse> getHelpCatagory(@Header("Authorization") String token);

    @POST("getHelpSubCategory/")
    Call<HelpSubCatagoryResponse> getHelpSubCategory(@Header("Authorization") String token,
                                                     @Body HashMap<String,String> catagory
                                                    );

    @POST("getHelpQuestionAnswer/")
    Call<GetHelpQuestionAnswer> GetHelpQuestionAnswer (@Header("Authorization") String token,
                                                       @Body HashMap<String,String> sub_category
                                                      );

}
