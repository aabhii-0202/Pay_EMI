package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.inputParameterFeildsDTOdata;

public class inputParameterFeilds extends BaseApiResponse{


    @SerializedName("data")
    @Expose
    private inputParameterFeildsDTOdata data;


    @SerializedName("status")
    @Expose
    private boolean status;


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
                ", data=" + data +
                ", status=" + status +
                '}';
    }
}
