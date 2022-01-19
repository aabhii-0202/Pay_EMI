package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mediustechnologies.payemi.ApiResponse.homePage;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.recyclerItems.emiListItem;
import com.mediustechnologies.payemi.adapters.emiListItemAdapter;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard extends AppCompatActivity {
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
            String loanpaied = data.get(i).getLoan_paid();

            int progress;
            try{
                double total = Double.parseDouble(totalemi);
                double paied = Double.parseDouble(loanpaied);
                int p = (int) (total/paied);
                progress = p;


            }catch (Exception e){
                progress = -1;
            }



            emilist.add(new emiListItem(data.get(i).getEmi(),data.get(i).getBiller__billerName(),data.get(i).getLoan_type(),data.get(i).getLoan_paid(),data.get(i).getAmount(),data.get(i).getBiller__logo_url(),progress));

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

        adapter.setOnButtonClickListner(pos -> {
            Intent i = new Intent(context, GetLoanDetails.class);
            i.putExtra("loan_id",data.get(pos).getId());
            i.putExtra("emi",data.get(pos).getEmi());
            i.putExtra("bankname",emilist.get(pos).getBank_Name());
            i.putExtra("billerName",data.get(pos).getBiller__billerName());
            i.putExtra("logo",data.get(pos).getBiller__logo_url());
            i.putExtra("data", data.get(pos));
            startActivity(i);
        });
        adapter.setOnItemClickListner(position -> {
            Intent i = new Intent(context, EMITransactionHistory.class);
            i.putExtra("name",emilist.get(position).getBank_Name());
            i.putExtra("id",data.get(position).getId());
            i.putExtra("biller_id",data.get(position).getBiller__billerId());

            startActivity(i);
        });
        adapter.setOnMissingClickLIstner(pos -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context,R.style.fullscreenalert);
            View view = getLayoutInflater().inflate(R.layout.add_missing_info,null);
            mBuilder.setView(view);

            AlertDialog d = mBuilder.create();
            TextView name = view.findViewById(R.id.name);
            name.setText(data.get(pos).getBiller__billerName()+" Loan Account Number "+data.get(pos).getLoan_acc_no());

            d.show();
            view.findViewById(R.id.cross).setOnClickListener(view1 -> d.cancel());


            EditText loantype = d.findViewById(R.id.loantype);
            EditText amount = d.findViewById(R.id.loanamount);
            EditText emi = d.findViewById(R.id.emi);
            EditText month = d.findViewById(R.id.month);
            EditText year = d.findViewById(R.id.year);

            d.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String loanType = loantype.getText().toString();
                    String Amount = amount.getText().toString();
                    String EMI = emi.getText().toString();




                }
            });


        });
    }

    private void callapi(){

        Call<List<homePage>> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().homepage(utils.access_token,utils.phone,utils.profileId );

        call.enqueue(new Callback<List<homePage>>() {
            @Override
            public void onResponse(Call<List<homePage>> call, Response<List<homePage>> response) {

                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    data = response.body();
                    setData();
                    try {
                        Log.d("tag", "-->>" + data.get(0).toString());
                    }catch (Exception e){
                        Log.d("tag","New User");
                        startActivity(new Intent(context, EmiCategories.class));

                    }

                }else {
                    Log.e("tag","Home "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<homePage>> call, Throwable t) {
                Toast.makeText(context, "Unable to fetch Loans", Toast.LENGTH_SHORT).show();
                Log.e("tag","API home: "+t.toString());
            }
        });
    }

    private void init(){
        binding.addbutton.setOnClickListener(view -> startActivity(new Intent(context, EmiCategories.class)));
    }
}