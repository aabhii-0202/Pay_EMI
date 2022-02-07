package com.mediustechnologies.payemi.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class HomepageDTO implements Parcelable {

    @SerializedName("biller__billerName")
    @Expose
    String biller__billerName;

    @SerializedName("biller__logo_url")
    @Expose
    String biller__logo_url;

    @SerializedName("biller__inputparameters_mandatory1")
    @Expose
    HashMap<String, mandatoryParmsDTO> biller__inputparameters_mandatory1;

    @SerializedName("biller__billerTimeout")
    @Expose
    String biller__billerTimeout;

    @SerializedName("biller__billerPaymentExactness")
    @Expose
    String biller__billerPaymentExactness;

    @SerializedName("customer_name")
    @Expose
    String customer_name;

    @SerializedName("biller__billerId")
    @Expose
    String biller__billerId;

    @SerializedName("customer_mobile")
    @Expose
    String customer_mobile;

    @SerializedName("due_date")
    @Expose
    String due_date;

    @SerializedName("Amount")
    @Expose
    String Amount;

    @SerializedName("due_amount")
    @Expose
    String due_amount;

    @SerializedName("emi")
    @Expose
    String emi;

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("loan_acc_no")
    @Expose
    String loan_acc_no;

    @SerializedName("loan_type")
    @Expose
    String loan_type;

    @SerializedName("loan_amount")
    @Expose
    String loan_amount;

    @SerializedName("loan_paid")
    @Expose
    String loan_paid;

    protected HomepageDTO(Parcel in) {
        biller__billerName = in.readString();
        biller__logo_url = in.readString();
        biller__billerTimeout = in.readString();
        biller__billerPaymentExactness = in.readString();
        customer_name = in.readString();
        biller__billerId = in.readString();
        customer_mobile = in.readString();
        due_date = in.readString();
        Amount = in.readString();
        due_amount = in.readString();
        emi = in.readString();
        id = in.readString();
        loan_acc_no = in.readString();
        loan_type = in.readString();
        loan_amount = in.readString();
        loan_paid = in.readString();
    }

    public static final Creator<HomepageDTO> CREATOR = new Creator<HomepageDTO>() {
        @Override
        public HomepageDTO createFromParcel(Parcel in) {
            return new HomepageDTO(in);
        }

        @Override
        public HomepageDTO[] newArray(int size) {
            return new HomepageDTO[size];
        }
    };

    public String getLoan_acc_no() {
        return loan_acc_no;
    }

    public void setLoan_acc_no(String loan_acc_no) {
        this.loan_acc_no = loan_acc_no;
    }

    public String getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(String loan_type) {
        this.loan_type = loan_type;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getLoan_paid() {
        return loan_paid;
    }

    public void setLoan_paid(String loan_paid) {
        this.loan_paid = loan_paid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBiller__billerName() {
        return biller__billerName;
    }

    public void setBiller__billerName(String biller__billerName) {
        this.biller__billerName = biller__billerName;
    }

    public String getBiller__logo_url() {
        return biller__logo_url;
    }

    public void setBiller__logo_url(String biller__logo_url) {
        this.biller__logo_url = biller__logo_url;
    }

    public HashMap<String, mandatoryParmsDTO> getBiller__inputparameters_mandatory1() {
        return biller__inputparameters_mandatory1;
    }

    public void setBiller__inputparameters_mandatory1(HashMap<String, mandatoryParmsDTO> biller__inputparameters_mandatory1) {
        this.biller__inputparameters_mandatory1 = biller__inputparameters_mandatory1;
    }

    public String getBiller__billerTimeout() {
        return biller__billerTimeout;
    }

    public void setBiller__billerTimeout(String biller__billerTimeout) {
        this.biller__billerTimeout = biller__billerTimeout;
    }

    public String getBiller__billerPaymentExactness() {
        return biller__billerPaymentExactness;
    }

    public void setBiller__billerPaymentExactness(String biller__billerPaymentExactness) {
        this.biller__billerPaymentExactness = biller__billerPaymentExactness;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getBiller__billerId() {
        return biller__billerId;
    }

    public void setBiller__billerId(String biller__billerId) {
        this.biller__billerId = biller__billerId;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(String due_amount) {
        this.due_amount = due_amount;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    @Override
    public String toString() {
        return "homePage{" +
                "biller__billerName='" + biller__billerName + '\'' +
                ", biller__logo_url='" + biller__logo_url + '\'' +
                ", biller__inputparameters_mandatory1=" + biller__inputparameters_mandatory1 +
                ", biller__billerTimeout='" + biller__billerTimeout + '\'' +
                ", biller__billerPaymentExactness='" + biller__billerPaymentExactness + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", biller__billerId='" + biller__billerId + '\'' +
                ", customer_mobile='" + customer_mobile + '\'' +
                ", due_date='" + due_date + '\'' +
                ", Amount='" + Amount + '\'' +
                ", due_amount='" + due_amount + '\'' +
                ", emi='" + emi + '\'' +
                ", id='" + id + '\'' +
                ", loan_acc_no='" + loan_acc_no + '\'' +
                ", loan_type='" + loan_type + '\'' +
                ", loan_amount='" + loan_amount + '\'' +
                ", loan_paid='" + loan_paid + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(biller__billerName);
        parcel.writeString(biller__logo_url);
        parcel.writeString(biller__billerTimeout);
        parcel.writeString(biller__billerPaymentExactness);
        parcel.writeString(customer_name);
        parcel.writeString(biller__billerId);
        parcel.writeString(customer_mobile);
        parcel.writeString(due_date);
        parcel.writeString(Amount);
        parcel.writeString(due_amount);
        parcel.writeString(emi);
        parcel.writeString(id);
        parcel.writeString(loan_acc_no);
        parcel.writeString(loan_type);
        parcel.writeString(loan_amount);
        parcel.writeString(loan_paid);
    }
}
