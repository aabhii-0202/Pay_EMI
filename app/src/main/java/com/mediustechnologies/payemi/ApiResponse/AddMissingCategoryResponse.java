package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMissingCategoryResponse extends BaseApiResponse{
    @SerializedName("loan_data")
    @Expose
    private String loan_data;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data_passed_by_frontend")
    @Expose
    private String data_passed_by_frontend;


    public String getLoan_data() {
        return loan_data;
    }

    public void setLoan_data(String loan_data) {
        this.loan_data = loan_data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData_passed_by_frontend() {
        return data_passed_by_frontend;
    }

    public void setData_passed_by_frontend(String data_passed_by_frontend) {
        this.data_passed_by_frontend = data_passed_by_frontend;
    }

    @Override
    public String toString() {
        return "AddMissingCategoryResponse{" +
                "loan_data='" + loan_data + '\'' +
                ", status='" + status + '\'' +
                ", data_passed_by_frontend='" + data_passed_by_frontend + '\'' +
                '}';
    }

}
