package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.ActivityPaymentInfoBinding;

public class paymentInfo extends AppCompatActivity {

    private ActivityPaymentInfoBinding binding;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(view -> finish());
        binding.payNow.setOnClickListener(view -> startActivity(new Intent(context,payEMI.class)));




    }
}