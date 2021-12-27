package com.mediustechnologies.payemi.activities.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mediustechnologies.payemi.Models.sendOTPResponse
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import android.widget.Toast
import android.content.Intent
import android.util.Log
import android.view.View
import com.mediustechnologies.payemi.activities.login.act32verifyNumber
import com.mediustechnologies.payemi.databinding.ActivitySignInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class act31signIn : AppCompatActivity() {
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
        binding!!.sendOTPbtn.setOnClickListener { view: View? ->
//            startActivity(new Intent(context, act32verifyNumber.class));
            sendOTP()
        }
    }

    private fun sendOTP() {
        val phone = binding!!.phoneNumber.text.toString().trim { it <= ' ' }
        //        String courntycode = binding.countryCodePicker.getSelectedCountryCode();
//        phone = "+"+courntycode+phone;
        if (phone.length == 10) {
            val call = RetrofitClient.getInstance(urlconstants.AuthURL).api.sendOTP(phone)
            call.enqueue(object : Callback<sendOTPResponse> {
                override fun onResponse(
                    call: Call<sendOTPResponse>,
                    response: Response<sendOTPResponse>
                ) {
                    Log.d(
                        "tag", "MESSAGE: " + response.body()!!
                            .message + " PAYLOAD: " + response.body()!!.payload
                    )
                    //                   showOtpSentDialog();
                    Toast.makeText(context, "OTP sent to $phone", Toast.LENGTH_SHORT).show()
                    val i = Intent(context, act32verifyNumber::class.java)
                    i.putExtra("phone", phone)
                    startActivity(i)
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
}