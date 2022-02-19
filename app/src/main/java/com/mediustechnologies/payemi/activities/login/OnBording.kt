package com.mediustechnologies.payemi.activities.login

import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import com.mediustechnologies.payemi.adapters.SliderAdapter
import android.os.Bundle
import android.os.CountDownTimer
import android.content.Intent
import com.mediustechnologies.payemi.activities.login.SendOTP
import android.annotation.SuppressLint
import android.content.Context
import android.view.View.OnTouchListener
import android.view.MotionEvent
import android.view.View
import com.mediustechnologies.payemi.databinding.ActivityOnBordingBinding

class OnBording : BaseAppCompatActivity() {
    private var binding: ActivityOnBordingBinding? = null
    private var sliderAdapter: SliderAdapter? = null
    private val context: Context = this
    private var pageno = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBordingBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        initSlider()
        init()
        countdown()
    }

    private fun countdown() {
        object : CountDownTimer(3000, 30) {
            override fun onTick(l: Long) {
                val time = l.toFloat()
                var p = time / 3000
                p = 1 - p
                p *= 100f
                val q = p.toInt()
                binding!!.progress.progress = q
            }

            override fun onFinish() {
                //change view pager
                pageno++
                if (pageno < 3) {
                    binding!!.slider.setCurrentItem(pageno, true)
                    countdown()
                } else {
                    startActivity(Intent(context, SendOTP::class.java))
                }
            }
        }.start()
    }

    private fun initSlider() {
        sliderAdapter = SliderAdapter(context)
        binding!!.slider.adapter = sliderAdapter
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        binding!!.slider.setOnTouchListener { view, motionEvent -> true }
        binding!!.backbtn.setOnClickListener { view: View? -> finish() }
        binding!!.skip.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    SendOTP::class.java
                )
            )
        }
    }
}