package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterComplaintDataDTO {

    @SerializedName("complaint_id")
    @Expose
    private String  complaint_id;

    @SerializedName("complaint_assigned_to")
    @Expose
    private String  complaint_assigned_to;

    @SerializedName("complaint_status")
    @Expose
    private String  complaint_status;

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getComplaint_assigned_to() {
        return complaint_assigned_to;
    }

    public void setComplaint_assigned_to(String complaint_assigned_to) {
        this.complaint_assigned_to = complaint_assigned_to;
    }

    public String getComplaint_status() {
        return complaint_status;
    }

    public void setComplaint_status(String complaint_status) {
        this.complaint_status = complaint_status;
    }

    @Override
    public String toString() {
        return "RegisterComplaintDataDTO{" +
                "complaint_id='" + complaint_id + '\'' +
                ", complaint_assigned_to='" + complaint_assigned_to + '\'' +
                ", complaint_status='" + complaint_status + '\'' +
                '}';
    }
}
