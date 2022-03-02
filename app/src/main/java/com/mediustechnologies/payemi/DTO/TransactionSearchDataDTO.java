package com.mediustechnologies.payemi.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionSearchDataDTO {

    @SerializedName("agentId")
    @Expose
    String agentId;

    @SerializedName("biller_name")
    @Expose
    String biller_name;

    @SerializedName("amount")
    @Expose
    String amount;

    @SerializedName("transaction_date")
    @Expose
    String transaction_date;

    @SerializedName("transaction_status")
    @Expose
    String transation_status;

    @SerializedName("transaction_ref_id")
    @Expose
    String transaction_ref_id;


    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getBiller_name() {
        return biller_name;
    }

    public void setBiller_name(String biller_name) {
        this.biller_name = biller_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getTransation_status() {
        return transation_status;
    }

    public void setTransation_status(String transation_status) {
        this.transation_status = transation_status;
    }

    public String getTransaction_ref_id() {
        return transaction_ref_id;
    }

    public void setTransaction_ref_id(String transaction_ref_id) {
        this.transaction_ref_id = transaction_ref_id;
    }

    @Override
    public String toString() {
        return "TransactionSearchDataDTO{" +
                "agentId='" + agentId + '\'' +
                ", biller_name='" + biller_name + '\'' +
                ", amount='" + amount + '\'' +
                ", transaction_date='" + transaction_date + '\'' +
                ", transation_status='" + transation_status + '\'' +
                ", transaction_ref_id='" + transaction_ref_id + '\'' +
                '}';
    }
}
