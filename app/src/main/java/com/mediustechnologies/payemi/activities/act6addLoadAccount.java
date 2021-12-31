package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.Models.inputParameterFeilds;
import com.mediustechnologies.payemi.activities.payments.act11AddNewCard;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityAddLoadAccountBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act6addLoadAccount extends AppCompatActivity {

    private ActivityAddLoadAccountBinding binding;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddLoadAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getInputParameters();
    }

    private void getInputParameters() {

        String biller_id = getIntent().getStringExtra("biller_id");

        Call<inputParameterFeilds> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().inputparameterfeilds(utils.access_token,biller_id);

        call.enqueue(new Callback<inputParameterFeilds>() {
            @Override
            public void onResponse(Call<inputParameterFeilds> call, Response<inputParameterFeilds> response) {
                if(response.code()==200){
                    inputParameterFeilds inputParameterFeilds = response.body();


                    String url = inputParameterFeilds.getData().getLogo();
                    Glide.with(binding.Image).load(url).into(binding.Image);
                    Log.d("tag", "onResponse: input Parameter Feilds"+inputParameterFeilds.toString());



                }
            }

            @Override
            public void onFailure(Call<inputParameterFeilds> call, Throwable t) {

            }
        });

    }

    private void init(){
        binding.backButton.setOnClickListener(view -> finish());
        String url = getIntent().getStringExtra("url");
        Glide.with(binding.Image).load(url).into(binding.Image);
        binding.getDetails.setOnClickListener(view -> startActivity(new Intent(context, act11AddNewCard.class)));
    }
}