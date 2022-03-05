package com.mediustechnologies.payemi.activities;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.DTO.GetHelpQuestionAnswerDTO;
import com.mediustechnologies.payemi.adapters.helpActivityAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityHelpSubcatagoryBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.ApiResponse.GetHelpQuestionAnswer;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpSubcatagory extends BaseAppCompatActivity {

    private final Context context = this;
    private ActivityHelpSubcatagoryBinding binding;
    private String body;
    private List<GetHelpQuestionAnswerDTO> list;
    private helpActivityAdapter helpActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpSubcatagoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        body = getIntent().getStringExtra("body");
        callApi(body);
    }

    private void callApi(String body) {

        HashMap<String,String> sub_category = new HashMap<>();
        sub_category.put("sub_category",body);
        Call<GetHelpQuestionAnswer> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().GetHelpQuestionAnswer(utils.access_token,sub_category);

        call.enqueue(new Callback<GetHelpQuestionAnswer>() {
            @Override
            public void onResponse(Call<GetHelpQuestionAnswer> call, Response<GetHelpQuestionAnswer> response) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        list= response.body().getData();
                        initrec();
                    }
                    else {
                        try {
                            utils.errortoast(context, response.body().getMessage());
                        } catch (Exception e) {
                            Log.e("tag", e.toString());
                        }
                    }
                } else {
                    Toast.makeText(context, "Failed " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("tag", "" + response.code());
                }
            }

            @Override
            public void onFailure(Call<GetHelpQuestionAnswer> call, Throwable t) {

            }
        });
    }

    private void initrec() {

        RecyclerView recyclerView = binding.rec;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        helpActivityAdapter = new helpActivityAdapter(list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(helpActivityAdapter);
        helpActivityAdapter.SetOnQuestionClicked(position -> recyclerView.getLayoutManager().scrollToPosition(position));

        initsearchbar();

    }

    private void initsearchbar() {

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                    String text = editable.toString();
                    ArrayList<GetHelpQuestionAnswerDTO> filteredlist= new ArrayList<>();

                    for(GetHelpQuestionAnswerDTO item : list){
                        if(item.getQuestion().toLowerCase().contains(text.toLowerCase())){
                            filteredlist.add(item);
                        }
                    }
                helpActivityAdapter.filterlist(filteredlist);
            }
        });


    }

    private void init() {
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}