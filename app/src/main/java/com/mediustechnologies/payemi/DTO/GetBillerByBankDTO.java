package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBillerByBankDTO {


    @SerializedName("billerId")
    @Expose
    private String billerId;

    @SerializedName("biller_bank__bank_name")
    @Expose
    private String biller_bank__bank_name;

    @SerializedName("billerName")
    @Expose
    private String billerName;

    @SerializedName("logo_url")
    @Expose
    private String logo_url;


    public String getBillerId() {
        return billerId;
    }

    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }

    public String getBiller_bank__bank_name() {
        return biller_bank__bank_name;
    }

    public void setBiller_bank__bank_name(String biller_bank__bank_name) {
        this.biller_bank__bank_name = biller_bank__bank_name;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    @Override
    public String toString() {
        return "bankSubItem{" +
                "billerId='" + billerId + '\'' +
                ", biller_bank__bank_name='" + biller_bank__bank_name + '\'' +
                ", billerName='" + billerName + '\'' +
                ", logo_url='" + logo_url + '\'' +
                '}';
    }
}
