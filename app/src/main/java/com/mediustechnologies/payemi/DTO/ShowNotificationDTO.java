package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowNotificationDTO {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("bank_logo")
    @Expose
    private String bank_logo;

    @SerializedName("bank_name")
    @Expose
    private String bank_name;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("profile")
    @Expose
    private String profile;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_logo() {
        return bank_logo;
    }

    public void setBank_logo(String bank_logo) {
        this.bank_logo = bank_logo;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
