package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.mediustechnologies.payemi.Models.transaction_chat;
import com.mediustechnologies.payemi.adapters.transction_chatAdapter;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiTransactionPageBinding;

import java.util.ArrayList;
import java.util.List;

public class payEMI_transaction_page extends AppCompatActivity {
    private Context context = this;
    private ActivityPayEmiTransactionPageBinding binding;
    private ArrayList<transaction_chat> chatlist;
    private transction_chatAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiTransactionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        dummydata();
        initrecyclerview();
    }

    private void dummydata(){
        chatlist = new ArrayList<>();
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct"));
        chatlist.add(new transaction_chat("Scratch Now","Earn a reward!","",null));
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct"));
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct"));
        chatlist.add(new transaction_chat("Rs. 140","You earned a reward!","",null));
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct"));
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct"));
        chatlist.add(new transaction_chat("Scratch Now","Earn a reward!","",null));
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct"));
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct"));
        chatlist.add(new transaction_chat("Rs. 140","You earned a reward!","",null));
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct"));

    }

    private void initrecyclerview(){
        recyclerView = binding.paymentRecyclerView;
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new transction_chatAdapter(context,chatlist);
        recyclerView.setAdapter(adapter);

    }

    private void init(){

    }
}