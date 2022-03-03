package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.activities.HelpSubcatagory;
import com.mediustechnologies.payemi.adapters.HelpCatagoryAdapter;
import com.mediustechnologies.payemi.adapters.HelpSubcatagoryAdapter;
import com.mediustechnologies.payemi.databinding.HelpFragmentBinding;
import com.mediustechnologies.payemi.databinding.ProfileFragmentBinding;
import com.mediustechnologies.payemi.recyclerItems.HelpCatagoryList;
import com.mediustechnologies.payemi.recyclerItems.HelpSubCatagoryItem;

import java.util.ArrayList;
import java.util.List;

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

        initCatagoryRecyclerView();
        initSubCatRec();
    }


    private void initCatagoryRecyclerView(){

        List<HelpCatagoryList> catlist = new ArrayList<>();
        catlist.add(new HelpCatagoryList("Payment"));
        catlist.add(new HelpCatagoryList("Coupons"));
        catlist.add(new HelpCatagoryList("Reservation"));
        catlist.add(new HelpCatagoryList("Cancellation"));
        catlist.add(new HelpCatagoryList("Refund"));
        catlist.add(new HelpCatagoryList("Cashback"));

        RecyclerView catagories = binding.helpcat;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        HelpCatagoryAdapter helpCatagoryAdapter = new HelpCatagoryAdapter(catlist);
        catagories.setLayoutManager(linearLayoutManager);
        catagories.setAdapter(helpCatagoryAdapter);

        helpCatagoryAdapter.setOnItemClickListner(position -> {
            callApiForSubcatagoryList(catlist.get(position).getTitle());
            System.out.println(position);
        });
    }

    private void callApiForSubcatagoryList(String title) {
        initSubCatRec();
    }

    private void initSubCatRec() {

        List<HelpSubCatagoryItem> subcatlist = new ArrayList<>();
        subcatlist.add(new HelpSubCatagoryItem("sahfaihsfdha"));
        subcatlist.add(new HelpSubCatagoryItem("sahfaihsfdha"));
        subcatlist.add(new HelpSubCatagoryItem("sahfaihsfdha"));
        subcatlist.add(new HelpSubCatagoryItem("sahfaihsfdha"));
        subcatlist.add(new HelpSubCatagoryItem("sahfaihsfdha"));
        subcatlist.add(new HelpSubCatagoryItem("sahfaihsfdha"));

        RecyclerView subcat = binding.helprec;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        HelpSubcatagoryAdapter subcatadapter = new HelpSubcatagoryAdapter(subcatlist);
        subcat.setLayoutManager(linearLayoutManager);
        subcat.setAdapter(subcatadapter);

        subcatadapter.setOnItemClick(position -> {
            Intent i = new Intent(context, HelpSubcatagory.class);
            i.putExtra("body",subcatlist.get(position).getTitle());
            startActivity(i);
        });




    }

}
