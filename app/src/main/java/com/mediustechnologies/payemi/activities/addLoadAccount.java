package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.ActivityAddLoadAccountBinding;

public class addLoadAccount extends AppCompatActivity {

    private ActivityAddLoadAccountBinding binding;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddLoadAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        binding.backButton.setOnClickListener(view -> finish());
        binding.getDetails.setOnClickListener(view -> startActivity(new Intent(context,paymentInfo.class)));
    }
}