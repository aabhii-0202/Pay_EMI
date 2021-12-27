package com.mediustechnologies.payemi.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mediustechnologies.payemi.Models.sendOTPResponse;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.databinding.ActivitySignInBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        binding.sendOTPbtn.setOnClickListener(view->{
//            startActivity(new Intent(context, act32verifyNumber.class));
            sendOTP();
        });



    }

    private void sendOTP() {
        String phone = binding.phoneNumber.getText().toString().trim();
//        String courntycode = binding.countryCodePicker.getSelectedCountryCode();
//        phone = "+"+courntycode+phone;
        if(phone.length()==10){
            Call<sendOTPResponse> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().sendOTP(phone);
            String finalPhone = phone;
            call.enqueue(new Callback<sendOTPResponse>() {
                @Override
                public void onResponse(Call<sendOTPResponse> call, Response<sendOTPResponse> response) {
                    Log.d("tag","MESSAGE: "+response.body().getMessage()+" PAYLOAD: "+response.body().getPayload());
//                   showOtpSentDialog();
                    Toast.makeText(context, "OTP sent to "+ finalPhone, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context,act32verifyNumber.class);
                    i.putExtra("phone",finalPhone);
                    startActivity(i);
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
}