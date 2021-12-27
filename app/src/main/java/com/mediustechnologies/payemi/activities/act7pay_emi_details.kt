package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.mediustechnologies.payemi.activities.act8payEMI
import com.mediustechnologies.payemi.Models.fetchBill
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import com.mediustechnologies.payemi.databinding.ActivityPaymentInfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class act7pay_emi_details : AppCompatActivity() {
    private var binding: ActivityPaymentInfoBinding? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentInfoBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.payNow.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    act8payEMI::class.java
                )
            )
        }

//        fetchBill();
    }

    private fun fetchBill() {
        val Id_biller = 3.toString() + ""
        val loanNumber = "XX45648XXUDD"
        val mobile = "1234567890"
        val call = RetrofitClient.getInstance(urlconstants.AuthURL).api.fetchBill(
            Id_biller,
            loanNumber,
            mobile
        )
        call.enqueue(object : Callback<fetchBill?> {
            override fun onResponse(call: Call<fetchBill?>, response: Response<fetchBill?>) {
                val billdata = response.body()
                setData(billdata)
            }

            override fun onFailure(call: Call<fetchBill?>, t: Throwable) {}
        })
    }

    private fun setData(data: fetchBill?) {
        binding!!.FinancerName.text = ""
        binding!!.loanName.text = ""
        binding!!.DueDate.text = data!!.payload.dueDate
        binding!!.ChargesLevied.text = ""
        binding!!.BaseBillAmount.text = ""
        binding!!.LatePaymentFee.text = data.payload.late_Payment_Fee
        binding!!.AdditionalCharges.text = data.payload.additional_Charges
        binding!!.FixedCharges.text = data.payload.fixed_Charges
        binding!!.EMI.text = ""
        binding!!.Tenure.text = ""
        binding!!.Amount.text = data.payload.billAmount
        binding!!.ServiceTax.text = ""
        binding!!.TotalAmount.text = data.payload.billAmount
    }
}