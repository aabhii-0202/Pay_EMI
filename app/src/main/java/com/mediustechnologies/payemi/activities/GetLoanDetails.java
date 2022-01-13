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

public class GetLoanDetails extends AppCompatActivity {
    private final Context context = this;
    private ActivityPayEmiDetailsBinding binding;
    private fetchBill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPayEmiDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        init();
        fetchBill();
    }
    private void init(){

        binding.emi.setText(getIntent().getStringExtra("emi"));
        binding.scrollview.setVisibility(View.GONE);
        binding.backButton.setOnClickListener(view -> finish());
        binding.paybtn.setOnClickListener(view ->nextscreen());
        String bankname = getIntent().getStringExtra("bankname");
        binding.FinancerName.setText(bankname);

    }

    private void nextscreen(){

        Intent i = new Intent(context,Exactness.class);
        i.putExtra("bill_id",bill.getPayload().get(0).getId());

        i.putExtra("customer",bill.getPayload().get(0).getCustomer_name());
        i.putExtra("amount",bill.getPayload().get(0).getAmount());

        i.putExtra("profile_id",bill.getPayload().get(0).getProfile_id());
        String url = getIntent().getStringExtra("logo");
        i.putExtra("logo",url);
        String name = getIntent().getStringExtra("billerName");
        i.putExtra("billerName",name);



        startActivity(i);
    }

    private void fetchBill() {

        String loan_id = getIntent().getStringExtra("loan_id");

        Call<fetchBill> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().getLoanDetails(utils.access_token,loan_id,utils.profileId);

        call.enqueue(new Callback<fetchBill>() {
            @Override
            public void onResponse(Call<fetchBill> call, Response<fetchBill> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    bill = response.body();

                    utils.bill_id = response.body().getPayload().get(0).getId();

                    binding.paybtn.setVisibility(View.VISIBLE);
//                    exactness = bill.getPayload().get("payment_exactness");
                    setData(bill);
                    binding.progress.setVisibility(View.GONE);
                    binding.scrollview.setVisibility(View.VISIBLE);

                }else if (response.code()==400){
                    Toast.makeText(context, "Phone number not linked to loan, please enter with linked phone number", Toast.LENGTH_LONG).show();
                    utils.loginAgain(context);
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