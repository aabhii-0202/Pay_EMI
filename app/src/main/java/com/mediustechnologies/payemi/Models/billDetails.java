package com.mediustechnologies.payemi.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class billDetails {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("biller")
    @Expose
    private String biller;

    @SerializedName("loan_acc_no")
    @Expose
    private String loan_acc_no;

    @SerializedName("customer_name")
    @Expose
    private String customer_name;

    @SerializedName("customer_mobile")
    @Expose
    private String customer_mobile;

    @SerializedName("product_name")
    @Expose
    private String product_name;

    @SerializedName("tenure")
    @Expose
    private String tenure;

    @SerializedName("due_date")
    @Expose
    private String due_date;

    @SerializedName("bill_number")
    @Expose
    private String bill_number;

    @SerializedName("transaction_date_and_time")
    @Expose
    private String transaction_date_and_time;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("transation_status")
    @Expose
    private String transation_status;

    @SerializedName("order_id")
    @Expose
    private String order_id;

    @SerializedName("transaction_id")
    @Expose
    private String transaction_id;

    @SerializedName("transaction_date")
    @Expose
    private String transaction_date;

    @SerializedName("approval_number")
    @Expose
    private String approval_number;

    @SerializedName("charges_levied")
    @Expose
    private String charges_levied;

    @SerializedName("late_payment_fees")
    @Expose
    private String late_payment_fees;

    @SerializedName("additional_charges")
    @Expose
    private String additional_charges;

    @SerializedName("fixed_charges")
    @Expose
    private String fixed_charges;

    @SerializedName("emi")
    @Expose
    private String emi;

    @SerializedName("initiation_channel")
    @Expose
    private String initiation_channel;

    @SerializedName("payment_mode")
    @Expose
    private String payment_mode;

    @SerializedName("customer_convinience_fees")
    @Expose
    private String customer_convinience_fees;

    @SerializedName("service_tax")
    @Expose
    private String service_tax;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public String getLoan_acc_no() {
        return loan_acc_no;
    }

    public void setLoan_acc_no(String loan_acc_no) {
        this.loan_acc_no = loan_acc_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getBill_number() {
        return bill_number;
    }

    public void setBill_number(String bill_number) {
        this.bill_number = bill_number;
    }

    public String getTransaction_date_and_time() {
        return transaction_date_and_time;
    }

    public void setTransaction_date_and_time(String transaction_date_and_time) {
        this.transaction_date_and_time = transaction_date_and_time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransation_status() {
        return transation_status;
    }

    public void setTransation_status(String transation_status) {
        this.transation_status = transation_status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getApproval_number() {
        return approval_number;
    }

    public void setApproval_number(String approval_number) {
        this.approval_number = approval_number;
    }

    public String getCharges_levied() {
        return charges_levied;
    }

    public void setCharges_levied(String charges_levied) {
        this.charges_levied = charges_levied;
    }

    public String getLate_payment_fees() {
        return late_payment_fees;
    }

    public void setLate_payment_fees(String late_payment_fees) {
        this.late_payment_fees = late_payment_fees;
    }

    public String getAdditional_charges() {
        return additional_charges;
    }

    public void setAdditional_charges(String additional_charges) {
        this.additional_charges = additional_charges;
    }

    public String getFixed_charges() {
        return fixed_charges;
    }

    public void setFixed_charges(String fixed_charges) {
        this.fixed_charges = fixed_charges;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getInitiation_channel() {
        return initiation_channel;
    }

    public void setInitiation_channel(String initiation_channel) {
        this.initiation_channel = initiation_channel;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getCustomer_convinience_fees() {
        return customer_convinience_fees;
    }

    public void setCustomer_convinience_fees(String customer_convinience_fees) {
        this.customer_convinience_fees = customer_convinience_fees;
    }

    public String getService_tax() {
        return service_tax;
    }

    public void setService_tax(String service_tax) {
        this.service_tax = service_tax;
    }
}
