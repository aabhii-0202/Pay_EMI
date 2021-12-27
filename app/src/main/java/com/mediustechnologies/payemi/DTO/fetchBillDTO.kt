package com.mediustechnologies.payemi.DTO

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class fetchBillDTO {
    @SerializedName("Late_Payment_Fee")
    @Expose
    var late_Payment_Fee: String? = null

    @SerializedName("Fixed_Charges")
    @Expose
    var fixed_Charges: String? = null

    @SerializedName("Additional_Charges")
    @Expose
    var additional_Charges: String? = null

    @SerializedName("dueDate")
    @Expose
    var dueDate: String? = null

    @SerializedName("billPeriod")
    @Expose
    var billPeriod: String? = null

    @SerializedName("billNumber")
    @Expose
    var billNumber: String? = null

    @SerializedName("customerName")
    @Expose
    var customerName: String? = null

    @SerializedName("billAmount")
    @Expose
    var billAmount: String? = null

    @SerializedName("Total_Outstanding_Amount")
    @Expose
    var total_Outstanding_Amount: String? = null

    @SerializedName("bill_id")
    @Expose
    var bill_id: String? = null

    @SerializedName("order_id")
    @Expose
    var order_id: String? = null

    @SerializedName("loan_number")
    @Expose
    var loan_number: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null
}