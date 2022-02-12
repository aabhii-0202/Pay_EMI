package com.mediustechnologies.payemi.activities.login

import android.content.Context
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.os.CountDownTimer
import com.mediustechnologies.payemi.ApiResponse.sendOTPResponse
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import com.mediustechnologies.payemi.ApiResponse.verifyOTPresponse
import com.mediustechnologies.payemi.commons.utils
import android.content.Intent
import android.util.Log
import android.view.View
import com.mediustechnologies.payemi.activities.EmiCategories
import com.mediustechnologies.payemi.R
import com.mediustechnologies.payemi.activities.DashBoardclasses.Home_Nav
import com.mediustechnologies.payemi.databinding.ActivityVerifyNumberBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class CheckOTP : BaseAppCompatActivity() {
    private var binding: ActivityVerifyNumberBinding? = null
    private val context: Context = this
    private var phone: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyNumberBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        val s = intent.getStringExtra("otp")
        Toast.makeText(context, "OTP: $s", Toast.LENGTH_LONG).show()
        init()
        countdown()
    }

    private fun countdown() {
        val start = TimeUnit.MINUTES.toMillis(2)
        object : CountDownTimer(start, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000
                var time = ""
                time =
                    if (second % 60 > 9) (second / 60).toString() + ":" + second % 60 else (second / 60).toString() + ":0" + second % 60
                binding!!.resendOTPtext.text = "Resend OTP - 0$time sec"
            }

            override fun onFinish() {
                binding!!.resendOTPtext.text = "Click to resend OTP"
                binding!!.resendOTPtext.textSize = 16f
                binding!!.resendOTPtext.isClickable = true
            }
        }.start()
    }

    private fun resendOTP() {
        if (phone!!.length == 10) {
            val call = RetrofitClient().getInstance(context, urlconstants.AuthURL).api.sendOTP(
                phone,
                "androidapp"
            )
            val finalPhone = phone
            call.enqueue(object : Callback<sendOTPResponse> {
                override fun onResponse(
                    call: Call<sendOTPResponse>,
                    response: Response<sendOTPResponse>
                ) {
                    Log.d(
                        "tag", "MESSAGE: " + response.body()!!
                            .message + " PAYLOAD: " + response.body()!!.payload
                    )
                    Toast.makeText(context, "OTP sent to $finalPhone", Toast.LENGTH_SHORT).show()
                    binding!!.resendOTPtext.isClickable = false
                    countdown()
                }

                override fun onFailure(call: Call<sendOTPResponse>, t: Throwable) {
                    Log.d("tag", "OTP sent failed: $t")
                    Toast.makeText(
                        context,
                        "Fail to send OTP please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            Toast.makeText(context, "Wrong Phone number! Try again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verify() {
        val otp = binding!!.OTPpinView.text.toString()
        if (otp.length == 4) {
            run {
                val call = RetrofitClient().getInstance(context, urlconstants.AuthURL).api.checkOTP(
                    phone,
                    otp
                )
                call.enqueue(object : Callback<verifyOTPresponse> {
                    override fun onResponse(
                        call: Call<verifyOTPresponse>,
                        response: Response<verifyOTPresponse>
                    ) {
                        if (response.code() == utils.RESPONSE_SUCCESS) {
                            utils.access_token = "Bearer " + response.body()!!.access_token
                            utils.refresh_token = response.body()!!.refresh_token
                            utils.phone = phone
                            utils.profileId = response.body()!!.id
                            utils.customer_id = response.body()!!.customer_id
                            val preferences =
                                applicationContext.getSharedPreferences("PAY_EMI", MODE_PRIVATE)
                            preferences.edit().putString("phone", phone).apply()
                            preferences.edit().putString(
                                "refresh_token",
                                "Bearer " + response.body()!!.refresh_token
                            ).apply()
                            preferences.edit()
                                .putString("token", "Bearer " + response.body()!!.access_token)
                                .apply()
                            preferences.edit().putString("profileid", response.body()!!.id).apply()
                            preferences.edit().putString("cutomerid", response.body()!!.customer_id)
                                .apply()
                            if (intent.getBooleanExtra("newUser", true)) {
                                val i = Intent(context, EmiCategories::class.java)
                                i.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(i)
                            } else {
                                val i = Intent(context, Home_Nav::class.java)
                                i.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(i)
                            }
                        } else if (response.code() == 400) {
                            Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("tag", response.code().toString() + "")
                        }
                    }

                    override fun onFailure(call: Call<verifyOTPresponse>, t: Throwable) {
                        Log.d("tag", "Unable to verify OTP")
                    }
                })
            }
        } else {
            Toast.makeText(context, "Please enter a valid 4 digit OTP.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        phone = intent.getStringExtra("phone")
        binding!!.codesenttotext.text = "4 digit code sent to $phone"
        binding!!.back.setOnClickListener { view: View? -> finish() }
        binding!!.verifyOTP.setOnClickListener { view: View? -> verify() }
        binding!!.OTPpinView.cursorColor = getColor(R.color.grey)
        binding!!.resendOTPtext.setOnClickListener { view: View? -> resendOTP() }
        binding!!.resendOTPtext.isClickable = false
    }
}