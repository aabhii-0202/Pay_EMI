package com.mediustechnologies.payemi.Models;

public class transaction_chat {

    private String name,loan_name,amount,status_date;

    public transaction_chat(String name, String loan_name, String amount, String status_date) {
        this.name = name;
        this.loan_name = loan_name;
        this.amount = amount;
        this.status_date = status_date;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoan_name() {
        return loan_name;
    }

    public void setLoan_name(String loan_name) {
        this.loan_name = loan_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus_date() {
        return status_date;
    }

    public void setStatus_date(String status_date) {
        this.status_date = status_date;
    }
}
