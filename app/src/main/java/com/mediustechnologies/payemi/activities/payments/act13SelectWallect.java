package com.mediustechnologies.payemi.activities.payments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.adapters.WalletAdapter;
import com.mediustechnologies.payemi.adapters.bankListAdapter;
import com.mediustechnologies.payemi.databinding.ActivitySelectWallectBinding;
import com.mediustechnologies.payemi.recyclerItems.walletItem;

import java.util.ArrayList;
import java.util.List;

public class act13SelectWallect extends AppCompatActivity {

    private ActivitySelectWallectBinding binding;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectWallectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
        initdata();
    }

    private void initdata() {
        List<walletItem> walletItem = new ArrayList<>();
        walletItem.add(new walletItem(R.drawable.paytmwallet,"Paytm"));
        walletItem.add(new walletItem(R.drawable.amazonpay,"Amazon Pay"));
        walletItem.add(new walletItem(R.drawable.mobiwik,"Mobiwik"));
        walletItem.add(new walletItem(R.drawable.phonepewallet,"Phone Pe"));

        RecyclerView recyclerView = binding.walletlist;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        WalletAdapter adapter = new WalletAdapter(walletItem);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListner(new WalletAdapter.onItemClicked(){

            @Override
            public void onItemClick(int position) {
                Toast.makeText(context, "Link "+walletItem.get(position).getWalletname()+" wallet.", Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void init(){
        binding.back.setOnClickListener(view -> finish());

    }
}