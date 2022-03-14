package com.mediustechnologies.payemi.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.ApiResponse.banklistResponse;
import com.mediustechnologies.payemi.ApiResponse.getAllBanks;
import com.mediustechnologies.payemi.ApiResponse.getBillerByBank;
import com.mediustechnologies.payemi.DTO.GetBillerByBankDTO;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.adapters.bankListAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.BankListFragmentBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankListFragment extends Fragment {

    private BankListFragmentBinding binding;
    private Context context;
    private banklistResponse banklist;
    private List<getAllBanks> filteredList;
    public bankListAdapter adapter;

    public static BankListFragment newInstance() {
        return new BankListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }



    // adding the layout with inflater
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bank_list_fragment, container, false);
        binding = BankListFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.progress.setVisibility(View.VISIBLE);
        binding.listOfBanks.setVisibility(View.GONE);
        String catagory = getArguments().getString("position");
        addItemsInRecyclerView(catagory);

    }


    private void addItemsInRecyclerView(String loan_category_id) {
        Call<banklistResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getAllBanks(
                utils.access_token, loan_category_id
        );

        call.enqueue(new Callback<banklistResponse>() {
            @Override
            public void onResponse(Call<banklistResponse> call, Response<banklistResponse> response) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.isSuccessful() && response.body().getData() != null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        banklist = response.body();
                        Log.d("tag", "Banklist setdata: " + banklist.toString());
                        initRecyclerView();
//                        searchbar();
                    } else {
                        try {
                            utils.errortoast(context, response.body().getMessage());
                        } catch (Exception e) {
                            Log.e("tag", e.toString());
                        }
                    }
                } else {
                    Log.d("tag", "Get BankList onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<banklistResponse> call, Throwable t) {

            }
        });
    }

    private void initRecyclerView() {

        RecyclerView rec = binding.listOfBanks;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rec.setLayoutManager(gridLayoutManager);
        adapter = new bankListAdapter(banklist.getData());
        rec.setAdapter(adapter);
        binding.progress.setVisibility(View.GONE);
        binding.listOfBanks.setVisibility(View.VISIBLE);

        adapter.setOnItemClickListner(new bankListAdapter.onItemClicked() {
            @Override
            public void onItemClick(int position) {
                String url = "";
                String name = "";

                if (filteredList != null && !filteredList.isEmpty()) {
                    url = filteredList.get(position).getBank_logo_url();
                    name = filteredList.get(position).getBank_name();
                }
                else {
                    url = banklist.getData().get(position).getBank_logo_url();
                    name = banklist.getData().get(position).getBank_name();
                }
                check(name, url);
            }
        });
    }

    private void check(String name, String url) {
        Call<getBillerByBank> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getBillerByBank(utils.access_token, name);

        call.enqueue(new Callback<getBillerByBank>() {
            @Override
            public void onResponse(Call<getBillerByBank> call, Response<getBillerByBank> response) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        List<GetBillerByBankDTO> bankSubList = response.body().getData();
                        if (bankSubList.size() == 1) {
                            Intent i = new Intent(context, AddLoanAccount.class);
                            i.putExtra("url", bankSubList.get(0).getLogo_url());
                            i.putExtra("biller_id", bankSubList.get(0).getBillerId());
                            i.putExtra("biller_name", bankSubList.get(0).getBillerName());
                            startActivity(i);
                        } else {
                            Intent i = new Intent(context, BillerList.class);
                            i.putExtra("name", name);
                            i.putExtra("imgurl", url);
                            startActivity(i);
                        }
                    } else {
                        try {
                            utils.errortoast(context, response.body().getMessage());
                        } catch (Exception e) {
                            Log.e("tag", e.toString());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<getBillerByBank> call, Throwable t) {
                Intent i = new Intent(context, BillerList.class);
                i.putExtra("name", name);
                i.putExtra("imgurl", url);
                startActivity(i);
            }
        });
    }

//    private void searchbar() {
//        binding.search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                filter(editable.toString());
//            }
//        });
//    }

    public void filter(String text) {
        filteredList =new ArrayList<>();
        for (getAllBanks item : banklist.getData()){
            if(item.getBank_name().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        System.out.println("Filtered List: "+filteredList.size());
        System.out.println("Bank List: "+banklist.getData().size());

        adapter.filterList(filteredList);
    }
}
