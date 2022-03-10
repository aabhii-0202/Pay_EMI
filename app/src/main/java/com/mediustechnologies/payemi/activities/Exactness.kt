package com.mediustechnologies.payemi.activities

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mediustechnologies.payemi.ApiResponse.CreateOrderIdResponse
import com.mediustechnologies.payemi.DTO.HomepageDTO
import com.mediustechnologies.payemi.commons.urlconstants
import com.mediustechnologies.payemi.commons.utils
import com.mediustechnologies.payemi.databinding.ActivityPayEmiBinding
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Exactness : BaseAppCompatActivity(), PaymentResultListener {
    private var binding: ActivityPayEmiBinding? = null
    private val context: Context = this
    private var data: HomepageDTO? = null
    private var billerName: String? = null
    private var bill_id: String? = null
    private var profile_id: String? = null
    private var url: String? = null
    private var order_id: String? = null
    private var Exactness: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayEmiBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        var exactness: String? = null
        try {
            data = intent.getParcelableExtra("data")
            url = data!!.biller__logo_url
            exactness = data!!.biller__billerPaymentExactness
            binding!!.LoanName.text = intent.getStringExtra("type")
        } catch (e: Exception) {
            url = intent.getStringExtra("logo")
            exactness = intent.getStringExtra("exact")
        }
        val customer = intent.getStringExtra("customer")
        val amount = intent.getStringExtra("amount")
        billerName = intent.getStringExtra("billerName")
        try {
//            Log.d("tag","Exactness : "+exactness);
            if (exactness == null) {
                Exactness = "any"
            } else if (exactness.toLowerCase().contains("up")) {
                Exactness = "up"
            } else if (exactness.toLowerCase().contains("down") || exactness.toLowerCase()
                    .contains("below")
            ) {
                Exactness = "down"
            } else {
                Exactness = "exact"
                binding!!.enterAmount.isEnabled = false
            }
        } catch (e: Exception) { }
        Glide.with(binding!!.bankImage).load(url).into(binding!!.bankImage)
        binding!!.amount.text = "Rs. $amount"
        binding!!.enterAmount.setText(amount)
        binding!!.customerName.text = customer
        binding!!.billername.text = "Paying to $billerName"
        bill_id = intent.getStringExtra("bill_id")
        profile_id = intent.getStringExtra("profile_id")
        binding!!.payButton.setOnClickListener {
            var next = true
            val amont = binding!!.enterAmount.text.toString()
            val originalamount = intent.getStringExtra("amount")
            val a = amont.toDouble()
            val oa = originalamount!!.toDouble()
            next = if (Exactness === "up") {
                if (oa <= a) {
                    true
                } else {
                    Toast.makeText(
                        context,
                        "You are not allowed to pay any amount lower than Rs.$originalamount",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }
            } else if (Exactness === "down") {
                if (oa < a) {
                    Toast.makeText(
                        context,
                        "You are not allowed to pay any amount greater than Rs.$originalamount",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                } else true
            } else if (Exactness === "any") {
                true
            } else {
                true
            }
            if (next) orderId
        }
    }

    private val orderId: Unit
        private get() {
            val amount = binding!!.enterAmount.text.toString()
            val d = amount.toInt()
            val note = binding!!.note.text.toString()
            val call =
                RetrofitClient().getInstance(context, urlconstants.AuthURL).api.getRazorpayOrderId(
                    utils.access_token,
                    utils.profileId,
                    bill_id,
                    d,
                    note
                )
            call.enqueue(object : Callback<CreateOrderIdResponse?> {
                override fun onResponse(
                    call: Call<CreateOrderIdResponse?>,
                    response: Response<CreateOrderIdResponse?>
                ) {
                    if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                        if (response.body()!!.error == null || response.body()!!
                                .error.equals("false", ignoreCase = true)
                        ) {
                            order_id = response.body()!!.order_id
                            startPayment()
                        } else {
                            try {
                                utils.errortoast(context, response.body()!!.message)
                            } catch (e: Exception) {
                                Log.e("tag", e.toString())
                            }
                        }
                    } else {
                        Toast.makeText(context, "Failed " + response.code(), Toast.LENGTH_SHORT)
                            .show()
                        Log.e("tag", "" + response.code())
                    }
                }

                override fun onFailure(call: Call<CreateOrderIdResponse?>, t: Throwable) {
                    Toast.makeText(context, "Failed " + t.message, Toast.LENGTH_SHORT).show()
                    Log.e("tag", t.message!!)
                }
            })
        }


    private fun razorpaypaymentfailed() {
        Toast.makeText(context, "Card payment failed", Toast.LENGTH_SHORT).show()
        Log.d("tag", "Card Payment: Failed ")
        val j = Intent(context, PaymentSuccessful::class.java)
        j.putExtra("billerName", intent.getStringExtra("billerName"))
        j.putExtra("bill_id", intent.getStringExtra("bill_id"))
        j.putExtra("status", false)
        startActivity(j)
    }

    fun startPayment() {

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_I4yUvwRiUPNWay")

//        checkout.setImage(R.drawable.logo)
        val activity: Activity = this
        try {
            val amount = binding!!.enterAmount.text.toString()+"00"
            val options = JSONObject()
            options.put("name", billerName)
            options.put("description", "Bill id: "+bill_id)
            options.put("image", url)
//            options.put("order_id", orderId) //from response of step 3.
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", amount)
            options.put("prefill.contact", utils.phone)
            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)
            checkout.open(activity, options)
        } catch (e: Exception) {
            Log.e("tag", "Error in starting Razorpay Checkout", e)
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Log.d("payment","Success $p0")
        nextScreen(true)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("payment","Failed $p1")
        nextScreen(false)
    }

    private fun nextScreen(status: Boolean) {
        val i = Intent(context,PaymentSuccessful::class.java)
        i.putExtra("status",status)
        i.putExtra("bill_id",bill_id)

        startActivity(i)
    }
}