package com.mediustechnologies.payemi.activities.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.mediustechnologies.payemi.adapters.SliderAdapter
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.mediustechnologies.payemi.activities.login.act31signIn
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.mediustechnologies.payemi.databinding.ActivityOnBordingBinding

class act28onBording : AppCompatActivity() {
    private var binding: ActivityOnBordingBinding? = null
    private var sliderAdapter: SliderAdapter? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBordingBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        initSlider()
        init()
    }

    private fun initSlider() {
        sliderAdapter = SliderAdapter(context)
        binding!!.slider.adapter = sliderAdapter
        binding!!.slider.setOnPageChangeListener(listener)
    }

    private fun init() {
        binding!!.backbtn.setOnClickListener { view: View? -> finish() }
        binding!!.skip.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    act31signIn::class.java
                )
            )
        }
    }

    var listener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            if (position == 2) startActivity(Intent(context, act31signIn::class.java))
        }

        override fun onPageSelected(position: Int) {}
        override fun onPageScrollStateChanged(state: Int) {}
    }
}