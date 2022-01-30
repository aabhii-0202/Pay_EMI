package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mediustechnologies.payemi.ApiResponse.ifNewUser;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.login.OnBording;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends BaseAppCompatActivity {

    private final Context context = SplashScreen.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        utils.application = getApplicationContext();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("PAY_EMI", MODE_PRIVATE);
        String phone = preferences.getString("phone","");
        String token = preferences.getString("token","");
        String id = preferences.getString("profileid","");
        String refresh_token = preferences.getString("refresh_token","");
        String custid = preferences.getString("cutomerid","");

        System.out.println("Token---------------- "+token);
        System.out.println("Refresh Token--------"+refresh_token);
        System.out.println("Phone---------------- "+phone);
        System.out.println("ProfileId------------ "+id);
        System.out.println("Customer Id---------- "+custid);

        new Handler().postDelayed(() -> {

            Call<ifNewUser> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().checkfornewUser(phone);

            call.enqueue(new Callback<ifNewUser>() {
                @Override
                public void onResponse(Call<ifNewUser> call, Response<ifNewUser> response) {

                    if (response.code()==utils.RESPONSE_SUCCESS){
                        ifNewUser n = response.body();
                        assert n != null;

                        if (n.getNew_user()){
                            startActivity(new Intent(context, OnBording.class));
                            finish();
                        }else{
                            Toast.makeText(context, "Welcome "+phone, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context, DashBoard.class));
                            utils.access_token= token;
                            utils.refresh_token= refresh_token;
                            utils.phone=phone;
                            utils.profileId = id;
                            utils.customer_id = custid;
                            finish();
                        }
                    }
                    else {
                        Log.d("tag","Check if new: "+response.message());
                        startActivity(new Intent(context, OnBording.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ifNewUser> call, Throwable t) {

                }
            });


        },2000);


    }
}