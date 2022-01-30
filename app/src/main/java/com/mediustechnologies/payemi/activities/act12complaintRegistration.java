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

import com.mediustechnologies.payemi.ApiResponse.verifyOTPresponse;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.databinding.ActivityComplaintRegistrationBinding;
import com.mediustechnologies.payemi.ApiResponse.sendOTPResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act12complaintRegistration extends BaseAppCompatActivity {
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



        binding.sendOTP.setOnClickListener(view -> {
            sendOTP();
            showOtpSentDialog();
        });

        binding.verifyOTP.setOnClickListener(View->{binding.statusLayout.setVisibility(android.view.View.VISIBLE);});

        binding.submit.setOnClickListener(View->{startActivity(new Intent(context, act20transaction_Search.class));});

        binding.radioRegistration.setOnClickListener(view -> {
            binding.radioTracking.setTextColor(getColor(R.color.black));
            binding.radioRegistration.setTextColor(getColor(R.color.btncolor));
            binding.layoutreg.setVisibility(View.VISIBLE);
            binding.layouttracking.setVisibility(View.GONE);
            binding.viewbtn.setVisibility(View.GONE);
        });

        binding.radioTracking.setOnClickListener(view -> {
            binding.radioRegistration.setTextColor(getColor(R.color.black));
            binding.radioTracking.setTextColor(getColor(R.color.btncolor));
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
           Call<sendOTPResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().sendOTP(phone);
           call.enqueue(new Callback<sendOTPResponse>() {
               @Override
               public void onResponse(Call<sendOTPResponse> call, Response<sendOTPResponse> response) {
                   System.out.println("OTP Response: "+response.body().toString());
//                   showOtpSentDialog();
               }

               @Override
               public void onFailure(Call<sendOTPResponse> call, Throwable t) {
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
            Call<verifyOTPresponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().checkOTP(phone,otp);




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