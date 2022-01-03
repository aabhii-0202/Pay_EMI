package com.mediustechnologies.payemi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class fetchBill {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("payload")
    @Expose
    private LinkedHashMap<String, String> payload;

    @SerializedName("message")
    @Expose
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LinkedHashMap<String, String> getPayload() {
        return payload;
    }

    public void setPayload(LinkedHashMap<String, String> payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "fetchBill{" +
                "status=" + status +
                ", payload=" + payload +
                ", message='" + message + '\'' +
                '}';
    }
}
