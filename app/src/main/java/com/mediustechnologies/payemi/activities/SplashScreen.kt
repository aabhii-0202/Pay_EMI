package com.mediustechnologies.payemi.activities

import android.content.Context
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.mediustechnologies.payemi.R
import com.mediustechnologies.payemi.commons.utils
import android.content.SharedPreferences
import com.mediustechnologies.payemi.ApiResponse.ifNewUser
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.Window
import com.mediustechnologies.payemi.activities.login.OnBording
import com.mediustechnologies.payemi.activities.DashBoardclasses.Home_Nav
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SplashScreen : BaseAppCompatActivity() {
    private val context: Context = this@SplashScreen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)
        utils.application = applicationContext
        val preferences = applicationContext.getSharedPreferences("PAY_EMI", MODE_PRIVATE)
        val phone = preferences.getString("phone", "")
        val token = preferences.getString("token", "")
        val id = preferences.getString("profileid", "")
        val refresh_token = preferences.getString("refresh_token", "")
        val custid = preferences.getString("cutomerid", "")
        val name = preferences.getString("name", "")
        val path = preferences.getString("path", "")
        println("ProfileId------------ $id")
        println("Name----------------- $name")
        println("Token---------------- $token")
        println("Refresh Token-------- $refresh_token")
        println("Phone---------------- $phone")
        println("Customer Id---------- $custid")
        println("Profile Pic---------- $path")
        Handler().postDelayed({
            val call =
                RetrofitClient().getInstance(context, urlconstants.AuthURL).api.checkfornewUser(
                    phone
                )
            call.enqueue(object : Callback<ifNewUser> {
                override fun onResponse(call: Call<ifNewUser>, response: Response<ifNewUser>) {
                    if (response.code() == utils.RESPONSE_SUCCESS) {
                        if (response.body()!!.error == null || response.body()!!
                                .error.equals("false", ignoreCase = true)
                        ) {
                            val n = response.body()!!
                            if (n.new_user) {
                                startActivity(Intent(context, OnBording::class.java))
                                finish()
                            } else if(phone!!.length==10){

                                val i = Intent(context, Home_Nav::class.java)
                                i.putExtra("fragment", 0)
                                startActivity(i)
                                utils.access_token = token
                                utils.refresh_token = refresh_token
                                utils.phone = phone
                                utils.profileId = id
                                utils.customer_id = custid
                                utils.name = name
                                utils.profileUrl = path
                                finish()
                            }else{
                                startActivity(Intent(context, OnBording::class.java))
                            }
                        } else {
                            try {
                                utils.errortoast(context, response.body()!!.message)
                            } catch (e: Exception) {
                                Log.e("tag", e.toString())
                            }

                        }
                    } else {
                        Log.d("tag", "Check if new: " + response.message())
                        startActivity(Intent(context, OnBording::class.java))
                        finish()
                    }
                }

                override fun onFailure(call: Call<ifNewUser>, t: Throwable) {}
            })
        }, 2000)
    }
}