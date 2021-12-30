package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.recyclerItems.bankSubItem;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.adapters.bankListAdapter;
import com.mediustechnologies.payemi.adapters.bankSublistAdapter;
import com.mediustechnologies.payemi.databinding.ActivityBankSubCategoriesBinding;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act5BankSubCategories extends AppCompatActivity {

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

        addDummmyItemsInRecyclerView();

    }

    private void initRecyclerView() {
        bankSubListRecyclerview = binding.ListRecyclerView;
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bankSubListRecyclerview.setLayoutManager(linearLayoutManager);
        adapter = new bankSublistAdapter(bankSubList);
        bankSubListRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListner(new bankListAdapter.onItemClicked() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(context, position+" position item clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addDummmyItemsInRecyclerView() {
        bankSubList = new ArrayList<>();

        String name = getIntent().getStringExtra("name");
        Call<List<bankSubItem>> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().getBillerByBank(name);

        call.enqueue(new Callback<List<bankSubItem>>() {
            @Override
            public void onResponse(Call<List<bankSubItem>> call, Response<List<bankSubItem>> response) {
                bankSubList = response.body();
                initRecyclerView();

                if(bankSubList!=null) {
                    String s = bankSubList.get(0).toString()+" "+bankSubList.get(1).toString();
                    Log.d("tag", "SUb response " + s);
                }
            }

            @Override
            public void onFailure(Call<List<bankSubItem>> call, Throwable t) {
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void init(){
        binding.backButton.setOnClickListener(view -> finish());
        binding.bharatBillLogo.setOnClickListener(view -> startActivity(new Intent(this, act6addLoadAccount.class)));
    }
}