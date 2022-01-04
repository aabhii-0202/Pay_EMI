package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mediustechnologies.payemi.Models.homePage;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.recyclerItems.emiListItem;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.adapters.emiListItemAdapter;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act33payEMI_home extends AppCompatActivity {
    private ActivityPayEmiHomeBinding binding;
    private List<emiListItem> emilist;
    private final Context context = this;
    private  List<homePage> data;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        callapi();

    }

    private void setData() {
        emilist = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            String totalemi = data.get(i).getAmount();
            String remaining = data.get(i).getDue_amount();
            String paid = "NUll";
            if(totalemi!=null&&remaining!=null)
            paid= Integer.toString(Integer.parseInt(totalemi)-Integer.parseInt(remaining));

            emilist.add(new emiListItem(data.get(i).getEmi(),data.get(i).getBiller__billerName(),"loan name",paid,data.get(i).getAmount(),data.get(i).getBiller__logo_url()));


        }


        initrecycler();
    }

    private void initrecycler() {
        RecyclerView emilistRecycler = binding.loanrecyclerview;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        emilistRecycler.setLayoutManager(linearLayoutManager);
        emiListItemAdapter adapter = new emiListItemAdapter(emilist);
        emilistRecycler.setAdapter(adapter);

        adapter.setOnButtonClickListner(new emiListItemAdapter.onButtonClickeListner() {
            @Override
            public void onButtonClick(int pos) {
                Intent i = new Intent(context,act34pay_EMI_Details.class);
                i.putExtra("bankname",emilist.get(pos).getBank_Name());
                startActivity(i);
            }
        });
        adapter.setOnItemClickListner(new emiListItemAdapter.onItemClicked() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(context,act39payEMI_transaction_page.class);
                i.putExtra("name",emilist.get(position).getBank_Name());
                startActivity(i);
            }
        });
    }

    private void callapi(){

        Call<List<homePage>> call = RetrofitClient.getInstance(urlconstants.AuthURL).getApi().homepage(utils.access_token,utils.phone);

        call.enqueue(new Callback<List<homePage>>() {
            @Override
            public void onResponse(Call<List<homePage>> call, Response<List<homePage>> response) {
                if(response.code()==200&&response.body()!=null){
                    data = response.body();
                    setData();

                }
            }

            @Override
            public void onFailure(Call<List<homePage>> call, Throwable t) {
                Toast.makeText(context, "Unable to fetch Loans", Toast.LENGTH_SHORT).show();
                Log.d("tag","API home: "+t.toString());
            }
        });
    }

    private void init(){
        binding.addbutton.setOnClickListener(view -> startActivity(new Intent(context, act4BankList.class)));
    }
}