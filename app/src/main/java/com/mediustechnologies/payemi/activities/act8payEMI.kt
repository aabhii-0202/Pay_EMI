package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.razorpay.PaymentResultWithDataListener
import android.os.Bundle
import com.razorpay.Checkout
import com.mediustechnologies.payemi.commons.constants
import org.json.JSONObject
import org.json.JSONException
import android.content.Intent
import android.view.View
import com.mediustechnologies.payemi.activities.act9paymentSuccessful
import com.mediustechnologies.payemi.databinding.ActivityPayEmiBinding
import com.razorpay.PaymentData

class act8payEMI : AppCompatActivity(), PaymentResultWithDataListener {
    private var binding: ActivityPayEmiBinding? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayEmiBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        binding!!.payButton.setOnClickListener { view: View? ->  //                startActivity(new Intent(context, act9paymentSuccessful.class)));
            openRazorpay()
        }
    }

    private fun openRazorpay() {
        val checkout = Checkout()
        checkout.setKeyID(constants.RAZOR_PAY_KEY)

//        checkout.setImage(R.drawable.);
        val `object` = JSONObject()
        try {
            `object`.put("name", "Pay EMI")
            `object`.put("description", "Test payment")
            `object`.put("theme.color", "#0093DD")
            `object`.put("currency", "INR")
            `object`.put("amount", "500")
            `object`.put("prefill.contact", "9087654321")
            `object`.put("prefill.email", "abc@gmail.com")
            checkout.open(this@act8payEMI, `object`)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(s: String, paymentData: PaymentData) {
        startActivity(Intent(context, act9paymentSuccessful::class.java))
    }

    override fun onPaymentError(i: Int, s: String, paymentData: PaymentData) {
        startActivity(Intent(context, act9paymentSuccessful::class.java))
    }
}