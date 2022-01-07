package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mediustechnologies.payemi.recyclerItems.transaction_chat;
import com.mediustechnologies.payemi.adapters.transction_chatAdapter;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiTransactionPageBinding;

import java.util.ArrayList;

public class act39payEMI_transaction_page extends AppCompatActivity {
    private final Context context = this;
    private ActivityPayEmiTransactionPageBinding binding;
    private ArrayList<transaction_chat> chatlist;
    private final String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiTransactionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        dummydata();
//        getAllCashbacks();
        initrecyclerview();
    }

    private void getAllCashbacks() {

        String profile_id = 2 + "";//dummy profile id for now



    }

    private void dummydata() {
        chatlist = new ArrayList<>();
        chatlist.add(new transaction_chat(null, null, null, null, "15 Oct 6:24PM"));
        chatlist.add(new transaction_chat("Payment to HDFC Bank", "Car Loan", "₹ 14,288", "  Paid | 15 Oct", null));
        chatlist.add(new transaction_chat("Scratch Now", "Earn a reward!", "", null, null));
        chatlist.add(new transaction_chat("Payment to HDFC Bank", "Car Loan", "₹ 14,288", "  Paid | 15 Oct", null));
        chatlist.add(new transaction_chat("Payment to HDFC Bank", "Car Loan", "₹ 14,288", "  Paid | 15 Oct", null));
        chatlist.add(new transaction_chat("Rs. 140", "You earned a reward!", "", null, null));
        chatlist.add(new transaction_chat("Payment to HDFC Bank", "Car Loan", "₹ 14,288", "  Paid | 15 Oct", null));
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct",null));
        chatlist.add(new transaction_chat("Scratch Now","Earn a reward!","",null,null));
        chatlist.add(new transaction_chat(null,null,null,null,"18 Oct 6:24PM"));
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct",null));
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct",null));
        chatlist.add(new transaction_chat("Rs. 140","You earned a reward!","",null,null));
        chatlist.add(new transaction_chat("Payment to HDFC Bank","Car Loan","₹ 14,288","  Paid | 15 Oct",null));

    }

    private void initrecyclerview(){
        RecyclerView recyclerView = binding.paymentRecyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        transction_chatAdapter adapter = new transction_chatAdapter(context, chatlist);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListner(new transction_chatAdapter.onItemClick() {
            @Override
            public void onItemClick(int postion) {
                Intent i = new Intent(context,act40transaction_Details.class);
                i.putExtra("amount",chatlist.get(postion).getAmount());
                startActivity(i);
            }
        });


    }

    private void init(){

        String name = getIntent().getStringExtra("name");
        binding.bankname.setText(name);
        binding.backButton.setOnClickListener(view -> finish());
        binding.dotts.setOnClickListener(view ->startActivity(new Intent(context, act40transaction_Details.class)));

    }
}