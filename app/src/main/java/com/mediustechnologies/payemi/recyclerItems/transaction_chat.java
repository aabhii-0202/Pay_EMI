package com.mediustechnologies.payemi.recyclerItems;

public class transaction_chat {
    String name,loanname,amount,status,date,redeemed,type;

    public transaction_chat(String name, String loanname, String amount, String status, String date, String redeemed, String type) {
        this.name = name;
        this.loanname = loanname;
        this.amount = amount;
        this.status = status;
        this.date = date;
        this.redeemed = redeemed;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoanname() {
        return loanname;
    }

    public void setLoanname(String loanname) {
        this.loanname = loanname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(String redeemed) {
        this.redeemed = redeemed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
