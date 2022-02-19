package com.mediustechnologies.payemi.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;

public class billFetchDTO implements Parcelable {

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
    private String loan_idloan_id;

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
    private HashMap<String,String> biller_additional_info;

    @SerializedName("inputparams_value")
    @Expose
    private HashMap<String,String> inputparams_value;

    @SerializedName("amountOptions")
    @Expose
    private HashMap<String,String> amountOptions;

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

    @SerializedName("approval_number")
    @Expose
    private String approval_number;

    @SerializedName("emi")
    @Expose
    private String emi;

    @SerializedName("tenure")
    @Expose
    private String tenure;

    @SerializedName("charges_levied")
    @Expose
    private String charges_levied;

    protected billFetchDTO(Parcel in) {
        id = in.readString();
        biller_id = in.readString();
        profile_id = in.readString();
        loan_idloan_id = in.readString();
        loan_acc_no = in.readString();
        customer_name = in.readString();
        customer_mobile = in.readString();
        bill_number = in.readString();
        transaction_date_and_time = in.readString();
        amount = in.readString();
        transation_status = in.readString();
        order_id = in.readString();
        transaction_id = in.readString();
        transaction_date = in.readString();
        initiation_channel = in.readString();
        payment_mode = in.readString();
        customer_convinience_fees = in.readString();
        service_tax = in.readString();
        RespBillNumber = in.readString();
        RespBillPeriod = in.readString();
        RespDueDate = in.readString();
        requestId = in.readString();
        approval_number = in.readString();
        emi = in.readString();
        tenure = in.readString();
        charges_levied = in.readString();
    }

    public static final Creator<billFetchDTO> CREATOR = new Creator<billFetchDTO>() {
        @Override
        public billFetchDTO createFromParcel(Parcel in) {
            return new billFetchDTO(in);
        }

        @Override
        public billFetchDTO[] newArray(int size) {
            return new billFetchDTO[size];
        }
    };

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

    public String getLoan_idloan_id() {
        return loan_idloan_id;
    }

    public void setLoan_idloan_id(String loan_idloan_id) {
        this.loan_idloan_id = loan_idloan_id;
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

    public HashMap<String, String> getBiller_additional_info() {
        return biller_additional_info;
    }

    public void setBiller_additional_info(HashMap<String, String> biller_additional_info) {
        this.biller_additional_info = biller_additional_info;
    }

    public HashMap<String, String> getInputparams_value() {
        return inputparams_value;
    }

    public void setInputparams_value(HashMap<String, String> inputparams_value) {
        this.inputparams_value = inputparams_value;
    }

    public HashMap<String, String> getAmountOptions() {
        return amountOptions;
    }

    public void setAmountOptions(HashMap<String, String> amountOptions) {
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

    public String getApproval_number() {
        return approval_number;
    }

    public void setApproval_number(String approval_number) {
        this.approval_number = approval_number;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public String getCharges_levied() {
        return charges_levied;
    }

    public void setCharges_levied(String charges_levied) {
        this.charges_levied = charges_levied;
    }


    @Override
    public String toString() {
        return "billFetchDTO{" +
                "id='" + id + '\'' +
                ", biller_id='" + biller_id + '\'' +
                ", profile_id='" + profile_id + '\'' +
                ", loan_idloan_id='" + loan_idloan_id + '\'' +
                ", loan_acc_no='" + loan_acc_no + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", customer_mobile='" + customer_mobile + '\'' +
                ", biller_additional_info=" + biller_additional_info +
                ", inputparams_value=" + inputparams_value +
                ", amountOptions=" + amountOptions +
                ", bill_number='" + bill_number + '\'' +
                ", transaction_date_and_time='" + transaction_date_and_time + '\'' +
                ", amount='" + amount + '\'' +
                ", transation_status='" + transation_status + '\'' +
                ", order_id='" + order_id + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", transaction_date='" + transaction_date + '\'' +
                ", initiation_channel='" + initiation_channel + '\'' +
                ", payment_mode='" + payment_mode + '\'' +
                ", customer_convinience_fees='" + customer_convinience_fees + '\'' +
                ", service_tax='" + service_tax + '\'' +
                ", RespBillNumber='" + RespBillNumber + '\'' +
                ", RespBillPeriod='" + RespBillPeriod + '\'' +
                ", RespDueDate='" + RespDueDate + '\'' +
                ", requestId='" + requestId + '\'' +
                ", approval_number='" + approval_number + '\'' +
                ", emi='" + emi + '\'' +
                ", tenure='" + tenure + '\'' +
                ", charges_levied='" + charges_levied + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(biller_id);
        parcel.writeString(profile_id);
        parcel.writeString(loan_idloan_id);
        parcel.writeString(loan_acc_no);
        parcel.writeString(customer_name);
        parcel.writeString(customer_mobile);
        parcel.writeString(bill_number);
        parcel.writeString(transaction_date_and_time);
        parcel.writeString(amount);
        parcel.writeString(transation_status);
        parcel.writeString(order_id);
        parcel.writeString(transaction_id);
        parcel.writeString(transaction_date);
        parcel.writeString(initiation_channel);
        parcel.writeString(payment_mode);
        parcel.writeString(customer_convinience_fees);
        parcel.writeString(service_tax);
        parcel.writeString(RespBillNumber);
        parcel.writeString(RespBillPeriod);
        parcel.writeString(RespDueDate);
        parcel.writeString(requestId);
        parcel.writeString(approval_number);
        parcel.writeString(emi);
        parcel.writeString(tenure);
        parcel.writeString(charges_levied);
    }
}
