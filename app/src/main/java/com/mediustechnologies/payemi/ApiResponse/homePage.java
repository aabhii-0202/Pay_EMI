package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class homePage {

    @SerializedName("biller__billerName")
    @Expose
    String biller__billerName;

    @SerializedName("biller__logo_url")
    @Expose
    String biller__logo_url;

    @SerializedName("biller__inputparameters_mandatory1")
    @Expose
    HashMap<String, String> biller__inputparameters_mandatory1;

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

    public HashMap<String, String> getBiller__inputparameters_mandatory1() {
        return biller__inputparameters_mandatory1;
    }

    public void setBiller__inputparameters_mandatory1(HashMap<String, String> biller__inputparameters_mandatory1) {
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
                '}';
    }
}
