package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mediustechnologies.payemi.Models.emiListItem;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.adapters.emiListItemAdapter;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class act33payEMI_home extends AppCompatActivity {
    private ActivityPayEmiHomeBinding binding;
    private List<emiListItem> emilist;
    private final Context context = this;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        init();
        dummydata();
        initrecycler();
    }

    private void dummydata() {
        emilist = new ArrayList<>();

        emilist.add(new emiListItem("Rs 15,000","ICICI Bank Limited","Home Loan","₹ 52L","₹ 80L",R.drawable.b9,60));
        emilist.add(new emiListItem("Rs 1,000","SBI","Edu Loan","₹ 1.2L","₹ 2L",R.drawable.b2,60));
        emilist.add(new emiListItem("Rs 8,000","HDFC Bank Limited","Car Loan","₹ 5.2L","₹ 12L",R.drawable.b3,45));
        emilist.add(new emiListItem("Rs 1,000","SBI","Edu Loan","₹ 1.2L","₹ 2L",R.drawable.b2,60));
        emilist.add(new emiListItem("Rs 10,000","ICICI Bank Limited","Car Loan","₹ 5.2L","₹ 12L",R.drawable.b9,48));
        emilist.add(new emiListItem("Rs 19,000","ICICI Bank Limited","Home Loan","₹ 52L","₹ 80L",R.drawable.b9,60));
        emilist.add(new emiListItem("Rs 12,000","SBI","Edu Loan","₹ 1.2L","₹ 2L",R.drawable.b2,70));
        emilist.add(new emiListItem("Rs 10,000","HDFC Bank Limited","Car Loan","₹ 6L","₹ 12L",R.drawable.b3,50));
        emilist.add(new emiListItem("Rs 3,000","SBI","Edu Loan","₹ 1.2L","₹ 2L",R.drawable.b2,80));



    }

    private void initrecycler() {
        RecyclerView emilistRecycler = binding.loanrecyclerview;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        emilistRecycler.setLayoutManager(linearLayoutManager);
        emiListItemAdapter adapter = new emiListItemAdapter(emilist);
        emilistRecycler.setAdapter(adapter);
    }

    private void init(){
        binding.addbutton.setOnClickListener(view -> startActivity(new Intent(context, act34pay_EMI_Details.class)));
    }
}