package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mediustechnologies.payemi.databinding.ActivityPayEmiDetailsBinding;

public class act34pay_EMI_Details extends AppCompatActivity {
    private final Context context = this;
    private ActivityPayEmiDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPayEmiDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        init();
    }
    private void init(){

        binding.backButton.setOnClickListener(view -> finish());
        binding.paybtn.setOnClickListener(view -> startActivity(new Intent(context, act35payment_Page.class)));


    }
}