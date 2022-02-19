package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mediustechnologies.payemi.ApiResponse.ProfileInfoResponse;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ProfileFragmentBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFraggment extends Fragment {

    ProfileFragmentBinding binding;
    private Context context ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(getLayoutInflater());
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


        callapiforprofile();




        binding.profileupdate.setOnClickListener(view1 -> {






        });


    }

    private void callapiforprofile() {

        Call<ProfileInfoResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().profileInfo(utils.access_token,utils.phone);

        call.enqueue(new Callback<ProfileInfoResponse>() {
            @Override
            public void onResponse(Call<ProfileInfoResponse> call, Response<ProfileInfoResponse> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                        try {
                            binding.profilephone.setText(utils.phone);
                            binding.profilename.setText(utils.name);
                            binding.profilemail.setText(response.body().getData().get(0).getEmail());
                            binding.profileUsername.setText(response.body().getData().get(0).getUser());
                            binding.profileaddress.setText(response.body().getData().get(0).getAddress());
                        }
                        catch (Exception e){
                            e.printStackTrace();

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
            public void onFailure(Call<ProfileInfoResponse> call, Throwable t) {

            }
        });

    }
}
