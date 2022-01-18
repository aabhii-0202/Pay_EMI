package com.mediustechnologies.payemi.activities.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mediustechnologies.payemi.adapters.SliderAdapter;
import com.mediustechnologies.payemi.databinding.ActivityOnBordingBinding;

public class OnBording extends AppCompatActivity {
    private ActivityOnBordingBinding binding;
    private SliderAdapter sliderAdapter;
    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBordingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initSlider();
        init();


    }

    private void initSlider() {
        sliderAdapter = new SliderAdapter(context);
        binding.slider.setAdapter(sliderAdapter);
        binding.slider.addOnPageChangeListener(listener);
    }

    private void init(){
        binding.backbtn.setOnClickListener(view -> finish());
        binding.skip.setOnClickListener(view -> startActivity(new Intent(context, SendOTP.class)));
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position==2)startActivity(new Intent(context, SendOTP.class));
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}