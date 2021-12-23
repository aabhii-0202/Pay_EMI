package com.mediustechnologies.payemi.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mediustechnologies.payemi.databinding.ActivitySignInBinding;

public class act31signIn extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();



    }

    private void init(){
        binding.sendOTPbtn.setOnClickListener(view->{startActivity(new Intent(context, act32verifyNumber.class));});



    }
}