package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mediustechnologies.payemi.ApiResponse.getAllBanks;
import com.mediustechnologies.payemi.activities.login.SendOTP;
import com.mediustechnologies.payemi.adapters.catagoryAdapter;
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

public class BankList extends AppCompatActivity {

    private ActivityBankListBinding binding;
    private List<getAllBanks> banklist;
    private GridLayoutManager gridLayoutManager ;
    private bankListAdapter adapter;
    private final Context context = this;
    private ArrayList<String> catagories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        int p = getIntent().getIntExtra("position",0);
        initcatagoriesRecyclerview(p);

        addItemsInRecyclerView(getIntent().getStringExtra("loan_category"));




    }

    private void initcatagoriesRecyclerview(int p) {
        catagories = getIntent().getStringArrayListExtra("catagories");
        RecyclerView catagory = binding.catagory;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        catagory.setLayoutManager(linearLayoutManager);
        catagory.scrollToPosition(p);


        catagoryAdapter ad = new catagoryAdapter(catagories, p);
        catagory.setAdapter(ad);

        ad.setOnCatagoryClickListner(new catagoryAdapter.oncatagoryClick() {
            @Override
            public void onCatagoryClick(int position) {
                addItemsInRecyclerView(catagories.get(position));
                initcatagoriesRecyclerview(position);

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

        searchbar();
        adapter.setOnItemClickListner(position -> {
            Intent i = new Intent(context, BillerList.class);
            i.putExtra("name",banklist.get(position).getBank_name());
            i.putExtra("imgurl",banklist.get(position).getBank_logo_url());
            i.putExtra("count",banklist.get(position).getCount());
            startActivity(i);
        });
    }

    private void searchbar() {

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String text){
        List<getAllBanks> filteredList = new ArrayList<>();

        for(getAllBanks item : banklist){
            if(item.getBank_name().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);

    }


    private void addItemsInRecyclerView(String loan_category) {


        Log.d("tag","Access Token Saved in Utils "+utils.access_token);
        Call<List<getAllBanks>> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getAllBanks(utils.access_token,loan_category);

        call.enqueue(new Callback<List<getAllBanks>>() {
            @Override
            public void onResponse(Call<List<getAllBanks>> call, Response<List<getAllBanks>> response) {

                if(response.code()==utils.RESPONSE_SUCCESS&&response.isSuccessful()&&response.body()!=null) {
//                    Log.d("tag", "setdata: "+banklist.toString());
                    banklist  = response.body();
                    initRecyclerView();
                }
                if(response.code()==400){
                    Log.d("tag","Error code 400 on fetch bill. ErrorCode: BOU001");
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





    private void init(){


        binding.backButton.setOnClickListener(view -> finish());
        binding.logout.setOnClickListener(view -> {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("PAY_EMI", MODE_PRIVATE);
            preferences.edit().putString("phone", "").apply();
            preferences.edit().putString("refresh_token", "Bearer ").apply();
            preferences.edit().putString("token", "Bearer ").apply();
            preferences.edit().putString("profileid","").apply();

            Intent i = new Intent(context, SendOTP.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        });




    }
}