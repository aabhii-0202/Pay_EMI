package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.mediustechnologies.payemi.Models.bankSubItem;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.adapters.bankListAdapter;
import com.mediustechnologies.payemi.adapters.bankSublistAdapter;
import com.mediustechnologies.payemi.databinding.ActivityBankSubCategoriesBinding;
import java.util.ArrayList;
import java.util.List;

public class BankSubCategories extends AppCompatActivity {

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
        initRecyclerView();
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
        bankSubList.add(new bankSubItem(R.drawable.axis_financne));
        bankSubList.add(new bankSubItem(R.drawable.axis_financne));

    }

    private void init(){
        binding.backButton.setOnClickListener(view -> finish());
        binding.bharatBillLogo.setOnClickListener(view -> startActivity(new Intent(this,paymentInfo.class)));
    }
}