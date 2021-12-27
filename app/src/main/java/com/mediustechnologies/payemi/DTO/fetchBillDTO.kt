package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class fetchBillDTO {
    @SerializedName("Late_Payment_Fee")
    @Expose
    private String Late_Payment_Fee;

    @SerializedName("Fixed_Charges")
    @Expose
    private String Fixed_Charges;

    @SerializedName("Additional_Charges")
    @Expose
    private String Additional_Charges;

    @SerializedName("dueDate")
    @Expose
    private String dueDate;

    @SerializedName("billPeriod")
    @Expose
    private String billPeriod;

    @SerializedName("billNumber")
    @Expose
    private String billNumber;

    @SerializedName("customerName")
    @Expose
    private String customerName;

    @SerializedName("billAmount")
    @Expose
    private String billAmount;

    @SerializedName("Total_Outstanding_Amount")
    @Expose
    private String Total_Outstanding_Amount;

    @SerializedName("bill_id")
    @Expose
    private String bill_id;

    @SerializedName("order_id")
    @Expose
    private String order_id;

    @SerializedName("loan_number")
    @Expose
    private String loan_number;

    @SerializedName("mobile")
    @Expose
    private String mobile;


    public String getLate_Payment_Fee() {
        return Late_Payment_Fee;
    }

    public void setLate_Payment_Fee(String late_Payment_Fee) {
        Late_Payment_Fee = late_Payment_Fee;
    }

    public String getFixed_Charges() {
        return Fixed_Charges;
    }

    public void setFixed_Charges(String fixed_Charges) {
        Fixed_Charges = fixed_Charges;
    }

    public String getAdditional_Charges() {
        return Additional_Charges;
    }

    public void setAdditional_Charges(String additional_Charges) {
        Additional_Charges = additional_Charges;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBillPeriod() {
        return billPeriod;
    }

    public void setBillPeriod(String billPeriod) {
        this.billPeriod = billPeriod;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getTotal_Outstanding_Amount() {
        return Total_Outstanding_Amount;
    }

    public void setTotal_Outstanding_Amount(String total_Outstanding_Amount) {
        Total_Outstanding_Amount = total_Outstanding_Amount;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getLoan_number() {
        return loan_number;
    }

    public void setLoan_number(String loan_number) {
        this.loan_number = loan_number;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
