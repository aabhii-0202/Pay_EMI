package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileInfoDataDTO {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("phone_number")
    @Expose
    private String phone_number;

    @SerializedName("wallet_amount")
    @Expose
    private String wallet_amount;

    @SerializedName("customer_id")
    @Expose
    private String customer_id;

    @SerializedName("user_name")
    @Expose
    private String user_name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("profile_url")
    @Expose
    private String profile_url;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("created_on_datetime")
    @Expose
    private String created_on_datetime;

    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("user_type")
    @Expose
    private String user_type;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("user")
    @Expose
    private String user;

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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCreated_on_datetime() {
        return created_on_datetime;
    }

    public void setCreated_on_datetime(String created_on_datetime) {
        this.created_on_datetime = created_on_datetime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ProfileInfoDataDTO{" +
                "id='" + id + '\'' +
                ", otp='" + otp + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", wallet_amount='" + wallet_amount + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", email='" + email + '\'' +
                ", profile_url='" + profile_url + '\'' +
                ", address='" + address + '\'' +
                ", fullname='" + fullname + '\'' +
                ", created_on_datetime='" + created_on_datetime + '\'' +
                ", data='" + data + '\'' +
                ", source='" + source + '\'' +
                ", user_type='" + user_type + '\'' +
                ", status='" + status + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
