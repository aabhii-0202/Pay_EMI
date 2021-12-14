package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.mediustechnologies.payemi.databinding.ActivityPayEmiDetailsBinding;

public class pay_EMI_Details extends AppCompatActivity {
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


    }
}