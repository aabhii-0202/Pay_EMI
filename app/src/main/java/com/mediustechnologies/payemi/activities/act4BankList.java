package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mediustechnologies.payemi.Models.getAllBanks;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.adapters.bankListAdapter;
import com.mediustechnologies.payemi.databinding.ActivityBankListBinding;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act4BankList extends AppCompatActivity {

    private ActivityBankListBinding binding;
    private List<getAllBanks> banklist;
    private GridLayoutManager gridLayoutManager ;
    private bankListAdapter adapter;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        addItemsInRecyclerView();




    }


    private void addItemsInRecyclerView() {


        Log.d("tag","Access Token Saved in Utils "+utils.access_token);
        Call<List<getAllBanks>> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().getAllBanks(utils.access_token);

        call.enqueue(new Callback<List<getAllBanks>>() {
            @Override
            public void onResponse(Call<List<getAllBanks>> call, Response<List<getAllBanks>> response) {

                if(response.code()==200) {
//                    Log.d("tag", "setdata: "+banklist.toString());
                    banklist  = response.body();
                    initRecyclerView();
                }
                else{
                    Log.d("tag", "Get BankList onResponse: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<getAllBanks>> call, Throwable t) {
                Log.d("tag","Failed"+t.toString());
            }
        });




    }



    private void initRecyclerView() {
        RecyclerView bankRecyclerView = binding.listOfBanks;
        gridLayoutManager = new GridLayoutManager(context,3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bankRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new bankListAdapter(banklist);
        bankRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListner(new bankListAdapter.onItemClicked() {
            @Override
            public void onItemClick(int position) {
                //on item click listner
                Intent i = new Intent(context,act5BankSubCategories.class);
                i.putExtra("name",banklist.get(position).getBank_name());
                i.putExtra("imgurl",banklist.get(position).getBank_logo_url());
                startActivity(i);
            }
        });




    }

    private void init(){

        binding.backButton.setOnClickListener(view -> finish());





    }
}