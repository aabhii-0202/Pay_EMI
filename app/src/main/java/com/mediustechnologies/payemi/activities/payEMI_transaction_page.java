package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.mediustechnologies.payemi.databinding.ActivityPayEmiTransactionPageBinding;

public class payEMI_transaction_page extends AppCompatActivity {
    private Context context = this;
    private ActivityPayEmiTransactionPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiTransactionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        dummydata();
        initrecyclerview();
    }

    private void dummydata(){

    }

    private void initrecyclerview(){

    }

    private void init(){

    }
}