package com.mediustechnologies.payemi.activities.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import com.mediustechnologies.payemi.adapters.SliderAdapter;
import com.mediustechnologies.payemi.databinding.ActivityOnBordingBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;

public class OnBording extends BaseAppCompatActivity {
    private ActivityOnBordingBinding binding;
    private SliderAdapter sliderAdapter;
    private final Context context = this;
    private int pageno = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBordingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initSlider();
        init();

        countdown();


    }

    private void countdown(){

        new CountDownTimer(3000,30){

            @Override
            public void onTick(long l) {
                float time = (float) l;
                float p = time/3000;
                p=1-p;
                p*=100;
                int q = (int)p;
                binding.progress.setProgress(q);
            }

            @Override
            public void onFinish() {
                //change view pager
                pageno++;

                if(pageno<3) {
                    binding.slider.setCurrentItem(pageno, true);
                    countdown();
                }
                else {
                    startActivity(new Intent(context, SendOTP.class));
                }
            }
        }.start();

    }


    private void initSlider() {
        sliderAdapter = new SliderAdapter(context);
        binding.slider.setAdapter(sliderAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        binding.slider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        binding.backbtn.setOnClickListener(view -> finish());
        binding.skip.setOnClickListener(view -> startActivity(new Intent(context, SendOTP.class)));
    }

}