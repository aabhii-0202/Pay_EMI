package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.ApiResponse.inputParameterFeilds;
import com.mediustechnologies.payemi.adapters.inputParametersAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityInputParameterFeildsBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLoanAccount extends AppCompatActivity {

    private ActivityInputParameterFeildsBinding binding;
    private final Context context = this;
    private String url,biller_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputParameterFeildsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getInputParameters();
    }

    private void getInputParameters() {

        biller_id = getIntent().getStringExtra("biller_id");
//        biller_id="HPCL00000NAT01";

        String token = utils.access_token;
//        token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjQxMTQwMjAyLCJpYXQiOjE2NDEwNTM4MDIsImp0aSI6IjU3YmY3N2FmMjEyODQyNmJiYjQzNWNiY2NjNGFmNGU2IiwidXNlcl9pZCI6NX0.TNphEM82rFFVGBrItlmEQtHTTU4kYiofjzzDq2tmnbQ";


        Call<inputParameterFeilds> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().inputparameterfeilds(token,biller_id);

        call.enqueue(new Callback<inputParameterFeilds>() {
            @Override
            public void onResponse(Call<inputParameterFeilds> call, Response<inputParameterFeilds> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    inputParameterFeilds inputParameterFeilds = response.body();

                    String s = inputParameterFeilds.toString();
                    Log.d("tag", "InputParameterFeilds: "+s);

                    recyclerview(inputParameterFeilds);

                    String url = inputParameterFeilds.getData().getLogo();
                    Glide.with(binding.Image).load(url).into(binding.Image);
                    Log.d("tag", "onResponse: Input Parameter Feilds"+inputParameterFeilds.toString());

                }
            }

            @Override
            public void onFailure(Call<inputParameterFeilds> call, Throwable t) {
                Toast.makeText(context, "Fail to load input fields.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void recyclerview(inputParameterFeilds inputParameterFeilds) {
        RecyclerView recyclerView = binding.inputparmsrecycler;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        HashMap<String,String> data = inputParameterFeilds.getData().getMandatoryl();
        if(data!=null&&data.size()>0) {
            inputParametersAdapter adapter = new inputParametersAdapter(data);
            recyclerView.setAdapter(adapter);

        }else{
            binding.text.setText("No Mandatory Input Parameter Feilds");
        }
    }

    private void init(){
        binding.backButton.setOnClickListener(view -> finish());
        url = getIntent().getStringExtra("url");
        Glide.with(binding.Image).load(url).into(binding.Image);
        binding.getDetails.setOnClickListener(view -> nextScreen());
    }

    private void nextScreen(){
        Intent i = new Intent(context, EMIDetailsBillFetch.class);
        i.putExtra("url",url);
        i.putExtra("biller_name",getIntent().getStringExtra("biller_name"));
        i.putExtra("biller_id",biller_id);
        startActivity(i);
    }
}