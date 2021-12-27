package com.mediustechnologies.payemi.Models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class billDetails {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("biller")
    @Expose
    var biller: String? = null

    @SerializedName("loan_acc_no")
    @Expose
    var loan_acc_no: String? = null

    @SerializedName("customer_name")
    @Expose
    var customer_name: String? = null

    @SerializedName("customer_mobile")
    @Expose
    var customer_mobile: String? = null

    @SerializedName("product_name")
    @Expose
    var product_name: String? = null

    @SerializedName("tenure")
    @Expose
    var tenure: String? = null

    @SerializedName("due_date")
    @Expose
    var due_date: String? = null

    @SerializedName("bill_number")
    @Expose
    var bill_number: String? = null

    @SerializedName("transaction_date_and_time")
    @Expose
    var transaction_date_and_time: String? = null

    @SerializedName("amount")
    @Expose
    var amount: String? = null

    @SerializedName("transation_status")
    @Expose
    var transation_status: String? = null

    @SerializedName("order_id")
    @Expose
    var order_id: String? = null

    @SerializedName("transaction_id")
    @Expose
    var transaction_id: String? = null

    @SerializedName("transaction_date")
    @Expose
    var transaction_date: String? = null

    @SerializedName("approval_number")
    @Expose
    var approval_number: String? = null

    @SerializedName("charges_levied")
    @Expose
    var charges_levied: String? = null

    @SerializedName("late_payment_fees")
    @Expose
    var late_payment_fees: String? = null

    @SerializedName("additional_charges")
    @Expose
    var additional_charges: String? = null

    @SerializedName("fixed_charges")
    @Expose
    var fixed_charges: String? = null

    @SerializedName("emi")
    @Expose
    var emi: String? = null

    @SerializedName("initiation_channel")
    @Expose
    var initiation_channel: String? = null

    @SerializedName("payment_mode")
    @Expose
    var payment_mode: String? = null

    @SerializedName("customer_convinience_fees")
    @Expose
    var customer_convinience_fees: String? = null

    @SerializedName("service_tax")
    @Expose
    var service_tax: String? = null
    override fun toString(): String {
        return "billDetails{" +
                "id='" + id + '\'' +
                ", biller='" + biller + '\'' +
                ", loan_acc_no='" + loan_acc_no + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", customer_mobile='" + customer_mobile + '\'' +
                ", product_name='" + product_name + '\'' +
                ", tenure='" + tenure + '\'' +
                ", due_date='" + due_date + '\'' +
                ", bill_number='" + bill_number + '\'' +
                ", transaction_date_and_time='" + transaction_date_and_time + '\'' +
                ", amount='" + amount + '\'' +
                ", transation_status='" + transation_status + '\'' +
                ", order_id='" + order_id + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", transaction_date='" + transaction_date + '\'' +
                ", approval_number='" + approval_number + '\'' +
                ", charges_levied='" + charges_levied + '\'' +
                ", late_payment_fees='" + late_payment_fees + '\'' +
                ", additional_charges='" + additional_charges + '\'' +
                ", fixed_charges='" + fixed_charges + '\'' +
                ", emi='" + emi + '\'' +
                ", initiation_channel='" + initiation_channel + '\'' +
                ", payment_mode='" + payment_mode + '\'' +
                ", customer_convinience_fees='" + customer_convinience_fees + '\'' +
                ", service_tax='" + service_tax + '\'' +
                '}'
    }
}