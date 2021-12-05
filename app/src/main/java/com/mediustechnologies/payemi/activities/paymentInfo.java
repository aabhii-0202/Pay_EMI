package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.ActivityPaymentInfoBinding;

public class paymentInfo extends AppCompatActivity {

    private ActivityPaymentInfoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(view -> finish());




    }
}