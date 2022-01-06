package com.mediustechnologies.payemi.activities.payments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mediustechnologies.payemi.databinding.ActivityAct12AddNewUpiBinding;

public class act12AddNewUpi extends AppCompatActivity {
    private ActivityAct12AddNewUpiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityAct12AddNewUpiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> finish());
    }
}