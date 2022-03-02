package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mediustechnologies.payemi.DTO.backend_use_only;
import com.mediustechnologies.payemi.DTO.RegisterComplaintDataDTO;

public class RegisterComplaintResponse extends BaseApiResponse{

    @SerializedName("data")
    @Expose
    private RegisterComplaintDataDTO data;

    @SerializedName("backend_use_only")
    @Expose
    private backend_use_only backend_use_only;

    public RegisterComplaintDataDTO getData() {
        return data;
    }

    public void setData(RegisterComplaintDataDTO data) {
        this.data = data;
    }

    public backend_use_only getBbps_response() {
        return backend_use_only;
    }

    public void setBbps_response(backend_use_only backend_use_only) {
        this.backend_use_only = backend_use_only;
    }

    @Override
    public String toString() {
        return "RegisterComplaintResponse{" +
                "data=" + data +
                ", bbps_response='" + backend_use_only + '\'' +
                '}';
    }
}
