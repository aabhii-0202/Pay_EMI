package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.Models.inputParameterFeilds;
import com.mediustechnologies.payemi.activities.payments.act11AddNewCard;
import com.mediustechnologies.payemi.adapters.inputParametersAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityInputParameterFeildsBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act6InputParameterFeilds extends AppCompatActivity {

    private ActivityInputParameterFeildsBinding binding;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputParameterFeildsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getInputParameters();
    }

    private void getInputParameters() {

        String biller_id = getIntent().getStringExtra("biller_id");
//        biller_id="HPCL00000NAT01";

        String token = utils.access_token;
//        token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjQxMTQwMjAyLCJpYXQiOjE2NDEwNTM4MDIsImp0aSI6IjU3YmY3N2FmMjEyODQyNmJiYjQzNWNiY2NjNGFmNGU2IiwidXNlcl9pZCI6NX0.TNphEM82rFFVGBrItlmEQtHTTU4kYiofjzzDq2tmnbQ";


        Call<inputParameterFeilds> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().inputparameterfeilds(token,biller_id);

        call.enqueue(new Callback<inputParameterFeilds>() {
            @Override
            public void onResponse(Call<inputParameterFeilds> call, Response<inputParameterFeilds> response) {
                if(response.code()==200){
                    inputParameterFeilds inputParameterFeilds = response.body();

                    String s = inputParameterFeilds.toString();
                    Log.d("tag", "inputParameterFeilds: "+s);

                    recyclerview(inputParameterFeilds);


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

    private void recyclerview(inputParameterFeilds inputParameterFeilds) {
        RecyclerView recyclerView = binding.inputparmsrecycler;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        HashMap<String,String> data = inputParameterFeilds.getData().getMandatoryl();
        inputParametersAdapter adapter = new inputParametersAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    private void init(){
        binding.backButton.setOnClickListener(view -> finish());
        String url = getIntent().getStringExtra("url");
        Glide.with(binding.Image).load(url).into(binding.Image);
        binding.getDetails.setOnClickListener(view -> startActivity(new Intent(context, act7pay_emi_details.class)));
    }
}