package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.mediustechnologies.payemi.Models.bankListItem;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.adapters.bankListAdapter;
import com.mediustechnologies.payemi.databinding.ActivityBankListBinding;
import java.util.ArrayList;
import java.util.List;

public class act4BankList extends AppCompatActivity {

    private ActivityBankListBinding binding;
    private List<bankListItem> banklist;
    private GridLayoutManager gridLayoutManager ;
    private bankListAdapter adapter;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        addDummmyItemsInRecyclerView();
        initRecyclerView();



    }

    private void addDummmyItemsInRecyclerView() {
        banklist = new ArrayList<>();

        banklist.add(new bankListItem(R.drawable.b1,"Axis Bank"));
        banklist.add(new bankListItem(R.drawable.b2,"HDFC Bank"));
        banklist.add(new bankListItem(R.drawable.b3,"SBI"));
        banklist.add(new bankListItem(R.drawable.b4,"Kotak Bank"));
        banklist.add(new bankListItem(R.drawable.b5,"Bank of Baroda"));
        banklist.add(new bankListItem(R.drawable.b6,"City Bank"));
        banklist.add(new bankListItem(R.drawable.b7,"HSBC Bank"));
        banklist.add(new bankListItem(R.drawable.b8,"Edelweiss Bank"));
        banklist.add(new bankListItem(R.drawable.b9,"ICICI Bank"));




    }

    private void initRecyclerView() {
        RecyclerView bankRecyclerView = binding.listOfBanks;
        gridLayoutManager = new GridLayoutManager(context,3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bankRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new bankListAdapter(banklist);
        bankRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListner(new bankListAdapter.onItemClicked() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(context ,position +" posion item clicked", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void init(){

        binding.backButton.setOnClickListener(view -> finish());
        binding.bharatBillLogo.setOnClickListener(view -> {
            startActivity(new Intent(this, act5BankSubCategories.class));
        });




    }
}