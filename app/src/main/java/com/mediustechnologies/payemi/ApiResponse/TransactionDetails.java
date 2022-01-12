package com.mediustechnologies.payemi.ApiResponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetails implements Parcelable {

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

    @SerializedName("razorpay_transaction_id")
    @Expose
    String razorpay_transaction_id;

    @SerializedName("customer_name")
    @Expose
    String customer_name;

    @SerializedName("customer_email")
    @Expose
    String customer_email;

    @SerializedName("biller_id")
    @Expose
    String biller_id;

    protected TransactionDetails(Parcel in) {
        biller_name = in.readString();
        amount = in.readString();
        razorpay_transaction_status = in.readString();
        bbps_transaction_status = in.readString();
        transaction_datetime = in.readString();
        type = in.readString();
        profile_id = in.readString();
        bill_id = in.readString();
        is_redeemed = in.readString();
        razorpay_transaction_id = in.readString();
        customer_name = in.readString();
        customer_email = in.readString();
        biller_id = in.readString();
    }

    public static final Creator<TransactionDetails> CREATOR = new Creator<TransactionDetails>() {
        @Override
        public TransactionDetails createFromParcel(Parcel in) {
            return new TransactionDetails(in);
        }

        @Override
        public TransactionDetails[] newArray(int size) {
            return new TransactionDetails[size];
        }
    };

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

    public String getRazorpay_transaction_id() {
        return razorpay_transaction_id;
    }

    public void setRazorpay_transaction_id(String razorpay_transaction_id) {
        this.razorpay_transaction_id = razorpay_transaction_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getBiller_id() {
        return biller_id;
    }

    public void setBiller_id(String biller_id) {
        this.biller_id = biller_id;
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
                ", razorpay_transaction_id='" + razorpay_transaction_id + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", customer_email='" + customer_email + '\'' +
                ", biller_id='" + biller_id + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(biller_name);
        parcel.writeString(amount);
        parcel.writeString(razorpay_transaction_status);
        parcel.writeString(bbps_transaction_status);
        parcel.writeString(transaction_datetime);
        parcel.writeString(type);
        parcel.writeString(profile_id);
        parcel.writeString(bill_id);
        parcel.writeString(is_redeemed);
        parcel.writeString(razorpay_transaction_id);
        parcel.writeString(customer_name);
        parcel.writeString(customer_email);
        parcel.writeString(biller_id);
    }
}
