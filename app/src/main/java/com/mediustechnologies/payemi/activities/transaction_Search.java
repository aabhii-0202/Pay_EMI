package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.mediustechnologies.payemi.databinding.ActivityTaransactionSearchBinding;

public class transaction_Search extends AppCompatActivity {

    private ActivityTaransactionSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTaransactionSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();


    }

    private void init(){

        binding.radioTransaction.setOnClickListener(view -> {
            if(binding.radioTransaction.isChecked()){
                binding.mobileNumberSelected.setVisibility(View.GONE);
                binding.transactionIdselected.setVisibility(View.VISIBLE);
                binding.details.setVisibility(View.GONE);
            }
        });

        binding.radioMobile.setOnClickListener(view -> {
            if(binding.radioMobile.isChecked()){
                binding.mobileNumberSelected.setVisibility(View.VISIBLE);
                binding.transactionIdselected.setVisibility(View.GONE);
                binding.details.setVisibility(View.GONE);
            }
        });

        binding.search.setOnClickListener(view -> binding.details.setVisibility(View.VISIBLE));

    }
}