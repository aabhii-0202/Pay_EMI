package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.Toast;

import com.mediustechnologies.payemi.ApiResponse.getAllBanks;
import com.mediustechnologies.payemi.ApiResponse.loancategory;
import com.mediustechnologies.payemi.adapters.bankListAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityEmiCategoriesBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmiCategories extends AppCompatActivity {
    private ActivityEmiCategoriesBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmiCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        api();
    }

    private void setdata(List<loancategory> data){

        List<getAllBanks> banklist = new ArrayList<>();

        for(int i=0;i<data.size();i++){

            getAllBanks temp = new getAllBanks();
            temp.setBank_name(data.get(i).getCategory_name());
            temp.setId(data.get(i).getId());
            temp.setBank_logo_url(data.get(i).getUrl());
            banklist.add(temp);
        }


        RecyclerView category = binding.rec;
        GridLayoutManager gridLayoutManager  = new GridLayoutManager(context,3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        category.setLayoutManager(gridLayoutManager);
        bankListAdapter adapter = new bankListAdapter(banklist);
        category.setAdapter(adapter);
        adapter.setOnItemClickListner(position -> {
            Intent i = new Intent(context, BankList.class);
            i.putExtra("loan_category",banklist.get(position).getBank_name());
            startActivity(i);
        });


    }

    private void api() {

//        utils.access_token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjQyNjc1MjEwLCJpYXQiOjE2NDI1ODg4MTAsImp0aSI6Ijk1YTkxZGYzZTJhODRiOWY5ODU1ZjQ4NTIwMjQ3YTk0IiwidXNlcl9pZCI6NH0.yEu8ikmsqqIIUuDyAGp540goMv7DjmWZQypC-dZpGg0";

        Call<List<loancategory>> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getLoanCategory(utils.access_token);

        call.enqueue(new Callback<List<loancategory>>() {
            @Override
            public void onResponse(Call<List<loancategory>> call, Response<List<loancategory>> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&& response.body()!=null){
                    List<loancategory> data = response.body();

                    if(data.size()>0) setdata(data);

                }
                else{
                    Log.e("tag",""+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<loancategory>> call, Throwable t) {
                Log.e("tag","Error in loading loan categories, "+t.toString());
                Toast.makeText(context, "Error fetching data, Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init(){
        binding.back.setOnClickListener(view -> finish());
    }
}