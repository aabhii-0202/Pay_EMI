package com.mediustechnologies.payemi.activities;

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
import com.mediustechnologies.payemi.DTO.HomepageDTO;
import com.mediustechnologies.payemi.DTO.billFetchDTO;
import com.mediustechnologies.payemi.adapters.fetchBillAdapter2;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiDetailsBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetLoanDetails extends BaseAppCompatActivity {
    private final Context context = this;
    private ActivityPayEmiDetailsBinding binding;
    private fetchBill bill;
    private HomepageDTO data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPayEmiDetailsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        init();
        fetchBill();
    }
    private void init(){

        data = getIntent().getParcelableExtra("data");

        String totalemi = data.getLoan_amount();
        String loanpaied = data.getLoan_paid();

        int progress;
        try{
            double total = Double.parseDouble(totalemi);
            double paied = Double.parseDouble(loanpaied);

            int p = (int) ((paied/total)*100);
            progress = p;


        }catch (Exception e){
            progress = -1;

        }

        if(progress==-1) binding.emiProgressbar.setVisibility(View.GONE);
        else {
            binding.emiProgressbar.setProgress(progress);
        }

        try{
            Glide.with(binding.financerlogo).load(data.getBiller__logo_url()).into(binding.financerlogo);
            binding.amount.setText("Rs."+data.getAmount());
            binding.productname.setText(data.getLoan_type());

            String temp = data.getLoan_paid();
            temp = formatinword(temp);
            binding.paidamount.setText(temp);
            temp = data.getLoan_amount();
            temp = formatinword(temp);
            binding.totalLoan.setText(temp);
            binding.interestRate.setText("NA");
            binding.emi.setText(data.getEmi());
            binding.Tenure.setText("NA");


        }
        catch (Exception e){}

        binding.scrollviewshare.setVisibility(View.GONE);
        binding.backButton.setOnClickListener(view -> finish());
        binding.paybtn.setOnClickListener(view ->nextscreen());
        String bankname = getIntent().getStringExtra("bankname");
        binding.FinancerName.setText(bankname);

    }

    private void nextscreen(){

        Intent i = new Intent(context,Exactness.class);

        i.putExtra("data",data);
        i.putExtra("type",data.getLoan_type());
        i.putExtra("bill_id",bill.getPayload().get(0).getId());

        i.putExtra("customer",bill.getPayload().get(0).getCustomer_name());
        i.putExtra("amount",bill.getPayload().get(0).getAmount());

        i.putExtra("profile_id",bill.getPayload().get(0).getProfile_id());

        String name = getIntent().getStringExtra("billerName");
        i.putExtra("billerName",name);



        startActivity(i);
    }

    private void fetchBill() {

        String loan_id = getIntent().getStringExtra("loan_id");

        Call<fetchBill> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getLoanDetails(utils.access_token,loan_id,utils.profileId);

        call.enqueue(new Callback<fetchBill>() {
            @Override
            public void onResponse(Call<fetchBill> call, Response<fetchBill> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {


                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        bill = response.body();

                        utils.bill_id = response.body().getPayload().get(0).getId();

                        binding.paybtn.setVisibility(View.VISIBLE);
                        setData(bill);
                        binding.progress.setVisibility(View.GONE);
                        binding.scrollviewshare.setVisibility(View.VISIBLE);
                    }
                    else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                        }
                    }



                }else if (response.code()==400){
                    Toast.makeText(context, "Phone number not linked to loan, please enter with linked phone number", Toast.LENGTH_LONG).show();
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

        try {


            billFetchDTO data = bill.getPayload().get(0);
            if (data.getAmount() != null) {
                binding.basebill.setText(data.getAmount());
            } else binding.basebillamountholder.setVisibility(View.GONE);

            if (data.getEmi() != null) {
                binding.emi.setText(data.getEmi());
            } else binding.emiholder.setVisibility(View.GONE);

            if (data.getTenure() != null) {
                binding.Tenure.setText(data.getTenure());
            } else binding.tenureholder.setVisibility(View.GONE);

            if (data.getRespDueDate() != null) {
                binding.duedate.setText(data.getRespDueDate());
            } else binding.duedateholder.setVisibility(View.GONE);

            if (data.getCharges_levied() != null) {
                binding.charges.setText(data.getCharges_levied());
            } else binding.chargeslevivedholder.setVisibility(View.GONE);

            LinkedHashMap<String, String> variableData = new LinkedHashMap<>();
            variableData.putAll(data.getAmountOptions());
            variableData.putAll(data.getInputparams_value());
            variableData.putAll(data.getBiller_additional_info());

            recyclerview(variableData);
        }
        catch (Exception e){

        }

    }

    private void recyclerview(LinkedHashMap<String, String> bill) {

        RecyclerView recyclerView = binding.fetchBillRecyclerView2;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        fetchBillAdapter2 adapter = new fetchBillAdapter2(bill);
        recyclerView.setAdapter(adapter);
    }

    private String formatinword(String total_amount) {
        String ans = "₹";
        try{
            int length = total_amount.length();
            if(length>7){
                ans += total_amount.substring(0,length-7)+"."+total_amount.substring(length-7,length-6)+"Cr";
            }
            else if(length>5){
                ans += total_amount.substring(0,length-5)+"."+total_amount.substring(length-5,length-4)+"L";
            }else if(length>3){
                ans += total_amount.substring(0,length-3)+"."+total_amount.substring(length-3,length-2)+"K";
            }
            else ans += total_amount;
        }
        catch (Exception e){
            return "null";
        }
        return ans;
    }
}