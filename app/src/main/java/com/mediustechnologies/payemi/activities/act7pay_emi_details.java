package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mediustechnologies.payemi.Models.fetchBill;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.databinding.ActivityPaymentInfoBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act7pay_emi_details extends AppCompatActivity {

    private ActivityPaymentInfoBinding binding;
    private final Context context = this;


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

        String Id_biller = 3 + "";
        String loanNumber = "XX45648XXUDD";
        String mobile = "1234567890";



    }

}