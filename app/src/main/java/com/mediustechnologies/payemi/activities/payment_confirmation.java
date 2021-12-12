package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mediustechnologies.payemi.databinding.ActivityPaymentConfirmationBinding;

public class payment_confirmation extends AppCompatActivity {

    private ActivityPaymentConfirmationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentConfirmationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
    }

    private void init(){




    }
}