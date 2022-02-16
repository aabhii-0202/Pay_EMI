package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.RegisterComplaintBbpsResponseDTO;
import com.mediustechnologies.payemi.DTO.RegisterComplaintDataDTO;

public class RegisterComplaintResponse extends BaseApiResponse{

    @SerializedName("data")
    @Expose
    private RegisterComplaintDataDTO data;

    @SerializedName("bbps response")
    @Expose
    private RegisterComplaintBbpsResponseDTO bbps_response;

    public RegisterComplaintDataDTO getData() {
        return data;
    }

    public void setData(RegisterComplaintDataDTO data) {
        this.data = data;
    }

    public RegisterComplaintBbpsResponseDTO getBbps_response() {
        return bbps_response;
    }

    public void setBbps_response(RegisterComplaintBbpsResponseDTO bbps_response) {
        this.bbps_response = bbps_response;
    }

    @Override
    public String toString() {
        return "RegisterComplaintResponse{" +
                "data=" + data +
                ", bbps_response='" + bbps_response + '\'' +
                '}';
    }
}
