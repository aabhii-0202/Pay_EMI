package com.mediustechnologies.payemi.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class billDetails {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("biller_id")
    @Expose
    private String biller_id;

    @SerializedName("profile_id")
    @Expose
    private String profile_id;

    @SerializedName("loan_id")
    @Expose
    private String loan_id;

    @SerializedName("loan_acc_no")
    @Expose
    private String loan_acc_no;

    @SerializedName("customer_name")
    @Expose
    private String customer_name;

    @SerializedName("customer_mobile")
    @Expose
    private String customer_mobile;

    @SerializedName("biller_additional_info")
    @Expose
    private String biller_additional_info;

    @SerializedName("inputparams_value")
    @Expose
    private String inputparams_value;

    @SerializedName("amountOptions")
    @Expose
    private String amountOptions;

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

    @SerializedName("RespBillNumber")
    @Expose
    private String RespBillNumber;

    @SerializedName("RespBillPeriod")
    @Expose
    private String RespBillPeriod;

    @SerializedName("RespDueDate")
    @Expose
    private String RespDueDate;

    @SerializedName("requestId")
    @Expose
    private String requestId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBiller_id() {
        return biller_id;
    }

    public void setBiller_id(String biller_id) {
        this.biller_id = biller_id;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
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

    public String getBiller_additional_info() {
        return biller_additional_info;
    }

    public void setBiller_additional_info(String biller_additional_info) {
        this.biller_additional_info = biller_additional_info;
    }

    public String getInputparams_value() {
        return inputparams_value;
    }

    public void setInputparams_value(String inputparams_value) {
        this.inputparams_value = inputparams_value;
    }

    public String getAmountOptions() {
        return amountOptions;
    }

    public void setAmountOptions(String amountOptions) {
        this.amountOptions = amountOptions;
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

    public String getcustomer_convinience_fees() {
        return customer_convinience_fees;
    }

    public void setcustomer_convinience_fees(String fixed_chcustomer_convinience_feesarges) {
        this.customer_convinience_fees = fixed_chcustomer_convinience_feesarges;
    }

    public String getService_tax() {
        return service_tax;
    }

    public void setService_tax(String service_tax) {
        this.service_tax = service_tax;
    }

    public String getRespBillNumber() {
        return RespBillNumber;
    }

    public void setRespBillNumber(String respBillNumber) {
        RespBillNumber = respBillNumber;
    }

    public String getRespBillPeriod() {
        return RespBillPeriod;
    }

    public void setRespBillPeriod(String respBillPeriod) {
        RespBillPeriod = respBillPeriod;
    }

    public String getRespDueDate() {
        return RespDueDate;
    }

    public void setRespDueDate(String respDueDate) {
        RespDueDate = respDueDate;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "billDetails{" +
                "id='" + id + '\'' +
                ", biller_id='" + biller_id + '\'' +
                ", profile_id='" + profile_id + '\'' +
                ", loan_id='" + loan_id + '\'' +
                ", loan_acc_no='" + loan_acc_no + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", customer_mobile='" + customer_mobile + '\'' +
                ", biller_additional_info='" + biller_additional_info + '\'' +
                ", inputparams_value='" + inputparams_value + '\'' +
                ", amountOptions='" + amountOptions + '\'' +
                ", bill_number='" + bill_number + '\'' +
                ", transaction_date_and_time='" + transaction_date_and_time + '\'' +
                ", amount='" + amount + '\'' +
                ", transation_status='" + transation_status + '\'' +
                ", order_id='" + order_id + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", transaction_date='" + transaction_date + '\'' +
                ", initiation_channel='" + initiation_channel + '\'' +
                ", payment_mode='" + payment_mode + '\'' +
                ", fixed_chcustomer_convinience_feesarges='" + customer_convinience_fees + '\'' +
                ", service_tax='" + service_tax + '\'' +
                ", RespBillNumber='" + RespBillNumber + '\'' +
                ", RespBillPeriod='" + RespBillPeriod + '\'' +
                ", RespDueDate='" + RespDueDate + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
