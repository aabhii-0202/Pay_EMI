package com.mediustechnologies.payemi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.mediustechnologies.payemi.R
import android.content.Intent
import android.os.Handler
import android.view.Window
import com.mediustechnologies.payemi.databinding.ActivityBankSubCategoriesBinding
import com.mediustechnologies.payemi.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

//    private var binding: ActivitySplashScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        binding = ActivitySplashScreenBinding.inflate(
//            layoutInflater
//        )
//        setContentView(binding!!.root)
setContentView(R.layout.activity_splash_screen)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({

            startActivity(Intent(this, act4BankList::class.java))
//            startActivity(Intent(this, ScratchCardActivity::class.java))
            finish()
        }, 2000)
    }
}