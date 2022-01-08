package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mediustechnologies.payemi.ApiResponse.TransactionDetails;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.recyclerItems.transaction_chat;
import com.mediustechnologies.payemi.adapters.transction_chatAdapter;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiTransactionPageBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        getAllTransaction();

    }

    private void getAllTransaction() {

        String biller_id = "OU12LO000NATGJ";
        int id =2;
        String token ="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjQxNjYzMzE2LCJpYXQiOjE2NDE1NzY5MTYsImp0aSI6IjY0NjMxMmM2YzNmOTQ3ZDE4ZDRhMTFlMWZiOTIwZDIzIiwidXNlcl9pZCI6NH0.CeUQTysLO8oU0e9Djby3tbjgSkuuNOBAZCqF0dGpDAw";

        Call<List<TransactionDetails>> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().allTransaction(token,id,biller_id);

        call.enqueue(new Callback<List<TransactionDetails>>() {
            @Override
            public void onResponse(Call<List<TransactionDetails>> call, Response<List<TransactionDetails>> response) {

                System.out.println(response.body().toString());

                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                    List<TransactionDetails> data = response.body();
                    addata(data);

                }else {
                    Toast.makeText(context, "Response "+response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","Transaction Details response "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<List<TransactionDetails>> call, Throwable t) {

            }
        });


    }

    private void addata(List<TransactionDetails> data) {

        chatlist = new ArrayList<>();

        for(int i=0;i<data.size();i++){
            String date = data.get(i).getTransaction_datetime();

            if(data.get(i).getType().equals("transaction"))
                chatlist.add(new transaction_chat(data.get(i).getBiller_name(),"not in api",data.get(i).getAmount(),date,null));
            else {
                if(data.get(i).getIs_redeemed().equals("false")){
                    chatlist.add(new transaction_chat("Scratch Now", "Earn a reward!", "", null, null));
                }else{
                    chatlist.add(new transaction_chat(data.get(i).getAmount(), "You earned a reward!", "", null, null));
                }
            }
        }
        initrecyclerview();
//        chatlist.add(new transaction_chat(null, null, null, null, "15 Oct 6:24PM"));                                  //for date line
//        chatlist.add(new transaction_chat("Payment to HDFC Bank", "Car Loan", "₹ 14,288", "  Paid | 15 Oct", null));  // tansaction
//        chatlist.add(new transaction_chat("Scratch Now","Earn a reward!","",null,null));                              // scratch card
//        chatlist.add(new transaction_chat("Rs. 140","You earned a reward!","",null,null));                            // scratch card

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