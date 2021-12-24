package com.mediustechnologies.payemi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.fetchBillDTO;

public class fetchBill {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("payload")
    @Expose
    private fetchBillDTO payload;

    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public fetchBillDTO getPayload() {
        return payload;
    }

    public void setPayload(fetchBillDTO payload) {
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
