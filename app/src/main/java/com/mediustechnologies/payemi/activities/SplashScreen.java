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

import com.mediustechnologies.payemi.Models.ifNewUser;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.login.act28onBording;
import com.mediustechnologies.payemi.activities.login.act31signIn;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(() -> {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("PAY_EMI", MODE_PRIVATE);
            String phone = preferences.getString("phone","");
            Call<ifNewUser> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().checkfornewUser(phone);

//            call.enqueue(new Callback<ifNewUser>() {
//                @Override
//                public void onResponse(Call<ifNewUser> call, Response<ifNewUser> response) {
//                    if (response.code()==200){
//
//                        ifNewUser n = response.body();
//                        assert n != null;
//                        if (n.getNew_user().equals("true")){
//                            startActivity(new Intent(context, act28onBording.class));
//                        }else{
//                            Toast.makeText(context, "Welcome "+phone, Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(context, act33payEMI_home.class));
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ifNewUser> call, Throwable t) {
//
//                }
//            });


            startActivity(new Intent(this, act31signIn.class));
            finish();
        },2000);


    }
}