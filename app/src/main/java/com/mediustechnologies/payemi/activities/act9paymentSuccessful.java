package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mediustechnologies.payemi.DTO.billFetchDTO;
import com.mediustechnologies.payemi.Models.billDetails;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.scratchCard.fragment.DemoFragment;
import com.mediustechnologies.payemi.adapters.GetBillDetailsAdapter;
import com.mediustechnologies.payemi.adapters.fetchBillAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityPaymentSuccessfulBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.LinkedHashMap;
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

    private void setData(billFetchDTO data) {

        Log.d("tag", "act9paymentSuccessful " + data.toString());

        String bankname = getIntent().getStringExtra("billerName");
        binding.bankName.setText("To "+bankname);
        binding.loanname.setText("Not in API");
        binding.paidAmount.setText(data.getAmount());
        binding.timeanddate.setText("  Completed | "+data.getTransaction_date_and_time());

        binding.recieptBankName.setText(bankname);

        if(data.getRespBillPeriod()!=null)
            binding.BillPeriod.setText(data.getRespBillPeriod());
        else binding.billperiodholder.setVisibility(View.GONE);

        if(data.getTransaction_date()!=null)
            binding.BillDate.setText(data.getTransaction_date());
        else binding.billdateholder.setVisibility(View.GONE);

        if(data.getId()!=null)
            binding.BillNumber.setText(data.getId());
        else binding.billnumberholder.setVisibility(View.GONE);

        if(data.getBill_number()!=null)
            binding.BillerID.setText(data.getBill_number());
        else binding.billeridholder.setVisibility(View.GONE);

        if(data.getAmount()!=null)
            binding.TotalAmount.setText(data.getAmount());
        else binding.totalamountholder.setVisibility(View.GONE);

        if(data.getTransation_status()!=null)
            binding.TransactionStatus.setText(data.getTransation_status());
        else binding.transactionstatusholder.setVisibility(View.GONE);

        if(data.getTransaction_id()!=null)
            binding.TransactionID.setText(data.getTransaction_id());
        else binding.transactionidholder.setVisibility(View.GONE);

        if(data.getTransaction_date_and_time()!=null)
            binding.TransactionDateTime.setText(data.getTransaction_date_and_time());
        else binding.transactiondateholder.setVisibility(View.GONE);

        if(data.getCustomer_name()!=null)
            binding.CName.setText(data.getCustomer_name());
        else binding.custnameholder.setVisibility(View.GONE);

        if(data.getCustomer_mobile()!=null)
            binding.CNumber.setText(data.getCustomer_mobile());
        else binding.customernumberholder.setVisibility(View.GONE);

        if(data.getInitiation_channel()!=null)
            binding.InitiatingChannel.setText(data.getInitiation_channel());
        else binding.channelholder.setVisibility(View.GONE);

        if(data.getPayment_mode()!=null)
            binding.paymentMode.setText(data.getPayment_mode());
        else binding.modelholder.setVisibility(View.GONE);

        if(data.getAmount()!=null)
            binding.BillAmount.setText(data.getAmount());
        else binding.billamountholder.setVisibility(View.GONE);

        if(data.getService_tax()!=null)
            binding.serviceTax.setText(data.getService_tax());
        else binding.servicetaxholder.setVisibility(View.GONE);

//        binding.ApprovalNumber.setText("Not in API");
//        binding.convineanceFee.setText(data.getcustomer_convinience_fees());

        binding.totalAmount.setText(data.getAmount());

        LinkedHashMap<String,String> variableData = new LinkedHashMap<>();
        variableData.putAll(data.getAmountOptions());
        variableData.putAll(data.getInputparams_value());
        variableData.putAll(data.getBiller_additional_info());
        recyclerview(variableData);

    }

    private void recyclerview(LinkedHashMap<String, String> variableData) {
        RecyclerView recyclerView = binding.variablerec2;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        GetBillDetailsAdapter adapter = new GetBillDetailsAdapter(variableData);
        recyclerView.setAdapter(adapter);

    }

    private void getbilldetails() {
        String bill_id =  "395";
        String token = utils.access_token;

        Call<List<billFetchDTO>> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().getBillDetails(token,bill_id);

        call.enqueue(new Callback<List<billFetchDTO>>() {
            @Override
            public void onResponse(Call<List<billFetchDTO>> call, Response<List<billFetchDTO>> response) {
                if (response.code() == 200 && response.body() != null) {
                    billFetchDTO data = response.body().get(0);
                    setData(data);
                }
                else{
                    Log.d("tag", "onResponse: getbill detail "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<billFetchDTO>> call, Throwable t) {
                Log.d("tag","getbill details "+t.toString());
            }
        });
    }

    private void init() {
        binding.crossButton.setOnClickListener(view -> finish());
        binding.scratchpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d = new Dialog(context);
                d.setContentView(R.layout.scratchcardlayout);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();

                com.mediustechnologies.payemi.activities.scratchCard.ui.ScratchCardLayout card = d.findViewById(R.id.scratchCard);
                card.setRevealFullAtPercent(10);


            }
        });
    }
}