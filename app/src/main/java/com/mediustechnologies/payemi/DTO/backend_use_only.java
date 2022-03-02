package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class backend_use_only {

    @SerializedName("complaintAssigned")
    @Expose
    private String  complaintAssigned;

    @SerializedName("complaintId")
    @Expose
    private String  complaintId;

    @SerializedName("complaintStatus")
    @Expose
    private String  complaintStatus;

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

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
                ", complaintStatus='" + complaintStatus + '\'' +
                '}';
    }
}
