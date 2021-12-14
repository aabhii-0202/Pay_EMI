package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.mediustechnologies.payemi.databinding.ActivityPayEmiHomeBinding;

public class payEMI_home extends AppCompatActivity {
    private ActivityPayEmiHomeBinding binding;
    private Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){

    }
}