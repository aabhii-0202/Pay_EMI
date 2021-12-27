package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.mediustechnologies.payemi.activities.act36payment_confirmation
import com.mediustechnologies.payemi.databinding.ActivityPaymentPageBinding

class act35payment_Page : AppCompatActivity() {
    private var binding: ActivityPaymentPageBinding? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentPageBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.paywithcard.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    act36payment_confirmation::class.java
                )
            )
        }
        binding!!.paywithnetbanking.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    act36payment_confirmation::class.java
                )
            )
        }
        binding!!.paywithupi.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    act36payment_confirmation::class.java
                )
            )
        }
        binding!!.paywithwallet.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    act36payment_confirmation::class.java
                )
            )
        }
    }
}