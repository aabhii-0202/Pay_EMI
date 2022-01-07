package com.mediustechnologies.payemi.commons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.mediustechnologies.payemi.ApiResponse.RefreshTokenResponse;
import com.mediustechnologies.payemi.activities.apiBody.RefreshToken;
import com.mediustechnologies.payemi.activities.login.act31signIn;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class utils {
    public static String access_token,refresh_token,phone,profileId;

    public static void loginAgain(Context activitycontext,Context application){
        SharedPreferences preferences = application.getSharedPreferences("PAY_EMI", Context.MODE_PRIVATE);
        preferences.edit().putString("phone", "").apply();
        preferences.edit().putString("token", "Bearer ").apply();
        preferences.edit().putString("profileid","").apply();
        activitycontext.startActivity(new Intent(activitycontext, act31signIn.class));
    }

    public static void refreshToken (){
        Log.d("tag","Refresh token "+utils.refresh_token);
        RefreshToken token = new RefreshToken(utils.refresh_token);
        Call<RefreshTokenResponse> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().refreshToken(token);
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {

                if(response.code()==200&&response.body()!=null){
                    String t = response.body().toString();
                    Log.d("tag","refresh token: "+t);
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                Log.d("tag","refresh token: "+t.toString());
            }
        });
    }
}
