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
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.ApiResponse.bankSubItem;
import com.mediustechnologies.payemi.adapters.bankListAdapter;
import com.mediustechnologies.payemi.adapters.bankSublistAdapter;
import com.mediustechnologies.payemi.databinding.ActivityBankSubCategoriesBinding;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillerList extends BaseAppCompatActivity {

    private ActivityBankSubCategoriesBinding binding;
    private RecyclerView bankSubListRecyclerview;
    private List<bankSubItem> bankSubList;
    private LinearLayoutManager linearLayoutManager;
    private bankSublistAdapter adapter;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankSubCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        addItemsInRecyclerView();

    }

    private void initRecyclerView() {
        bankSubListRecyclerview = binding.ListRecyclerView;
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bankSubListRecyclerview.setLayoutManager(linearLayoutManager);
        adapter = new bankSublistAdapter(bankSubList);
        bankSubListRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListner(position -> {
            Intent i = new Intent(context, AddLoanAccount.class);
            i.putExtra("url",bankSubList.get(position).getLogo_url());
            i.putExtra("biller_id",bankSubList.get(position).getBillerId());
            i.putExtra("biller_name",bankSubList.get(position).getBillerName());
            startActivity(i);
        });

        int count = getIntent().getIntExtra("count",0);
        try {
            if (bankSubList.size()==1) {
                Intent i = new Intent(context, AddLoanAccount.class);
                i.putExtra("url", bankSubList.get(0).getLogo_url());
                i.putExtra("biller_id", bankSubList.get(0).getBillerId());
                i.putExtra("biller_name", bankSubList.get(0).getBillerName());
                startActivity(i);
                finish();
            }
        }catch (Exception e){

        }
        binding.progressbar.setVisibility(View.GONE);


    }

    private void addItemsInRecyclerView() {
        bankSubList = new ArrayList<>();

        String name = getIntent().getStringExtra("name");
        Call<List<bankSubItem>> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getBillerByBank(utils.access_token,name);

        call.enqueue(new Callback<List<bankSubItem>>() {
            @Override
            public void onResponse(Call<List<bankSubItem>> call, Response<List<bankSubItem>> response) {

                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    bankSubList = response.body();
                    Log.d("tag", "biller list "+response.body());
                    initRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<List<bankSubItem>> call, Throwable t) {
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void init(){
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.backButton.setOnClickListener(view -> finish());
        binding.ParentBankName.setText(getIntent().getStringExtra("name"));
        String url = getIntent().getStringExtra("imgurl");
        if(url!=null) Glide.with(binding.financerlogo).load(url).into(binding.financerlogo);
    }
}