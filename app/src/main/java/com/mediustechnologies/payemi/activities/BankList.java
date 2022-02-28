package com.mediustechnologies.payemi.activities;

import androidx.annotation.NonNull;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mediustechnologies.payemi.ApiResponse.banklistResponse;
import com.mediustechnologies.payemi.ApiResponse.getAllBanks;
import com.mediustechnologies.payemi.activities.login.SendOTP;
import com.mediustechnologies.payemi.adapters.catagoryAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.adapters.bankListAdapter;
import com.mediustechnologies.payemi.databinding.ActivityBankListBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankList extends BaseAppCompatActivity {

    private ActivityBankListBinding binding;
    private banklistResponse banklist;
    private GridLayoutManager gridLayoutManager ;
    private bankListAdapter adapter;
    private final Context context = this;
    private ArrayList<String> catagories,ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(view -> finish());

        int p = getIntent().getIntExtra("position",0);
        initcatagoriesRecyclerview(p);


        addItemsInRecyclerView(getIntent().getStringExtra("loan_category_id"));




    }

    private void initcatagoriesRecyclerview(int p) {
        catagories = getIntent().getStringArrayListExtra("catagories");
        ids = getIntent().getStringArrayListExtra("ids");
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
                addItemsInRecyclerView(ids.get(position));
                initcatagoriesRecyclerview(position);

                System.out.println(p+"  "+catagories.get(p));

            }
        });




    }

    private void initRecyclerView() {
        RecyclerView bankRecyclerView = binding.listOfBanks;
        gridLayoutManager = new GridLayoutManager(context,3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bankRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new bankListAdapter(banklist.getData());
        bankRecyclerView.setAdapter(adapter);

        searchbar();
        adapter.setOnItemClickListner(position -> {
            Intent i = new Intent(context, BillerList.class);
            i.putExtra("name",banklist.getData().get(position).getBank_name());
            i.putExtra("imgurl",banklist.getData().get(position).getBank_logo_url());
            i.putExtra("count",banklist.getData().size());

            startActivity(i);
        });
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();
        for(int i=0;i<catagories.size();i++){
            menu.add(0,i,Menu.NONE,catagories.get(i));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        for(int i=0;i<catagories.size();i++){
            if(i==item.getItemId()){
                addItemsInRecyclerView(ids.get(i));
            }
        }


        return super.onOptionsItemSelected(item);
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

        for(getAllBanks item : banklist.getData()){
            if(item.getBank_name().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);

    }


    private void addItemsInRecyclerView(String loan_category_id) {

        Log.d("tag","Access Token Saved in Utils "+utils.access_token);
        Call<banklistResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getAllBanks(utils.access_token,loan_category_id);

        call.enqueue(new Callback<banklistResponse>() {
            @Override
            public void onResponse(Call<banklistResponse> call, Response<banklistResponse> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.isSuccessful()&&response.body().getData()!=null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        banklist = response.body();
                        Log.d("tag", "Banklist setdata: " + banklist.toString());
                        initRecyclerView();
                    }else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                        }
                    }
                }
                else{
                    Log.d("tag", "Get BankList onResponse: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<banklistResponse> call, Throwable t) {
                Log.d("tag", "Get BankList failed : "+t.toString());
            }
        });
    }
}