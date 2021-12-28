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

        addDummmyItemsInRecyclerView();




    }

    private void setdata(List<getAllBanks> data) {

        Log.d("tag", "setdata: "+data.toString());
        banklist = new ArrayList<>();

        banklist = data;
        initRecyclerView();



    }
    private void addDummmyItemsInRecyclerView() {


        Call<List<getAllBanks>> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().getAllBanks();

        call.enqueue(new Callback<List<getAllBanks>>() {
            @Override
            public void onResponse(Call<List<getAllBanks>> call, Response<List<getAllBanks>> response) {
                List<getAllBanks> data = response.body();

                setdata(data);
            }

            @Override
            public void onFailure(Call<List<getAllBanks>> call, Throwable t) {

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
                startActivity(new Intent(context, act5BankSubCategories.class));
            }
        });




    }

    private void init(){

        binding.backButton.setOnClickListener(view -> finish());





    }
}