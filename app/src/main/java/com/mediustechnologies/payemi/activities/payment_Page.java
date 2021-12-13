package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.mediustechnologies.payemi.databinding.ActivityPaymentPageBinding;

public class payment_Page extends AppCompatActivity {
    private ActivityPaymentPageBinding binding;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
    }

    private void init(){
        binding.backButton.setOnClickListener(view -> finish());



    }
}