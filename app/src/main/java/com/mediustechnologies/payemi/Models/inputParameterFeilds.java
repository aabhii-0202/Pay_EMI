package com.mediustechnologies.payemi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.inputParameterFeildsDTOdata;

public class inputParameterFeilds {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private inputParameterFeildsDTOdata data;


    @SerializedName("status")
    @Expose
    private boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public inputParameterFeildsDTOdata getData() {
        return data;
    }

    public void setData(inputParameterFeildsDTOdata data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "inputParameterFeilds{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}
