package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mediustechnologies.payemi.databinding.ActivityPaypmentSuccessfulBinding;

public class paypmentSuccessful extends AppCompatActivity {

    private ActivityPaypmentSuccessfulBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaypmentSuccessfulBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }
    private void init(){
        binding.crossButton.setOnClickListener(view -> finish());
    }
}