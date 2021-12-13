package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mediustechnologies.payemi.databinding.ActivityPaymentConfirmationBinding;

public class payment_confirmation extends AppCompatActivity {

    private ActivityPaymentConfirmationBinding binding;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentConfirmationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
    }

    private void init(){
        binding.share.setOnClickListener(view ->open());
        binding.download.setOnClickListener(view ->open());
        binding.crossButton.setOnClickListener(view -> finish());



    }

    private void open() {
        startActivity(new Intent(context,transaction_Details.class));
    }
}