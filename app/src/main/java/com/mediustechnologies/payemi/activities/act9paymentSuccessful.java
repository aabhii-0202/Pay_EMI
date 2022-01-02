package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mediustechnologies.payemi.Models.billDetails;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.databinding.ActivityPaymentSuccessfulBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act9paymentSuccessful extends AppCompatActivity {

    private ActivityPaymentSuccessfulBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentSuccessfulBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getbilldetails();
    }

    private void setData(billDetails data) {

        Log.d("tag", "act9paymentSuccessful " + data.toString());

        binding.recieptBankName.setText("NA");
        binding.BillPeriod.setText("NA");
        binding.BillDate.setText("NA");
        binding.BillNumber.setText(data.getBill_number());
        binding.BillerID.setText(data.getId());
        binding.TotalAmount.setText(data.getAmount());
        binding.TransactionStatus.setText("NA");
        binding.TransactionID.setText(data.getTransaction_id());
        binding.TransactionDateTime.setText(data.getTransaction_date_and_time());
        binding.ApprovalNumber.setText("NA");
        binding.CName.setText(data.getCustomer_name());
        binding.CNumber.setText(data.getCustomer_mobile());
        binding.InitiatingChannel.setText(data.getInitiation_channel());
        binding.paymentMode.setText(data.getPayment_mode());
        binding.BillAmount.setText("NA");
        binding.convineanceFee.setText(data.getCustomer_convinience_fees());
        binding.serviceTax.setText(data.getService_tax());
        binding.totalAmount.setText("NA");

    }

    private void getbilldetails() {
        String bill_id = 5 + "";
        // dummy billerID
        Call<List<billDetails>> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().getBillDetails(bill_id);

        call.enqueue(new Callback<List<billDetails>>() {
            @Override
            public void onResponse(Call<List<billDetails>> call, Response<List<billDetails>> response) {
                if (response.code() == 200 && response.body() != null) {
                    billDetails data = response.body().get(0);
                    setData(data);
                }
            }

            @Override
            public void onFailure(Call<List<billDetails>> call, Throwable t) {
                billDetails data = null;
                setData(data);
            }
        });
    }

    private void init() {
        binding.crossButton.setOnClickListener(view -> finish());
    }
}