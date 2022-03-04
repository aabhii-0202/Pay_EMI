package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.activities.HelpSubcatagory;
import com.mediustechnologies.payemi.adapters.HelpCatagoryAdapter;
import com.mediustechnologies.payemi.adapters.HelpSubcatagoryAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.HelpFragmentBinding;
import com.mediustechnologies.payemi.ApiResponse.GetHelpCatagoryResponse;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.ApiResponse.HelpSubCatagoryResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpFragment extends Fragment {

    private HelpFragmentBinding binding;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HelpFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callApiforCatagories();
    }

    private void callApiforCatagories() {

        Call<GetHelpCatagoryResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getHelpCatagory(utils.access_token);

        call.enqueue(new Callback<GetHelpCatagoryResponse>() {
            @Override
            public void onResponse(Call<GetHelpCatagoryResponse> call, Response<GetHelpCatagoryResponse> response) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        initCatagoryRecyclerView(response.body());
                        initSubCatRec(response.body().getSub_category());
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
            public void onFailure(Call<GetHelpCatagoryResponse> call, Throwable t) {
                Toast.makeText(context,"Error fetching help catagories",Toast.LENGTH_LONG);
            }
        });
    }


    private void initCatagoryRecyclerView(GetHelpCatagoryResponse response){

        List<String> catlist = response.getCategory();
        RecyclerView catagories = binding.helpcat;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        HelpCatagoryAdapter helpCatagoryAdapter = new HelpCatagoryAdapter(catlist);
        catagories.setLayoutManager(linearLayoutManager);
        catagories.setAdapter(helpCatagoryAdapter);

        helpCatagoryAdapter.setOnItemClickListner(position -> {
//            callApiForSubcatagoryList(catlist.get(position));
        });
    }

    private void callApiForSubcatagoryList(String title) {

        Pair<String,String> catagory = new Pair<>("category","Payment");
        Call<HelpSubCatagoryResponse> call = new RetrofitClient().getInstance(context,urlconstants.AuthURL).getApi().getHelpSubCategory(utils.access_token,catagory);

        call.enqueue(new Callback<HelpSubCatagoryResponse>() {
            @Override
            public void onResponse(Call<HelpSubCatagoryResponse> call, Response<HelpSubCatagoryResponse> response) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
//                                initSubCatRec(response.body().getSub_category());
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
            public void onFailure(Call<HelpSubCatagoryResponse> call, Throwable t) {
                Log.e("tag","getHelpSubCategory api "+t.getMessage());
            }
        });



    }

    private void initSubCatRec(List<String> list) {



        RecyclerView subcat = binding.helprec;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        HelpSubcatagoryAdapter subcatadapter = new HelpSubcatagoryAdapter(list);
        subcat.setLayoutManager(linearLayoutManager);
        subcat.setAdapter(subcatadapter);

        subcatadapter.setOnItemClick(position -> {
            Intent i = new Intent(context, HelpSubcatagory.class);
            i.putExtra("body",list.get(position));
            startActivity(i);
        });




    }

}
