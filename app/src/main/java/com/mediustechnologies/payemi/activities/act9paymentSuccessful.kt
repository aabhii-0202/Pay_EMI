package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mediustechnologies.payemi.Models.billDetails
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import android.content.Intent
import android.util.Log
import android.view.View
import com.mediustechnologies.payemi.activities.act12complaintRegistration
import com.mediustechnologies.payemi.databinding.ActivityPaymentSuccessfulBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class act9paymentSuccessful : AppCompatActivity() {
    private var binding: ActivityPaymentSuccessfulBinding? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSuccessfulBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
        getbilldetails()
    }

    private fun setData(data: billDetails?) {
        Log.d("tag", "act9paymentSuccessful " + data.toString())
        binding!!.recieptBankName.text = "NA"
        binding!!.BillPeriod.text = "NA"
        binding!!.BillDate.text = "NA"
        binding!!.BillNumber.text = data!!.bill_number
        binding!!.BillerID.text = data.id
        binding!!.TotalAmount.text = data.amount
        binding!!.TransactionStatus.text = "NA"
        binding!!.TransactionID.text = data.transaction_id
        binding!!.TransactionDateTime.text = data.transaction_date_and_time
        binding!!.ApprovalNumber.text = "NA"
        binding!!.CName.text = data.customer_name
        binding!!.CNumber.text = data.customer_mobile
        binding!!.InitiatingChannel.text = data.initiation_channel
        binding!!.paymentMode.text = data.payment_mode
        binding!!.BillAmount.text = "NA"
        binding!!.convineanceFee.text = data.customer_convinience_fees
        binding!!.serviceTax.text = data.service_tax
        binding!!.totalAmount.text = "NA"
    }

    private fun getbilldetails() {
        val bill_id = 5.toString() + ""
        // dummy billerID
        val call = RetrofitClient.getInstance(urlconstants.AuthURL).api.getBillDetails(bill_id)
        call.enqueue(object : Callback<List<billDetails?>?> {
            override fun onResponse(
                call: Call<List<billDetails?>?>,
                response: Response<List<billDetails?>?>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    val data = response.body()!![0]
                    setData(data)
                }
            }

            override fun onFailure(call: Call<List<billDetails?>?>, t: Throwable) {
                val data: billDetails? = null
                setData(data)
            }
        })
    }

    private fun init() {
        binding!!.crossButton.setOnClickListener { view: View? -> finish() }
        binding!!.share.setOnClickListener { View: View? -> go() }
        binding!!.download.setOnClickListener { View: View? -> go() }
    }

    private fun go() {
        startActivity(Intent(context, act12complaintRegistration::class.java))
    }
}