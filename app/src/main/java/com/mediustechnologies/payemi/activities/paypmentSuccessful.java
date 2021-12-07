package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mediustechnologies.payemi.databinding.ActivityPaypmentSuccessfulBinding;

public class paypmentSuccessful extends AppCompatActivity {

    private ActivityPaypmentSuccessfulBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaypmentSuccessfulBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }
    private void init(){
        binding.crossButton.setOnClickListener(view -> finish());
        binding.share.setOnClickListener(View -> go());
        binding.download.setOnClickListener(View -> go());
    }

    private void go() {
        startActivity(new Intent(context,complaintRegistration.class));
    }
}