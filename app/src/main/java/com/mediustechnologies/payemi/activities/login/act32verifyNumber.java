package com.mediustechnologies.payemi.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.mediustechnologies.payemi.Models.loginResponse;
import com.mediustechnologies.payemi.Models.verifyOTPresponse;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.act33payEMI_home;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.databinding.ActivityVerifyNumberBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act32verifyNumber extends AppCompatActivity {

    private ActivityVerifyNumberBinding binding;
    private final Context context = this;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        countdown();

    }

    private void countdown() {
        long start = TimeUnit.MINUTES.toMillis(2);
        new CountDownTimer(start, 1000) {


            public void onTick(long millisUntilFinished) {
                long second = millisUntilFinished/1000;
                String time="";
                if(second%60>9)
                 time = second/60+":"+second%60;
                else time = second/60+":0"+second%60;
                binding.resendOTPtext.setText("Resend OTP - 0" + time +" sec");
            }
            public void onFinish() {
                binding.resendOTPtext.setText("Click to resend OTP");
                binding.resendOTPtext.setTextSize(16);
                binding.resendOTPtext.setClickable(true);
            }
        }.start();

    }

    private void resendOTP(){
            if(phone.length()==10){
                Call<loginResponse> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().sendOTP(phone);
                String finalPhone = phone;
                call.enqueue(new Callback<loginResponse>() {
                    @Override
                    public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                        Log.d("tag","MESSAGE: "+response.body().getMessage()+" PAYLOAD: "+response.body().getPayload());
                        Toast.makeText(context, "OTP sent to "+ finalPhone, Toast.LENGTH_SHORT).show();
                        binding.resendOTPtext.setClickable(false);
                        countdown();

                    }

                    @Override
                    public void onFailure(Call<loginResponse> call, Throwable t) {
                        Log.d("tag","OTP sent failed: "+t.toString());
                        Toast.makeText(context, "Fail to send OTP please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(context, "Wrong Phone number! Try again", Toast.LENGTH_SHORT).show();
            }

    }


    private void verify(){
        String otp = binding.OTPpinView.getText().toString();
        if(otp.length()==4){
            {
                Call<verifyOTPresponse> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().checkOTP(phone,otp);

//                call.enqueue(new Callback<verifyOTPresponse>() {
//                    @Override
//                    public void onResponse(Call<verifyOTPresponse> call, Response<verifyOTPresponse> response) {
//                        Log.d("tag","Response from OTP verification: "+response.body().getOtp());
////                        startActivity(new Intent(context, act33payEMI_home.class));
//                    }
//
//                    @Override
//                    public void onFailure(Call<verifyOTPresponse> call, Throwable t) {
//                        Log.d("tag","Unable to verify OTP");
////                        startActivity(new Intent(context, act33payEMI_home.class));
//                    }
//                });


            }
        }else {
            Toast.makeText(context, "Please enter a valid 4 digit OTP.", Toast.LENGTH_SHORT).show();
        }


    }

    private void init(){
        phone = getIntent().getStringExtra("phone");
        binding.codesenttotext.setText("4 digit code sent to "+phone);
        binding.back.setOnClickListener(view -> finish());
        binding.verifyOTP.setOnClickListener(view -> {
            verify();
        });
        binding.OTPpinView.setCursorColor(getColor(R.color.grey));
        binding.resendOTPtext.setOnClickListener(view -> {
            resendOTP();
        });
        binding.resendOTPtext.setClickable(false);
    }

}