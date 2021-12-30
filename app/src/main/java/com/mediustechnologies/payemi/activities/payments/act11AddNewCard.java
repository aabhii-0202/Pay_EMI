package com.mediustechnologies.payemi.activities.payments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mediustechnologies.payemi.databinding.ActivityAct11AddNewCardBinding;

public class act11AddNewCard extends AppCompatActivity {

    private ActivityAct11AddNewCardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAct11AddNewCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}