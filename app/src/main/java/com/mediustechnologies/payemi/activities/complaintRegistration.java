package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.ActivityComplaintRegistrationBinding;

public class complaintRegistration extends AppCompatActivity {
    private ActivityComplaintRegistrationBinding binding;
    private Dialog dialog;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComplaintRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        // spinner
        AutoCompleteTextView dropdown = binding.spinner;
    //create a list of items for the spinner.
        String[] items = new String[]{"Transaction Based Complaint", "2", "3"};
    //create an adapter to describe how the items are displayed, adapters are used in several places in android.
    //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


        binding.sendOTP.setOnClickListener(view -> showOtpSentDialog());

        binding.verifyOTP.setOnClickListener(View->{binding.statusLayout.setVisibility(android.view.View.VISIBLE);});

        binding.submit.setOnClickListener(View->{startActivity(new Intent(context,transaction_Search.class));});



    }

    private void showOtpSentDialog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.otpsentpopup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        binding.otpLayout.setVisibility(View.VISIBLE);
    }
}