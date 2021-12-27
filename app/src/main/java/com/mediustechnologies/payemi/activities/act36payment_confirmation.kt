package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.mediustechnologies.payemi.activities.act39payEMI_transaction_page
import com.mediustechnologies.payemi.databinding.ActivityPaymentConfirmationBinding

class act36payment_confirmation : AppCompatActivity() {
    private var binding: ActivityPaymentConfirmationBinding? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentConfirmationBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        binding!!.share.setOnClickListener { view: View? -> open() }
        binding!!.download.setOnClickListener { view: View? -> open() }
        binding!!.crossButton.setOnClickListener { view: View? -> finish() }
    }

    private fun open() {
        startActivity(Intent(context, act39payEMI_transaction_page::class.java))
    }
}