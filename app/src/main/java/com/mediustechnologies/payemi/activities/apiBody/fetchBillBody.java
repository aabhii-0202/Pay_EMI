package com.mediustechnologies.payemi.activities.apiBody;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class fetchBillBody {

    public fetchBillBody(String loanNumber, String mobile) {
        this.loanNumber = loanNumber;
        this.mobile = mobile;
    }

    @SerializedName("Loan Number")
    @Expose

    private String loanNumber;

    @SerializedName("mobile")
    @Expose

    private String mobile;


    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
