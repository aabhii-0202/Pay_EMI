package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.commons.RetrofitClient;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.databinding.ActivityComplaintRegistrationBinding;
import com.mediustechnologies.payemi.commons.loginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        String[] items = new String[]{"Transaction Based Complaint", "Mobile Based Complaint"};
    //create an adapter to describe how the items are displayed, adapters are used in several places in android.
    //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


        binding.sendOTP.setOnClickListener(view -> {
            sendOTP();
            showOtpSentDialog();
        });

        binding.verifyOTP.setOnClickListener(View->{binding.statusLayout.setVisibility(android.view.View.VISIBLE);});

        binding.submit.setOnClickListener(View->{startActivity(new Intent(context,transaction_Search.class));});

        binding.radioRegistration.setOnClickListener(view -> {
            binding.layoutreg.setVisibility(View.VISIBLE);
            binding.layouttracking.setVisibility(View.GONE);
            binding.viewbtn.setVisibility(View.GONE);
        });

        binding.radioTracking.setOnClickListener(view -> {
            binding.layoutreg.setVisibility(View.GONE);
            binding.layouttracking.setVisibility(View.VISIBLE);
            binding.viewbtn.setVisibility(View.VISIBLE);
            binding.statusLayout.setVisibility(View.GONE);
            binding.otpLayout.setVisibility(View.GONE);
        });


    }

    private void sendOTP() {
       String phone = binding.phoneNumber.getText().toString().trim();
       if(phone.length()==10){
           Call<loginResponse> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().sendOTP(phone);
           call.enqueue(new Callback<loginResponse>() {
               @Override
               public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                   System.out.println("OTP Response: "+response.body().toString());
//                   showOtpSentDialog();
               }

               @Override
               public void onFailure(Call<loginResponse> call, Throwable t) {
                   Log.d("tag","OTP sent failed: "+t.toString());
               }
           });
       }else {
           binding.phoneNumber.setError("Please enter a valid phone number.");
       }
    }

    private void verifyOTP(){
        String phone = binding.phoneNumber.getText().toString().trim();
        String otp = binding.otpNumber.getText().toString().trim();
        if(phone.length()==10&&otp.length()>0){
            Call<loginResponse> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().checkOTP(phone,otp);

            call.enqueue(new Callback<loginResponse>() {
                @Override
                public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                    Log.d("tag","Response from OTP verification: "+response.body().toString());
                }

                @Override
                public void onFailure(Call<loginResponse> call, Throwable t) {
                    Log.d("tag","Unable to verify OTP");
                }
            });


        }else {
            binding.phoneNumber.setError("Please enter a valid phone number.");
        }
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