package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.mediustechnologies.payemi.activities.act7pay_emi_details
import com.mediustechnologies.payemi.databinding.ActivityAddLoadAccountBinding

class act6addLoadAccount : AppCompatActivity() {
    private var binding: ActivityAddLoadAccountBinding? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLoadAccountBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.getDetails.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    act7pay_emi_details::class.java
                )
            )
        }
    }
}