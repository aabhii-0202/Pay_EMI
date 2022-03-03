package com.mediustechnologies.payemi.activities;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.adapters.helpActivityAdapter;
import com.mediustechnologies.payemi.databinding.ActivityHelpSubcatagoryBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.recyclerItems.helpActivityRecyclerItem;

import java.util.ArrayList;
import java.util.List;

public class HelpSubcatagory extends BaseAppCompatActivity {

    private final Context context = this;
    private ActivityHelpSubcatagoryBinding binding;
    private String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpSubcatagoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
       body = getIntent().getStringExtra("body");
       callApi(0);
    }

    private void callApi(int ans){

        String answer = "We value our customer’s time and hence moved away from a single customer care number to a comprehensive chat-based support system for quick and easy resolution. You no longer have to go through the maze of an IVRS call support. Just search for your issue in the help section on this page and initiate a chat You can also email us your issue on support@swiggy.in. Note: We value your privacy and your information is safe with us. Please do not reveal any personal information, bank account number, you for these details. Please do not reveal these details to fraudsters and do not entertain phishing calls or emails";
        List<helpActivityRecyclerItem> list = new ArrayList<>();

        for(int i=0;i<10;i++){
            if(i==ans){
                list.add(new helpActivityRecyclerItem("Question "+i, answer));
            }
            else {
                list.add(new helpActivityRecyclerItem("Question "+i, ""));
            }
        }
        initrec(list);
    }

    private void initrec(List<helpActivityRecyclerItem> list){



        RecyclerView recyclerView = binding.rec;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        helpActivityAdapter helpActivityAdapter = new helpActivityAdapter(list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(helpActivityAdapter);

        helpActivityAdapter.SetOnQuestionClicked(new helpActivityAdapter.onQuestionAskd() {
            @Override
            public void OnQuestion(int position) {
                callApi(position);
            }
        });




    }

    private void init(){
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}