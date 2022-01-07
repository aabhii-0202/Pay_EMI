package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.Models.fetchBill;
import com.mediustechnologies.payemi.activities.apiBody.fetchBillBody;
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
    private String url,name,amount,customer ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.scrollView.setVisibility(View.GONE);
        url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("biller_name");

        binding.FinancerName.setText(name);
        Glide.with(binding.financerlogo).load(url).into(binding.financerlogo);
        binding.backButton.setOnClickListener(view -> finish());
        binding.payNow.setOnClickListener(view -> nextscreen());

        fetchBill();

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
                if(response.code()==200&&response.body()!=null){
                    fetchBill bill = response.body();

                    binding.payNow.setVisibility(View.VISIBLE);
//                    exactness = bill.getPayload().get("payment_exactness");
                    setData(bill);
                    binding.progress.setVisibility(View.GONE);

                }else if (response.code()==400){
                    Toast.makeText(context, "Phone number not linked to loan, please enter with linked phone number", Toast.LENGTH_LONG).show();
                    utils.loginAgain(context,getApplicationContext());
                    binding.progress.setVisibility(View.GONE);
                }else if (response.code()==401){
                    Toast.makeText(context, "Token Expired", Toast.LENGTH_LONG).show();
                    utils.loginAgain(context,getApplicationContext());
                    binding.progress.setVisibility(View.GONE);
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
        binding.scrollView.setVisibility(View.VISIBLE);
        amount = bill.getPayload().get(0).getAmount();
        customer = bill.getPayload().get(0).getCustomer_name();

//        String s = "Not in API";

//        binding.ChargesLevied.setText(s);
//
//        binding.LatePaymentFee.setText(s);
//
//        binding.AdditionalCharges.setText(s);
//
//        binding.FixedCharges.setText(s);
//
//        binding.EMI.setText(s);

//        binding.ServiceTax.setText(s);

        if(bill.getPayload().get(0).getRespDueDate()!=null)
            binding.DueDate.setText(bill.getPayload().get(0).getRespDueDate());
        else binding.duedatelayout.setVisibility(View.GONE);


        if(bill.getPayload().get(0).getAmount()!=null)
            binding.basebillamount.setText(bill.getPayload().get(0).getAmount());
        else binding.basebillamountcontainer.setVisibility(View.GONE);


        if(bill.getPayload().get(0).getRespBillPeriod()!=null)
            binding.Tenure.setText(bill.getPayload().get(0).getRespBillPeriod());
        else binding.tenurelayout.setVisibility(View.GONE);


        if(bill.getPayload().get(0).getAmount()!=null)
            binding.Amount.setText(bill.getPayload().get(0).getAmount());
        else binding.amountlayout.setVisibility(View.GONE);

        if(bill.getPayload().get(0).getAmount()!=null)
            binding.TotalAmount.setText(bill.getPayload().get(0).getAmount());
        else binding.totalamountlayout.setVisibility(View.GONE);

        LinkedHashMap<String,String> variableData = new LinkedHashMap<>();
        variableData.putAll(bill.getPayload().get(0).getAmountOptions());
        variableData.putAll(bill.getPayload().get(0).getInputparams_value());
        variableData.putAll(bill.getPayload().get(0).getBiller_additional_info());


        recyclerview(variableData);




    }

    private void recyclerview(LinkedHashMap<String, String> bill) {

        RecyclerView recyclerView = binding.variablerec;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        fetchBillAdapter adapter = new fetchBillAdapter(bill);
        recyclerView.setAdapter(adapter);
    }

    private void nextscreen(){
        Intent i = new Intent(context, act8payEMI.class);
        i.putExtra("logo",url);
//        i.putExtra("Exactness",exactness);
        i.putExtra("customer",customer);
        i.putExtra("amount",amount);
        i.putExtra("billerName",name);
        startActivity(i);
    }

}