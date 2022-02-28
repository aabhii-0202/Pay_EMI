package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class verifyOTPresponse extends BaseApiResponse{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("access_token")
    @Expose
    private String access_token;

    @SerializedName("refresh_token")
    @Expose
    private String refresh_token;


    @SerializedName("customer_id")
    @Expose
    private String customer_id;

    @SerializedName("profile_url")
    @Expose
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
