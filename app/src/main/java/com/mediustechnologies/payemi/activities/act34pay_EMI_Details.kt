package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.mediustechnologies.payemi.activities.act35payment_Page
import com.mediustechnologies.payemi.databinding.ActivityPayEmiDetailsBinding

class act34pay_EMI_Details : AppCompatActivity() {
    private val context: Context = this
    private var binding: ActivityPayEmiDetailsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPayEmiDetailsBinding.inflate(
            layoutInflater
        )
        super.onCreate(savedInstanceState)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.paybtn.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    act35payment_Page::class.java
                )
            )
        }
    }
}