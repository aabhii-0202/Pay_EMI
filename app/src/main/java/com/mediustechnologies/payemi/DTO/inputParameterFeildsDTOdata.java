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
    private HashMap<String,String> mandatory;

    @SerializedName("optional_params")
    @Expose
    private HashMap<String,String> optional;

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

    public HashMap<String, String> getMandatory() {
        return mandatory;
    }

    public void setMandatory(HashMap<String, String> mandatory) {
        this.mandatory = mandatory;
    }

    public HashMap<String, String> getOptional() {
        return optional;
    }

    public void setOptional(HashMap<String, String> optional) {
        this.optional = optional;
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

    @Override
    public String toString() {
        return "inputParameterFeildsDTOdata{" +
                "billerName='" + billerName + '\'' +
                ", mandatory=" + mandatory +
                ", optional=" + optional +
                ", logo='" + logo + '\'' +
                ", timeout=" + timeout +
                ", billerPaymentExactness='" + billerPaymentExactness + '\'' +
                '}';
    }
}
