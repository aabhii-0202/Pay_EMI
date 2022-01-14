package com.mediustechnologies.payemi.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mediustechnologies.payemi.ApiResponse.sendOTPResponse;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivitySignInBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOTP extends AppCompatActivity {
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
            sendOTP();
        });



    }

    private void sendOTP() {
        String phone = binding.phoneNumber.getText().toString().trim();
//        String courntycode = binding.countryCodePicker.getSelectedCountryCode();
//        phone = "+"+courntycode+phone;
        if(phone.length()==10){
            Call<sendOTPResponse> call = new RetrofitClient(context).getInstance(urlconstants.AuthURL).getApi().sendOTP(phone);
            String finalPhone = phone;
            call.enqueue(new Callback<sendOTPResponse>() {
                @Override
                public void onResponse(Call<sendOTPResponse> call, Response<sendOTPResponse> response) {

                    if(response.code()== utils.RESPONSE_SUCCESS&& response.body()!=null)
                    {
                        Log.d("tag", "MESSAGE: " + response.body().getMessage() + " PAYLOAD: " + response.body().getPayload());
                        Toast.makeText(context, "OTP sent to " + finalPhone, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, CheckOTP.class);
                        i.putExtra("phone", finalPhone);
                        i.putExtra("newUser", response.body().getNew_user());
                        i.putExtra("otp", response.body().getPayload());
                        startActivity(i);
                    }
                    else {
                        Log.d("tag","Error "+response.code());
                    }


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