package com.mediustechnologies.payemi.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.mediustechnologies.payemi.ApiResponse.sendOTPResponse;
import com.mediustechnologies.payemi.ApiResponse.verifyOTPresponse;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.DashBoard;
import com.mediustechnologies.payemi.activities.BankList;
import com.mediustechnologies.payemi.activities.EmiCategories;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityVerifyNumberBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOTP extends BaseAppCompatActivity {

    private ActivityVerifyNumberBinding binding;
    private final Context context = this;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String s = getIntent().getStringExtra("otp");
        Toast.makeText(context, "OTP: "+s, Toast.LENGTH_LONG).show();
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
                Call<sendOTPResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().sendOTP(phone,"androidapp");
                String finalPhone = phone;
                call.enqueue(new Callback<sendOTPResponse>() {
                    @Override
                    public void onResponse(Call<sendOTPResponse> call, Response<sendOTPResponse> response) {
                        Log.d("tag","MESSAGE: "+response.body().getMessage()+" PAYLOAD: "+response.body().getPayload());
                        Toast.makeText(context, "OTP sent to "+ finalPhone, Toast.LENGTH_SHORT).show();
                        binding.resendOTPtext.setClickable(false);
                        countdown();

                    }

                    @Override
                    public void onFailure(Call<sendOTPResponse> call, Throwable t) {
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

                Call<verifyOTPresponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().checkOTP(phone, otp);


                call.enqueue(new Callback<verifyOTPresponse>() {
                    @Override
                    public void onResponse(Call<verifyOTPresponse> call, Response<verifyOTPresponse> response) {
                        if (response.code() == utils.RESPONSE_SUCCESS) {

                            utils.access_token= "Bearer "+response.body().getAccess_token();
                            utils.refresh_token= response.body().getRefresh_token();
                            utils.phone=phone;
                            utils.profileId = response.body().getId();
                            utils.customer_id = response.body().getCustomer_id();

                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("PAY_EMI", MODE_PRIVATE);
                            preferences.edit().putString("phone", phone).apply();
                            preferences.edit().putString("refresh_token", "Bearer "+response.body().getRefresh_token()).apply();
                            preferences.edit().putString("token", "Bearer "+response.body().getAccess_token()).apply();
                            preferences.edit().putString("profileid",response.body().getId()).apply();
                            preferences.edit().putString("cutomerid",response.body().getCustomer_id()).apply();

                            if(getIntent().getBooleanExtra("newUser",true)){
                                Intent i = new Intent(context, EmiCategories.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }else {
                                Intent i = new Intent(context, DashBoard.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        }else if(response.code()==400){
                            Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.d("tag",response.code()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<verifyOTPresponse> call, Throwable t) {
                        Log.d("tag", "Unable to verify OTP");
//                        startActivity(new Intent(context, DashBoard.class));
                    }
                });


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