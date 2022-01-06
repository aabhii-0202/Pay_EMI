package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class inputParameterFeildsDTOdata {
    @SerializedName("billerName")
    @Expose
    private String billerName;

    @SerializedName("mandatory_params")
    @Expose
    private HashMap<String ,String> mandatory;

    @SerializedName("optional_params")
    @Expose
    private HashMap<String ,String> optional;

    @SerializedName("logo")
    @Expose
    private String logo;

    @SerializedName("timeout")
    @Expose
    private int timeout;

    @SerializedName("billerPaymentExactness")
    @Expose
    private String billerPaymentExactness;

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getBillerPaymentExactness() {
        return billerPaymentExactness;
    }

    public void setBillerPaymentExactness(String billerPaymentExactness) {
        this.billerPaymentExactness = billerPaymentExactness;
    }

    public HashMap<String, String> getMandatoryl() {
        return mandatory;
    }

    public void setMandatoryl(HashMap<String, String> mandatoryl) {
        this.mandatory = mandatoryl;
    }


    @Override
    public String toString() {
        return "inputParameterFeildsDTOdata{" +
                "billerName='" + billerName + '\'' +
                ", mandatoryl=" + mandatory +
                ", optional=" + optional +
                ", logo='" + logo + '\'' +
                ", timeout=" + timeout +
                ", billerPaymentExactness='" + billerPaymentExactness + '\'' +
                '}';
    }


}
