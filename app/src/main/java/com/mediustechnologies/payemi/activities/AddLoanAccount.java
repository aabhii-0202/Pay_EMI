package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mediustechnologies.payemi.ApiResponse.fetchBill;
import com.mediustechnologies.payemi.ApiResponse.inputParameterFeilds;
import com.mediustechnologies.payemi.DTO.mandatoryParmsDTO;
import com.mediustechnologies.payemi.adapters.inputParametersAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityInputParameterFeildsBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.DatePickerFragment;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLoanAccount extends BaseAppCompatActivity {

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
//        biller_id = "CAPR00000NATC0";
        String token = utils.access_token;
//        token ="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjQzNTU3MDIxLCJpYXQiOjE2NDM0NzA2MjEsImp0aSI6IjdlMWVmNjJmMjNlZjQyNzdhYzQ4ZmU2ZGM5MzcyODY4IiwidXNlcl9pZCI6NH0.X6LpLigl1PSTaxdn9DtactGwHOoyXwDjb6rmzMC1Anw";


        Call<inputParameterFeilds> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().inputparameterfeilds(token,biller_id);

        call.enqueue(new Callback<inputParameterFeilds>() {
            @Override
            public void onResponse(Call<inputParameterFeilds> call, Response<inputParameterFeilds> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                        inputParameterFeilds inputParameterFeilds = response.body();

                        String s = inputParameterFeilds.toString();
                        Log.d("tag", "InputParameterFeilds: " + s);

                        binding.linearLayout.setVisibility(View.VISIBLE);
                        binding.progressbar.setVisibility(View.GONE);

                        recyclerview(inputParameterFeilds);
                        exactness = inputParameterFeilds.getData().getBillerPaymentExactness();

                        String url = inputParameterFeilds.getData().getLogo();
                        Glide.with(binding.Image).load(url).into(binding.Image);
                        Log.d("tag", "onResponse: Input Parameter Feilds" + inputParameterFeilds.toString());
                    }else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                        }
                    }
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

//        LinkedHashMap<String, mandatoryParmsDTO> temp = new LinkedHashMap<>();
//        mandatoryParmsDTO temp2 = new mandatoryParmsDTO();
//        temp2.setKey("date");
//        temp2.setRegex(null);
//        temp2.setType("Numeric");
//        temp.put("Date",temp2);
//        data.putAll(temp);


        if(data!=null&&data.size()>0) {
            adapter = new inputParametersAdapter(data);
            recyclerView.setAdapter(adapter);
        }else{
            binding.text.setText("No Mandatory Input Parameter Feilds");
        }


    }





    private void init(){
        binding.linearLayout.setVisibility(View.GONE);
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.backButton.setOnClickListener(view -> finish());
        url = getIntent().getStringExtra("url");
        Glide.with(binding.Image).load(url).into(binding.Image);
        binding.getDetails.setOnClickListener(view -> nextScreen());
    }

    private void nextScreen(){

        binding.linearLayout.setVisibility(View.GONE);
        binding.progressbar.setVisibility(View.VISIBLE);
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

            Log.d("tag","Pattern: "+regex+" Matcher Value: "+value+" Return: "+check(value,regex));
            if(!check(value,regex)){
                Log.d("tag","Regex error for "+key);
                Toast.makeText(context, "Pattern didn't matched for "+ key, Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        return true;
    }

    private boolean check(String value, String regex) {

        if (regex== null||regex.equals("null")){
            return true;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    private void FetchBill(JsonObject jsonObject){

        String biller_id = getIntent().getStringExtra("biller_id");
        biller_id = "TVSC00000NAT0T";
        String  id = utils.profileId;
        id = "81";
        String phone = utils.phone;
        phone = "99943713390";

        LinkedHashMap<String, String> feilds = new LinkedHashMap<>();
        feilds.put("Agreement number","TN3003CA0005203");
        jsonObject = ApiJsonMap(feilds);


        //todo above lines are hard coded





        Call<fetchBill> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().billfetch(utils.access_token,id,biller_id,phone,jsonObject);


        String finalBiller_id = biller_id;
        call.enqueue(new Callback<fetchBill>() {
            @Override
            public void onResponse(Call<fetchBill> call, Response<fetchBill> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                        fetchBill bill = response.body();
                        utils.bill_id = bill.getPayload().get(0).getId();

                        LinkedHashMap<String, String> variableData = new LinkedHashMap<>();
                        variableData.putAll(bill.getPayload().get(0).getAmountOptions());
                        variableData.putAll(bill.getPayload().get(0).getInputparams_value());
                        variableData.putAll(bill.getPayload().get(0).getBiller_additional_info());

                        Intent i = new Intent(context, EMIDetailsBillFetch.class);
                        i.putExtra("url", url);
                        i.putExtra("variableData", variableData);
                        i.putExtra("bill", bill);
                        i.putExtra("exact", exactness);
                        i.putExtra("biller_name", getIntent().getStringExtra("biller_name"));
                        i.putExtra("biller_id", finalBiller_id);
                        startActivity(i);
                    }else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                            Log.e("tagg",response.body().getMessage());
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                        }
                    }
                }
                else{
                    Log.e("tag", "onResponse: "+response.code());
                }
                binding.linearLayout.setVisibility(View.VISIBLE);
                binding.progressbar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<fetchBill> call, Throwable t) {
                Log.d("tag", "onFailure: bill fetch"+t.toString());
                Toast.makeText(context, "Error fetching Bill", Toast.LENGTH_SHORT).show();
            }
        });
    }
}