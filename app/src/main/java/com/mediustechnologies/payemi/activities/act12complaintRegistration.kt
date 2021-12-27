package com.mediustechnologies.payemi.activities

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import com.mediustechnologies.payemi.activities.act20transaction_Search
import com.mediustechnologies.payemi.R
import com.mediustechnologies.payemi.Models.sendOTPResponse
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import com.mediustechnologies.payemi.Models.verifyOTPresponse
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import com.mediustechnologies.payemi.databinding.ActivityComplaintRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class act12complaintRegistration : AppCompatActivity() {
    private var binding: ActivityComplaintRegistrationBinding? = null
    private var dialog: Dialog? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplaintRegistrationBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        binding!!.sendOTP.setOnClickListener { view: View? ->
            sendOTP()
            showOtpSentDialog()
        }
        binding!!.verifyOTP.setOnClickListener { View: View? ->
            binding!!.statusLayout.visibility = android.view.View.VISIBLE
        }
        binding!!.submit.setOnClickListener { View: View? ->
            startActivity(
                Intent(
                    context,
                    act20transaction_Search::class.java
                )
            )
        }
        binding!!.radioRegistration.setOnClickListener { view: View? ->
            binding!!.radioTracking.setTextColor(getColor(R.color.black))
            binding!!.radioRegistration.setTextColor(getColor(R.color.btncolor))
            binding!!.layoutreg.visibility = View.VISIBLE
            binding!!.layouttracking.visibility = View.GONE
            binding!!.viewbtn.visibility = View.GONE
        }
        binding!!.radioTracking.setOnClickListener { view: View? ->
            binding!!.radioRegistration.setTextColor(getColor(R.color.black))
            binding!!.radioTracking.setTextColor(getColor(R.color.btncolor))
            binding!!.layoutreg.visibility = View.GONE
            binding!!.layouttracking.visibility = View.VISIBLE
            binding!!.viewbtn.visibility = View.VISIBLE
            binding!!.statusLayout.visibility = View.GONE
            binding!!.otpLayout.visibility = View.GONE
        }
    }

    private fun sendOTP() {
        val phone = binding!!.phoneNumber.text.toString().trim { it <= ' ' }
        if (phone.length == 10) {
            val call = RetrofitClient.getInstance(urlconstants.AuthURL).api.sendOTP(phone)
            call.enqueue(object : Callback<sendOTPResponse> {
                override fun onResponse(
                    call: Call<sendOTPResponse>,
                    response: Response<sendOTPResponse>
                ) {
                    println("OTP Response: " + response.body().toString())
                    //                   showOtpSentDialog();
                }

                override fun onFailure(call: Call<sendOTPResponse>, t: Throwable) {
                    Log.d("tag", "OTP sent failed: $t")
                }
            })
        } else {
            binding!!.phoneNumber.error = "Please enter a valid phone number."
        }
    }

    private fun verifyOTP() {
        val phone = binding!!.phoneNumber.text.toString().trim { it <= ' ' }
        val otp = binding!!.otpNumber.text.toString().trim { it <= ' ' }
        if (phone.length == 10 && otp.length > 0) {
            val call = RetrofitClient.getInstance(urlconstants.AuthURL).api.checkOTP(phone, otp)
        } else {
            binding!!.phoneNumber.error = "Please enter a valid phone number."
        }
    }

    private fun showOtpSentDialog() {
        dialog = Dialog(context)
        dialog!!.setContentView(R.layout.otpsentpopup)
        dialog!!.setCancelable(false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.findViewById<View>(R.id.cancel).setOnClickListener { view: View? ->
            dialog!!.cancel()
            binding!!.otpLayout.visibility = View.VISIBLE
        }
        dialog!!.show()
    }
}