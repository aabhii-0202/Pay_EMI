package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getAllBanks {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("count")
    @Expose
    private String count;

    @SerializedName("bank_name")
    @Expose
    private String bank_name;

    @SerializedName("biller_bank__bank_logo_url")
    @Expose
    private String bank_logo_url;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_logo_url() {
        return bank_logo_url;
    }

    public void setBank_logo_url(String bank_logo_url) {
        this.bank_logo_url = bank_logo_url;
    }

    @Override
    public String toString() {
        return "getAllBanks{" +
                "id='" + id + '\'' +
                ", count='" + count + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", bank_logo_url='" + bank_logo_url + '\'' +
                '}';
    }
}
