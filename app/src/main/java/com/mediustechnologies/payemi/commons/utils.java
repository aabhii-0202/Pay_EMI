package com.mediustechnologies.payemi.commons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonObject;
import com.mediustechnologies.payemi.ApiResponse.RefreshTokenResponse;
import com.mediustechnologies.payemi.activities.apiBody.RefreshToken;
import com.mediustechnologies.payemi.activities.login.SendOTP;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class utils extends Activity {
    public static String access_token,refresh_token,phone,profileId,customer_id;
    public static String bill_id;
    public static Context application;
    public static final int RESPONSE_SUCCESS = 200;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_FOUND = 404;
    public static final int UNAUTH = 401;


    public static void loginAgain(Context activitycontext){
        SharedPreferences preferences = application.getSharedPreferences("PAY_EMI", Context.MODE_PRIVATE);
        preferences.edit().putString("phone", "").apply();
        preferences.edit().putString("token", "Bearer ").apply();
        preferences.edit().putString("profileid","").apply();
        preferences.edit().putString("refresh_token", "Bearer ").apply();

        Intent i = new Intent(activitycontext, SendOTP.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activitycontext.startActivity(i);
    }

    public static void refreshToken (Context context){
        Log.d("tag","Refresh token "+utils.refresh_token);
        RefreshToken token = new RefreshToken(utils.refresh_token);
        Call<RefreshTokenResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().refreshToken(token);
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {

                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    String t = "Bearer "+response.body().toString();
                    utils.access_token = t;
                    SharedPreferences preferences = application.getSharedPreferences("PAY_EMI", Context.MODE_PRIVATE);
                    preferences.edit().putString("token",t).apply();
                    Log.d("tag","Refresh token: "+t);
                }
                else if(response.code()==UNAUTH){
                    //go to login page
                    loginAgain(context);

                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                Log.d("tag","Refresh token: "+t.toString());
            }
        });
    }

}
