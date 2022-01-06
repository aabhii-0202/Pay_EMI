package com.mediustechnologies.payemi.activities.payments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.LinearLayout;

import com.mediustechnologies.payemi.adapters.NetBankingListAdapter;
import com.mediustechnologies.payemi.databinding.ActivityNetBankingBinding;
import com.mediustechnologies.payemi.recyclerItems.NetBankingListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class act14NetBanking extends AppCompatActivity {

    private ActivityNetBankingBinding binding;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNetBankingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        recyclerview();





    }

    private void recyclerview(){

        List<NetBankingListItem> banklist = new ArrayList<>();
        banklist.add(new NetBankingListItem("Axis Bank"));
        banklist.add(new NetBankingListItem("Bandhan Bank"));
        banklist.add(new NetBankingListItem("Catholic Syrian Bank"));
        banklist.add(new NetBankingListItem("City Union Bank"));
        banklist.add(new NetBankingListItem("DCB Bank"));
        banklist.add(new NetBankingListItem("Dhanlaxmi Bank"));
        banklist.add(new NetBankingListItem("Federal Bank"));
        banklist.add(new NetBankingListItem("HDFC Bank"));
        banklist.add(new NetBankingListItem("ICICI Bank"));
        banklist.add(new NetBankingListItem("IDBI Bank"));
        banklist.add(new NetBankingListItem("IDFC First Bank"));
        banklist.add(new NetBankingListItem("IndusInd Bank"));
        banklist.add(new NetBankingListItem("State Bank"));

        RecyclerView recyclerView = binding.netbankinglist;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context   );
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        NetBankingListAdapter adapter = new NetBankingListAdapter(banklist);
        recyclerView.setAdapter(adapter);

        initsearchbar(adapter);

    }

    private void initsearchbar(NetBankingListAdapter adapter) {

        binding.searchbar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = binding.searchbar.getText().toString();
                adapter.getFilter().filter(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    private void init(){
        binding.back.setOnClickListener(view -> finish());

    }
}