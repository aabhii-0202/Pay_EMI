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
    private boolean registration;



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
        String[] items = new String[]{"Transaction Based Complaint", "Mobile Based Complaint"};
    //create an adapter to describe how the items are displayed, adapters are used in several places in android.
    //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


        binding.sendOTP.setOnClickListener(view -> showOtpSentDialog());

        binding.verifyOTP.setOnClickListener(View->{binding.statusLayout.setVisibility(android.view.View.VISIBLE);});

        binding.submit.setOnClickListener(View->{startActivity(new Intent(context,transaction_Search.class));});

        binding.radioRegistration.setOnClickListener(view -> {
            registration = true;
            binding.layoutreg.setVisibility(View.VISIBLE);
            binding.layouttracking.setVisibility(View.GONE);
            binding.viewbtn.setVisibility(View.GONE);
        });

        binding.radioTracking.setOnClickListener(view -> {
            registration = false;
            binding.layoutreg.setVisibility(View.GONE);
            binding.layouttracking.setVisibility(View.VISIBLE);
            binding.viewbtn.setVisibility(View.VISIBLE);
            binding.statusLayout.setVisibility(View.GONE);
            binding.otpLayout.setVisibility(View.GONE);
        });


    }

    private void showOtpSentDialog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.otpsentpopup);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.cancel).setOnClickListener(view->{
            dialog.cancel();
            binding.otpLayout.setVisibility(View.VISIBLE);
        });
        dialog.show();

    }
}