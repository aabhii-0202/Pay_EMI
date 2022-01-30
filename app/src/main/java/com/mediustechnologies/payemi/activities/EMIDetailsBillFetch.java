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
import com.mediustechnologies.payemi.ApiResponse.fetchBill;
import com.mediustechnologies.payemi.ApiResponse.homePage;
import com.mediustechnologies.payemi.activities.apiBody.fetchBillBody;
import com.mediustechnologies.payemi.adapters.fetchBillAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityPaymentInfoBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.HashMap;
import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EMIDetailsBillFetch extends BaseAppCompatActivity {

    private ActivityPaymentInfoBinding binding;
    private final Context context = this;
    private String url,name,amount,customer,bill_id,profile_id,exactness ;


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

        fetchBill bill = getIntent().getParcelableExtra("bill");
        binding.payNow.setVisibility(View.VISIBLE);

        setData(bill);
        bill_id = bill.getPayload().get(0).getId();
        profile_id = bill.getPayload().get(0).getProfile_id();
        binding.progress.setVisibility(View.GONE);

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

        if(bill.getExactness()!=null){
            exactness = bill.getExactness();
        }

        HashMap<String,String> variableData = new HashMap<>();

        variableData = (HashMap<String, String>) getIntent().getSerializableExtra("variableData");


        recyclerview(variableData);

    }

    private void recyclerview(HashMap<String, String> bill) {

        RecyclerView recyclerView = binding.variablerec;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        fetchBillAdapter adapter = new fetchBillAdapter(bill);
        recyclerView.setAdapter(adapter);
    }

    private void nextscreen(){
        Intent i = new Intent(context, Exactness.class);
        i.putExtra("logo",url);
        i.putExtra("customer",customer);
        i.putExtra("amount",amount);
        i.putExtra("billerName",name);
        i.putExtra("bill_id",bill_id);
        i.putExtra("profile_id",profile_id);
        i.putExtra("exact",exactness);


        startActivity(i);
    }

}