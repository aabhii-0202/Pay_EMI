package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mediustechnologies.payemi.ApiResponse.RegisterComplaintResponse;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ComplaintRegistrationFrragmentBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintRegFrag extends Fragment {

    private ComplaintRegistrationFrragmentBinding binding;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ComplaintRegistrationFrragmentBinding.inflate(getLayoutInflater());
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

        init();
    }

    private void callApi(String dispostion,String description, String type, String trans_id){

        Call<RegisterComplaintResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().registerComplaint(
                utils.access_token,dispostion,description,type,trans_id);

        call.enqueue(new Callback<RegisterComplaintResponse>() {
            @Override
            public void onResponse(Call<RegisterComplaintResponse> call, Response<RegisterComplaintResponse> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                        Dialog d = new Dialog(context);
                        d.setContentView(R.layout.complaint_registered_popup);
                        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView compid = d.findViewById(R.id.compid);
                        TextView compassighed = d.findViewById(R.id.compassighed);

                        try{
                            compid.setText(response.body().getData().getComplaint_id());
                            compassighed.setText(response.body().getData().getComplaint_assigned_to());
                            d.show();
                        }catch (Exception e){
                            e.printStackTrace();

                        }

                    }else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                        }
                    }
                }else {
                    Log.e("tag",""+response.code());
                }
            }

            @Override
            public void onFailure(Call<RegisterComplaintResponse> call, Throwable t) {
                Log.e("tag",t.getMessage());
            }
        });



    }

    private void init(){


        String[] itemlist = getResources().getStringArray(R.array.dispoistion);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(itemlist));








        binding.register.setOnClickListener(view -> {
            String complaint_dispostion,complaint_description,complaint_type,transaction_id;

            transaction_id = binding.refid.getText().toString();
            complaint_description = binding.descrip.getText().toString();
            complaint_type = "Transaction";
            complaint_dispostion = "Transaction Successful, account not updated";

            if(transaction_id.length()<1){
                binding.refid.setError("Enter valid Id");
            }else if(complaint_description.length()<1) {
                binding.descrip.setError("Enter description.");
            }else if(complaint_dispostion.length()<1){
                binding.dispositon.setError("Enter valid Id");
            }else{
                callApi(complaint_dispostion,complaint_description,complaint_type,transaction_id);
            }
        });
    }
}
