package com.mediustechnologies.payemi.activities.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.View;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.BankList;
import com.mediustechnologies.payemi.activities.payment_confirmation;
import com.mediustechnologies.payemi.adapters.SliderAdapter;
import com.mediustechnologies.payemi.databinding.ActivityOnBordingBinding;

public class onBording extends AppCompatActivity {
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
        binding.slider.setOnPageChangeListener(listener);

    }

    private void init(){
        binding.backbtn.setOnClickListener(view -> finish());
        binding.skip.setOnClickListener(view -> startActivity(new Intent(context, signIn.class)));
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position==2)startActivity(new Intent(context, signIn.class));
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}