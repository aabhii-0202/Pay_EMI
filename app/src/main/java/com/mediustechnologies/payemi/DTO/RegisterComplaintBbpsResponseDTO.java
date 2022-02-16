package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterComplaintBbpsResponseDTO {

    @SerializedName("complaintAssigned")
    @Expose
    private String  complaintAssigned;

    @SerializedName("complaintId")
    @Expose
    private String  complaintId;


    public String getComplaintAssigned() {
        return complaintAssigned;
    }

    public void setComplaintAssigned(String complaintAssigned) {
        this.complaintAssigned = complaintAssigned;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    @Override
    public String toString() {
        return "RegisterComplaintBbpsResponseDTO{" +
                "complaintAssigned='" + complaintAssigned + '\'' +
                ", complaintId='" + complaintId + '\'' +
                '}';
    }
}
