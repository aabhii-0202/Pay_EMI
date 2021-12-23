package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.mediustechnologies.payemi.databinding.ActivityPaymentSuccessfulBinding;

public class act9paymentSuccessful extends AppCompatActivity {

    private ActivityPaymentSuccessfulBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentSuccessfulBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }
    private void init(){
        binding.crossButton.setOnClickListener(view -> finish());
        binding.share.setOnClickListener(View -> go());
        binding.download.setOnClickListener(View -> go());
    }

    private void go() {
        startActivity(new Intent(context, act12complaintRegistration.class));
    }
}