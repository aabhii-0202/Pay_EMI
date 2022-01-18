package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mediustechnologies.payemi.ApiResponse.fetchBill;
import com.mediustechnologies.payemi.ApiResponse.inputParameterFeilds;
import com.mediustechnologies.payemi.DTO.mandatoryParmsDTO;
import com.mediustechnologies.payemi.activities.apiBody.fetchBillBody;
import com.mediustechnologies.payemi.adapters.inputParametersAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityInputParameterFeildsBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

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
    private String url,biller_id,exactness;
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
//        biller_id="ICIC00000NATKD";

        String token = utils.access_token;


        Call<inputParameterFeilds> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().inputparameterfeilds(token,biller_id);

        call.enqueue(new Callback<inputParameterFeilds>() {
            @Override
            public void onResponse(Call<inputParameterFeilds> call, Response<inputParameterFeilds> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    inputParameterFeilds inputParameterFeilds = response.body();

                    String s = inputParameterFeilds.toString();
                    Log.d("tag", "InputParameterFeilds: "+s);

                    recyclerview(inputParameterFeilds);
                    exactness = inputParameterFeilds.getData().getBillerPaymentExactness();

                    String url = inputParameterFeilds.getData().getLogo();
                    Glide.with(binding.Image).load(url).into(binding.Image);
                    Log.d("tag", "onResponse: Input Parameter Feilds"+inputParameterFeilds.toString());

                }
            }

            @Override
            public void onFailure(Call<inputParameterFeilds> call, Throwable t) {
                Toast.makeText(context, "Fail to load input fields.", Toast.LENGTH_SHORT).show();
                Log.e("tag","add Loan Account api response failed");
                binding.getDetails.setVisibility(View.GONE);
                binding.text.setText("Failed to load.");
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
//        if(verifydata(feilds))
        if(true)
        {


            FetchBill(ApiJsonMap(feilds));


        }
    }

    private JsonObject ApiJsonMap(LinkedHashMap<String, String> feilds) {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();

            for (Map.Entry item:feilds.entrySet()){
                jsonObj_.put((String) item.getKey(),item.getValue());
            }


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.d("tag", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            Log.d("tag","Add loan Account JSON exception line 154");
        }

        return gsonObject;
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

    private void FetchBill(JsonObject jsonObject){

        String biller_id = getIntent().getStringExtra("biller_id");
//        biller_id = "OU12LO000NATGJ";

        Call<fetchBill> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().billfetch(utils.access_token,biller_id,utils.phone,jsonObject);


        String finalBiller_id = biller_id;
        call.enqueue(new Callback<fetchBill>() {
            @Override
            public void onResponse(Call<fetchBill> call, Response<fetchBill> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    fetchBill bill = response.body();

                    utils.bill_id = bill.getPayload().get(0).getId();

                    LinkedHashMap<String,String> variableData = new LinkedHashMap<>();
                    variableData.putAll(bill.getPayload().get(0).getAmountOptions());
                    variableData.putAll(bill.getPayload().get(0).getInputparams_value());
                    variableData.putAll(bill.getPayload().get(0).getBiller_additional_info());

                    Intent i = new Intent(context, EMIDetailsBillFetch.class);
                    i.putExtra("url", url);
                    i.putExtra("variableData",variableData);
                    i.putExtra("bill",bill);
                    i.putExtra("exact",exactness);
                    i.putExtra("biller_name", getIntent().getStringExtra("biller_name"));
                    i.putExtra("biller_id", finalBiller_id);
                    startActivity(i);

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<fetchBill> call, Throwable t) {
                Log.d("tag", "onFailure: bill fetch"+t.toString());
                Toast.makeText(context, "Error fetching Bill", Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void fetchBill(JsonObject jsonObject){

        String biller_id = getIntent().getStringExtra("biller_id");
        biller_id = "OU12LO000NATGJ";

        String mobile = utils.phone;
        String loanNumber = "2775864";
        loanNumber = "2775864";
//        utils.access_token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjQxMzA3ODQwLCJpYXQiOjE2NDEyMjE0NDAsImp0aSI6ImYxOGE1ZjdhODA5YTRhNTU4MWUwOTg2ODM3N2Q1NzdmIiwidXNlcl9pZCI6NH0.r1g5N0HObaX0ckz0t3bx8uDoCVX9dunARy7LdChjfMI";
        fetchBillBody body = new fetchBillBody(loanNumber,mobile);

        Call<fetchBill> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().fetchBill(utils.access_token,biller_id,mobile,body);

        String finalBiller_id = biller_id;
        call.enqueue(new Callback<fetchBill>() {
            @Override
            public void onResponse(Call<fetchBill> call, Response<fetchBill> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    fetchBill bill = response.body();

                    utils.bill_id = bill.getPayload().get(0).getId();

                    LinkedHashMap<String,String> variableData = new LinkedHashMap<>();
                    variableData.putAll(bill.getPayload().get(0).getAmountOptions());
                    variableData.putAll(bill.getPayload().get(0).getInputparams_value());
                    variableData.putAll(bill.getPayload().get(0).getBiller_additional_info());

                    Intent i = new Intent(context, EMIDetailsBillFetch.class);
                    i.putExtra("url", url);
                    i.putExtra("variableData",variableData);
                    i.putExtra("bill",bill);
                    i.putExtra("exact",exactness);
                    i.putExtra("biller_name", getIntent().getStringExtra("biller_name"));
                    i.putExtra("biller_id", finalBiller_id);
                    startActivity(i);

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<fetchBill> call, Throwable t) {
                Log.d("tag", "onFailure: bill fetch"+t.toString());
                Toast.makeText(context, "Error fetching Bill", Toast.LENGTH_SHORT).show();
            }
        });



    }
}