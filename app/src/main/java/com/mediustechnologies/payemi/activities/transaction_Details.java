package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.internal.ManufacturerUtils;
import com.mediustechnologies.payemi.databinding.ActivityTransactionDetailsBinding;

public class transaction_Details extends AppCompatActivity {
    private ActivityTransactionDetailsBinding binding;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
    }

    private void init(){
        binding.backButton.setOnClickListener(view -> finish());
        binding.havingIssue.setOnClickListener(view -> {
            startActivity(new Intent(context,BankList.class));
        });

    }
}