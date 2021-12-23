package com.mediustechnologies.payemi.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mediustechnologies.payemi.activities.act33payEMI_home;
import com.mediustechnologies.payemi.databinding.ActivityVerifyNumberBinding;

public class act33verifyNumber extends AppCompatActivity {

    private ActivityVerifyNumberBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }

    private void init(){
        binding.back.setOnClickListener(view -> finish());
        binding.verifyOTP.setOnClickListener(view -> startActivity(new Intent(context, act33payEMI_home.class)));
    }

}