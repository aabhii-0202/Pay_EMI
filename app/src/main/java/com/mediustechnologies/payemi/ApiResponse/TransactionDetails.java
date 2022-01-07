package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetails {

    @SerializedName("biller_name")
    @Expose
    String biller_name;

    @SerializedName("amount")
    @Expose
    String amount;
    @SerializedName("razorpay_transaction_status")
    @Expose
    String razorpay_transaction_status;

    @SerializedName("bbps_transaction_status")
    @Expose
    String bbps_transaction_status;

    @SerializedName("transaction_datetime")
    @Expose
    String transaction_datetime;

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("profile_id")
    @Expose
    String profile_id;

    @SerializedName("bill_id")
    @Expose
    String bill_id;

    @SerializedName("is_redeemed")
    @Expose
    String is_redeemed;

    public String getBiller_name() {
        return biller_name;
    }

    public void setBiller_name(String biller_name) {
        this.biller_name = biller_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRazorpay_transaction_status() {
        return razorpay_transaction_status;
    }

    public void setRazorpay_transaction_status(String razorpay_transaction_status) {
        this.razorpay_transaction_status = razorpay_transaction_status;
    }

    public String getBbps_transaction_status() {
        return bbps_transaction_status;
    }

    public void setBbps_transaction_status(String bbps_transaction_status) {
        this.bbps_transaction_status = bbps_transaction_status;
    }

    public String getTransaction_datetime() {
        return transaction_datetime;
    }

    public void setTransaction_datetime(String transaction_datetime) {
        this.transaction_datetime = transaction_datetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getIs_redeemed() {
        return is_redeemed;
    }

    public void setIs_redeemed(String is_redeemed) {
        this.is_redeemed = is_redeemed;
    }

    @Override
    public String toString() {
        return "TransactionDetails{" +
                "biller_name='" + biller_name + '\'' +
                ", amount='" + amount + '\'' +
                ", razorpay_transaction_status='" + razorpay_transaction_status + '\'' +
                ", bbps_transaction_status='" + bbps_transaction_status + '\'' +
                ", transaction_datetime='" + transaction_datetime + '\'' +
                ", type='" + type + '\'' +
                ", profile_id='" + profile_id + '\'' +
                ", bill_id='" + bill_id + '\'' +
                ", is_redeemed='" + is_redeemed + '\'' +
                '}';
    }
}
