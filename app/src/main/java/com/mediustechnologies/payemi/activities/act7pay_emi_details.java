package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mediustechnologies.payemi.Models.fetchBill;
import com.mediustechnologies.payemi.Models.fetchBillBody;
import com.mediustechnologies.payemi.adapters.fetchBillAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityPaymentInfoBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.HashMap;
import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act7pay_emi_details extends AppCompatActivity {

    private ActivityPaymentInfoBinding binding;
    private final Context context = this;
    private String exactness ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(view -> finish());
        binding.payNow.setOnClickListener(view -> startActivity(new Intent(context, act8payEMI.class)));

        fetchBill();


    }

    private void fetchBill() {

        String biller_id = "OU12LO000NATGJ";
        String mobile = "9898990861";
        String loanNumber = "2775864";
        fetchBillBody body = new fetchBillBody(loanNumber,mobile);

        Call<fetchBill> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().fetchBill(utils.access_token,biller_id,mobile,body);

        call.enqueue(new Callback<fetchBill>() {
            @Override
            public void onResponse(Call<fetchBill> call, Response<fetchBill> response) {
                if(response.code()==200){
                    fetchBill bill = response.body();

                    recyclerview(bill);
                    exactness = bill.getPayload().get("payment_exactness");


                }
            }

            @Override
            public void onFailure(Call<fetchBill> call, Throwable t) {
                Log.d("tag", "onFailure: bill fetch"+t.toString());
                Toast.makeText(context, "Error fetching Bill", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void recyclerview(fetchBill bill) {

        RecyclerView recyclerView = binding.fetchBillRecyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        LinkedHashMap<String,String> map = bill.getPayload();
        fetchBillAdapter adapter = new fetchBillAdapter(map);
        recyclerView.setAdapter(adapter);
    }

    private void nextscreen(){
        Intent i = new Intent(context, act8payEMI.class);
        i.putExtra("Exactness",exactness);
        startActivity(i);
    }

}