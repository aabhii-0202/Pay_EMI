package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.mediustechnologies.payemi.databinding.ActivityTransactionDetailsBinding

class act40transaction_Details : AppCompatActivity() {
    private var binding: ActivityTransactionDetailsBinding? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionDetailsBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.havingIssue.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    SplashScreen::class.java
                )
            )
        }
    }
}