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
import com.mediustechnologies.payemi.DTO.mandatoryParmsDTO;
import com.mediustechnologies.payemi.adapters.inputParametersAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityInputParameterFeildsBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLoanAccount extends AppCompatActivity {

    private ActivityInputParameterFeildsBinding binding;
    private final Context context = this;
    private String url,biller_id;
    private inputParametersAdapter adapter;
    private LinkedHashMap<String, mandatoryParmsDTO> data;

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
        biller_id="ICIC00000NATKD";

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

        data = inputParameterFeilds.getData().getMandatory();
        if(data!=null&&data.size()>0) {
//            mandatoryParmsDTO d = new mandatoryParmsDTO();
//            d.setKey("abhishek_kumar");
//            d.setType("numeric");
//            d.setRegex("^[0-9]$");
//            data.put("abhishek kumar",d);
            adapter = new inputParametersAdapter(data);

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
        LinkedHashMap<String, String> feilds = adapter.getfeilds();
        if(verifydata(feilds)) {

            String s = makeJSON(feilds);

            Intent i = new Intent(context, EMIDetailsBillFetch.class);
            i.putExtra("body",s);
            i.putExtra("url", url);
            i.putExtra("biller_name", getIntent().getStringExtra("biller_name"));
            i.putExtra("biller_id", biller_id);
            startActivity(i);
        }
    }

    private String makeJSON(LinkedHashMap<String, String> feilds) {
        String ans ="";


        return ans;
    }

    private boolean verifydata(LinkedHashMap<String, String> feilds) {

        System.out.println(feilds);


        for (Map.Entry mapElement : data.entrySet()) {
            String key = (String)mapElement.getKey();
//            mandatoryParmsDTO value = ((mandatoryParmsDTO)mapElement.getValue());

            String regex = data.get(key).getRegex();
            String value = feilds.get(data.get(key).getKey());

            if(!check(value,regex)){
                Log.d("tag","Regex error for "+key);
                Toast.makeText(context, "Pattern didn't matched for "+ key, Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        return true;
    }

    private boolean check(String value, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }
}