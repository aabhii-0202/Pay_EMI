package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.ApiResponse.BaseApiResponse;
import com.mediustechnologies.payemi.ApiResponse.ShowNotificationResponse;
import com.mediustechnologies.payemi.DTO.ShowNotificationDTO;
import com.mediustechnologies.payemi.adapters.ShowNotificationAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.NotificationFragmentBinding;
import com.mediustechnologies.payemi.databinding.ProfileFragmentBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment  {

    private NotificationFragmentBinding binding;
    private Context context;

    public void clear(){
        binding.txt.setText("Notifications Cleared");
        binding.txt.setVisibility(View.VISIBLE);
        binding.parentlayout.setVisibility(View.GONE);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = NotificationFragmentBinding.inflate(getLayoutInflater());
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

        callApi();
    }


    private void callApi() {

        Call<ShowNotificationResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().showNotificatoin(utils.access_token, utils.phone);

        call.enqueue(new Callback<ShowNotificationResponse>() {
            @Override
            public void onResponse(Call<ShowNotificationResponse> call, Response<ShowNotificationResponse> response) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        if (!response.body().getData().isEmpty()) {
                            setData(response.body().getData());
                            binding.txt.setVisibility(View.GONE);


                        } else {
                            binding.parentlayout.setVisibility(View.GONE);
                            binding.txt.setVisibility(View.VISIBLE);
                            binding.txt.setText("No notification for now");
                        }
                    } else {
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
            public void onFailure(Call<ShowNotificationResponse> call, Throwable t) {

            }
        });


    }

    private void seen() {

        Call<BaseApiResponse> call = new RetrofitClient().getInstance(context,urlconstants.AuthURL).getApi().seenNotification(utils.access_token,utils.ids);
        call.enqueue(new Callback<BaseApiResponse>() {
            @Override
            public void onResponse(Call<BaseApiResponse> call, Response<BaseApiResponse> response) { }

            @Override
            public void onFailure(Call<BaseApiResponse> call, Throwable t) { }
        });
    }

    private void setData(List<ShowNotificationDTO> data) {

        List<String> list = new ArrayList<>();
 
        for (int i = 0; i < data.size(); i++) {
            try {
                list.add(data.get(i).getId());

            } catch (Exception e) {
                continue;
            }
        }

        try {
            HashMap<String, List<String>> ids = new HashMap<>();
            ids.put("notification_id", list);
            utils.ids = ids;
            seen();
        } catch (Exception e) {
        }


        ShowNotificationAdapter adapter = new ShowNotificationAdapter(data);
        RecyclerView recyclerView = binding.notificationRec;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnButtonClickListner(new ShowNotificationAdapter.onButtonClickeListner() {
            @Override
            public void onButtonClick(int pos) {
                String type = data.get(pos).getNotification_type();
                if (type != null) {
                    type = type.toLowerCase();
                    if (type.contains("transaction")) {
                        Toast.makeText(context, "Transaction Button Clicked", Toast.LENGTH_SHORT).show();
                    } else if (type.contains("cash")) {
                        Toast.makeText(context, "Redeem Button Clicked", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                }
            }
        });


    }


}
