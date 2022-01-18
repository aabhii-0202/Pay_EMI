package com.mediustechnologies.payemi.recyclerItems;

public class emiListItem {
    private String emiAmount,Bank_Name,Loan_Name,Paid_Amount,Total_Amount,icon;
    private int progress;

    public emiListItem(String emiAmount, String bank_Name, String loan_Name, String paid_Amount, String total_Amount, String icon) {
        this.emiAmount = emiAmount;
        Bank_Name = bank_Name;
        Loan_Name = loan_Name;
        Paid_Amount = paid_Amount;
        Total_Amount = total_Amount;
        this.icon = icon;

    }



    public String getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(String emiAmount) {
        this.emiAmount = emiAmount;
    }

    public String getBank_Name() {
        return Bank_Name;
    }

    public void setBank_Name(String bank_Name) {
        Bank_Name = bank_Name;
    }

    public String getLoan_Name() {
        return Loan_Name;
    }

    public void setLoan_Name(String loan_Name) {
        Loan_Name = loan_Name;
    }

    public String getPaid_Amount() {
        return Paid_Amount;
    }

    public void setPaid_Amount(String paid_Amount) {
        Paid_Amount = paid_Amount;
    }

    public String getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(String total_Amount) {
        Total_Amount = total_Amount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
