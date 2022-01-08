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
        String token = utils.access_token;
//        token ="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjQxNjYzMzE2LCJpYXQiOjE2NDE1NzY5MTYsImp0aSI6IjY0NjMxMmM2YzNmOTQ3ZDE4ZDRhMTFlMWZiOTIwZDIzIiwidXNlcl9pZCI6NH0.CeUQTysLO8oU0e9Djby3tbjgSkuuNOBAZCqF0dGpDAw";

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
            date = formatdate(date);

            TransactionDetails item = data.get(i);
            String status = item.getBbps_transaction_status();
            if(status!=null){
                if(status.equals("Successful")){
                    date = "  Failed | "+date;
                }else date = "  Paid | "+date;
            }


            chatlist.add(new transaction_chat("Payment to "+item.getBiller_name(),"Not in api","₹ "+item.getAmount(),status,date,item.getIs_redeemed(),item.getType()));
        }
        initrecyclerview();

    }

    private String formatdate(String date) {
        String ans ="";
        String day = date.substring(8,10);
        ans+=day+" ";
        String month = date.substring(5,7);
        String months[] = {
          "Jan","Feb","Mar","Apr","May","June","July","Aug","Sep",
          "Oct","Nov","Dec"
        };

        int m = Integer.parseInt(month);
        try {
            ans += months[m - 1];
        }catch (Exception e){
            ans = "Error finding month";
        }
        return ans;
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