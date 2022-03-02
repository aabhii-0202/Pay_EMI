package com.mediustechnologies.payemi.activities.login

import android.content.Context
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import android.os.Bundle
import com.mediustechnologies.payemi.ApiResponse.sendOTPResponse
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import com.mediustechnologies.payemi.commons.utils
import android.widget.Toast
import android.content.Intent
import android.util.Log
import android.view.View
import com.mediustechnologies.payemi.activities.login.CheckOTP
import com.mediustechnologies.payemi.databinding.ActivitySignInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendOTP : BaseAppCompatActivity() {
    private var binding: ActivitySignInBinding? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        binding!!.sendOTPbtn.setOnClickListener { view: View? -> sendOTP() }
    }

    private fun sendOTP() {
        val phone = binding!!.phoneNumber.text.toString().trim { it <= ' ' }
        if (phone.length == 10) {
            val call = RetrofitClient().getInstance(context, urlconstants.AuthURL).api.sendOTP(
                phone,
                "androidapp"
            )
            call.enqueue(object : Callback<sendOTPResponse?> {
                override fun onResponse(
                    call: Call<sendOTPResponse?>,
                    response: Response<sendOTPResponse?>
                ) {
                    if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                        Log.d(
                            "tag", "MESSAGE: " + response.body()!!
                                .message + " PAYLOAD: " + response.body()!!.payload
                        )
                        Toast.makeText(context, "OTP sent to $phone", Toast.LENGTH_SHORT).show()
                        val i = Intent(context, CheckOTP::class.java)
                        i.putExtra("phone", phone)
                        i.putExtra("newUser", response.body()!!.new_user)
                        i.putExtra("otp", response.body()!!.payload)
                        startActivity(i)
                    } else {
                        Log.d("tag", "Error " + response.code())
                    }
                }

                override fun onFailure(call: Call<sendOTPResponse?>, t: Throwable) {
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
}