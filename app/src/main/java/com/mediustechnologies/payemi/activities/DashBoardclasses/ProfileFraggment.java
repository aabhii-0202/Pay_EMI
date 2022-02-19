package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


        binding.datalayout.setVisibility(View.GONE);
        callapiforprofile();




        binding.profileupdate.setOnClickListener(view1 -> {

            binding.progress.setVisibility(View.VISIBLE);

            String name = binding.profilename.getText().toString();
            String mail = binding.profilemail.getText().toString();
            String username = binding.profileUsername.getText().toString();
            String address = binding.profileaddress.getText().toString();
            String imgurl = "";




            Call<ProfileInfoResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().updateProfileInfo(utils.access_token,utils.phone,name,mail,username,address,imgurl);

            call.enqueue(new Callback<ProfileInfoResponse>() {
                @Override
                public void onResponse(Call<ProfileInfoResponse> call, Response<ProfileInfoResponse> response) {

                    binding.progress.setVisibility(View.GONE);

                    if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                        if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                            try {
                                binding.profilephone.setText(utils.phone);
                                binding.profilename.setText(utils.name);
                                binding.profilemail.setText(response.body().getData().get(0).getEmail());
                                binding.profileUsername.setText(response.body().getData().get(0).getUser());
                                binding.profileaddress.setText(response.body().getData().get(0).getAddress());


                                SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);

                                preferences.edit().putString("name",response.body().getData().get(0).getUser_name()).apply();
                                preferences.edit().putString("profileid", response.body().getData().get(0).getId()).apply();
                                preferences.edit().putString("cutomerid", response.body().getData().get(0).getCustomer_id()).apply();

                                Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show();

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
                    else {
                        Log.e("tag",""+response.code());
                    }
                }

                @Override
                public void onFailure(Call<ProfileInfoResponse> call, Throwable t) {
                    binding.progress.setVisibility(View.GONE);
                    Log.e("tag",t.getMessage());
                    Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });




        });


    }

    private void callapiforprofile() {

        binding.progress.setVisibility(View.VISIBLE);

        Call<ProfileInfoResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().profileInfo(utils.access_token,utils.phone);

        call.enqueue(new Callback<ProfileInfoResponse>() {
            @Override
            public void onResponse(Call<ProfileInfoResponse> call, Response<ProfileInfoResponse> response) {

                binding.progress.setVisibility(View.GONE);

                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                        try {
                            binding.profilephone.setText(utils.phone);
                            binding.profilename.setText(utils.name);
                            binding.profilemail.setText(response.body().getData().get(0).getEmail());
                            binding.profileUsername.setText(response.body().getData().get(0).getUser_name());
                            binding.profileaddress.setText(response.body().getData().get(0).getAddress());
                        }
                        catch (Exception e){
                            e.printStackTrace();

                        }

                        binding.datalayout.setVisibility(View.VISIBLE);

                    } else {
                        try {
                            utils.errortoast(context, response.body().getMessage());
                        } catch (Exception e) {
                            Log.e("tag", e.toString());
                        }
                    }
                }
                else {
                    Log.e("tag",""+response.code());
                }
            }

            @Override
            public void onFailure(Call<ProfileInfoResponse> call, Throwable t) {
                binding.progress.setVisibility(View.GONE);
                Log.e("tag",t.getMessage());
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
