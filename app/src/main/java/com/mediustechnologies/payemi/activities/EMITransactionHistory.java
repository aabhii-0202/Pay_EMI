package com.mediustechnologies.payemi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.ApiResponse.AllTransactions;
import com.mediustechnologies.payemi.ApiResponse.RedeemScratchCard;
import com.mediustechnologies.payemi.ApiResponse.TransactionDetails;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.scratchCard.listener.ScratchListener;
import com.mediustechnologies.payemi.activities.scratchCard.ui.ScratchCardLayout;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.recyclerItems.transaction_chat;
import com.mediustechnologies.payemi.adapters.transction_chatAdapter;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiTransactionPageBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EMITransactionHistory extends BaseAppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private final Context context = this;
    private ActivityPayEmiTransactionPageBinding binding;
    private ArrayList<transaction_chat> chatlist;
    private final String TAG = "tag";
    private String logo,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiTransactionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getAllTransaction();

    }

    private void getAllTransaction() {

        String biller_id = getIntent().getStringExtra("biller_id");
//        biller_id = "OU12LO000NATGJ";
        String token = utils.access_token;

        Call<AllTransactions> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().allTransaction(token,utils.profileId,biller_id);

        call.enqueue(new Callback<AllTransactions>() {
            @Override
            public void onResponse(Call<AllTransactions> call, Response<AllTransactions> response) {

                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                    List<TransactionDetails> data = response.body().getData();
                    addata(data);

                }else {
                    Toast.makeText(context, "Response "+response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","Transaction Details response "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<AllTransactions> call, Throwable t) {

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
                    date = "  Paid | "+date;
                }else if(status.equals("failed")){
                    date = "  Failed | "+date;
                }
                else {
                    date = " Pending | "+date;
                }
            }else {
                status = "Pending";
                date = " Pending | "+date;
            }


            chatlist.add(new transaction_chat("Payment to "+item.getBiller_name(),"Not in api","₹ "+item.getAmount(),status,date,item.getIs_redeemed(),item.getType()));
        }
        initrecyclerview(data);

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

    private void initrecyclerview(List<TransactionDetails> data){
        RecyclerView recyclerView = binding.paymentRecyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        transction_chatAdapter adapter = new transction_chatAdapter(context, chatlist);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListner(new transction_chatAdapter.onItemClick() {
            @Override
            public void onItemClick(int postion) {

                TransactionDetails item = data.get(postion);

                if(item.getType().equals("transaction")) {
                    Intent i = new Intent(context, Transaction_Details.class);
                    i.putExtra("item", item);
                    i.putExtra("logl", logo);
                    startActivity(i);
                }else{
                    // code for cashback
                    if(item.getIs_redeemed().equals("false")){
                        Dialog d = new Dialog(context);
                        d.setContentView(R.layout.scratchcardlayout);
                        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView scratchamount = d.findViewById(R.id.scratch_cashback_amount);
                        scratchamount.setText("Rs. "+item.getAmount());
                        d.show();

                        com.mediustechnologies.payemi.activities.scratchCard.ui.ScratchCardLayout card = d.findViewById(R.id.scratchCard1);
                        card.setScratchListener(new ScratchListener() {
                            @Override
                            public void onScratchStarted() {

                            }

                            @Override
                            public void onScratchProgress(@NonNull ScratchCardLayout scratchCardLayout, int atLeastScratchedPercent) {
                                Log.d("tag", "onScratchProgress: "+atLeastScratchedPercent);
                                if(atLeastScratchedPercent>7){
                                    Log.d("tag","scratched");
                                    redeem(item.getBill_id());
                                }
                            }

                            @Override
                            public void onScratchComplete() {

                            }
                        });


                    }


                    else{

                    }
                }
            }
        });


    }

    private void redeem(String bill_id) {

        Log.d("tag"," --> "+utils.profileId+"--------"+bill_id);

        Call<RedeemScratchCard> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().redeemscratch(utils.access_token,utils.profileId,bill_id);

        call.enqueue(new Callback<RedeemScratchCard>() {
            @Override
            public void onResponse(Call<RedeemScratchCard> call, Response<RedeemScratchCard> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    if(response.body().getMessage().equals("Success")){
                        Log.d("tag","Redeemed cashback successful");
                        Toast.makeText(context,"Cashback Added Successfully",Toast.LENGTH_LONG).show();
                        getAllTransaction();

                    }

                }
                else if(response.code()==400){
                    System.out.println(response.message());
                }else{
                    Log.e("tag","Error ar redeem code "+response.code());
                }
            }

            @Override
            public void onFailure(Call<RedeemScratchCard> call, Throwable t) {

            }
        });
    }

    private void init(){

        name = getIntent().getStringExtra("name");
        logo = getIntent().getStringExtra("logo");

        Glide.with(binding.image).load(logo).into(binding.image);
        binding.bankname.setText(name);
        binding.backButton.setOnClickListener(view -> finish());



    }

    public void menu(View v){
        PopupMenu popup = new PopupMenu(context,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.transaction_history);
        popup.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.gethelp:
                Intent i = new Intent(context,act12complaintRegistration.class);
                startActivity(i);
                return  true;

            case R.id.refreshhistory:
                getAllTransaction();
                return  true;

            default:
                return false;
        }
    }


}