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

import com.mediustechnologies.payemi.ApiResponse.fetchBill;
import com.mediustechnologies.payemi.DTO.billFetchDTO;
import com.mediustechnologies.payemi.activities.apiBody.fetchBillBody;
import com.mediustechnologies.payemi.activities.payments.act35payment_Page;
import com.mediustechnologies.payemi.adapters.fetchBillAdapter;
import com.mediustechnologies.payemi.adapters.fetchBillAdapter2;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiDetailsBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act34pay_EMI_Details extends AppCompatActivity {
    private final Context context = this;
    private ActivityPayEmiDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPayEmiDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        init();
        fetchBill();
    }
    private void init(){

        binding.scrollview.setVisibility(View.GONE);
        binding.backButton.setOnClickListener(view -> finish());
        binding.paybtn.setOnClickListener(view -> startActivity(new Intent(context, act35payment_Page.class)));
        String bankname = getIntent().getStringExtra("bankname");
        binding.FinancerName.setText(bankname);

    }

    private void fetchBill() {

        String biller_id = getIntent().getStringExtra("biller_id");
        biller_id = "OU12LO000NATGJ";

        String mobile = utils.phone;
        String loanNumber = "2775864";
        loanNumber = "2775864";
//        utils.access_token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjQxMzA3ODQwLCJpYXQiOjE2NDEyMjE0NDAsImp0aSI6ImYxOGE1ZjdhODA5YTRhNTU4MWUwOTg2ODM3N2Q1NzdmIiwidXNlcl9pZCI6NH0.r1g5N0HObaX0ckz0t3bx8uDoCVX9dunARy7LdChjfMI";
        fetchBillBody body = new fetchBillBody(loanNumber,mobile);

        Call<fetchBill> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().fetchBill(utils.access_token,biller_id,mobile,body);

        call.enqueue(new Callback<fetchBill>() {
            @Override
            public void onResponse(Call<fetchBill> call, Response<fetchBill> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    fetchBill bill = response.body();

                    binding.paybtn.setVisibility(View.VISIBLE);
//                    exactness = bill.getPayload().get("payment_exactness");
                    setData(bill);
                    binding.progress.setVisibility(View.GONE);
                    binding.scrollview.setVisibility(View.VISIBLE);

                }else if (response.code()==400){
                    Toast.makeText(context, "Phone number not linked to loan, please enter with linked phone number", Toast.LENGTH_LONG).show();
                    utils.loginAgain(context,getApplicationContext());
                    binding.progress.setVisibility(View.GONE);
                }else if (response.code()==401){
                    Toast.makeText(context, "Token Expired", Toast.LENGTH_LONG).show();
                    binding.progress.setVisibility(View.GONE);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<fetchBill> call, Throwable t) {
                Log.d("tag", "onFailure: bill fetch"+t.toString());
                Toast.makeText(context, "Error fetching Bill", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void setData(fetchBill bill) {

        billFetchDTO data = bill.getPayload().get(0);
        if(data.getAmount()!=null){
            binding.basebill.setText(data.getAmount());
        }else binding.basebillamountholder.setVisibility(View.GONE);

        LinkedHashMap<String,String> variableData = new LinkedHashMap<>();
        variableData.putAll(data.getAmountOptions());
        variableData.putAll(data.getInputparams_value());
        variableData.putAll(data.getBiller_additional_info());


        recyclerview(variableData);



    }

    private void recyclerview(LinkedHashMap<String, String> bill) {

        RecyclerView recyclerView = binding.fetchBillRecyclerView2;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        fetchBillAdapter2 adapter = new fetchBillAdapter2(bill);
        recyclerView.setAdapter(adapter);
    }
}