package com.mediustechnologies.payemi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.billFetchDTO;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class fetchBill {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("payload")
    @Expose
    private List<billFetchDTO> payload;

    @SerializedName("message")
    @Expose
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<billFetchDTO> getPayload() {
        return payload;
    }

    public void setPayload(List<billFetchDTO> payload) {
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
