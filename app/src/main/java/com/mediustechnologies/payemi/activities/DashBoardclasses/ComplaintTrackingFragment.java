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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mediustechnologies.payemi.ApiResponse.RegisterComplaintResponse;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ComplaintRegistrationFrragmentBinding;
import com.mediustechnologies.payemi.databinding.ComplaintTrackingFragmentBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintTrackingFragment extends Fragment {

    ComplaintTrackingFragmentBinding binding;
    private Context context ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ComplaintTrackingFragmentBinding.inflate(getLayoutInflater());
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

        binding.checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comp_id = binding.complaintid.getText().toString();
                String type = "Transaction";

                if(comp_id==null||comp_id.trim().length()<1){
                    binding.complaintid.setError("Please Enter Complaint id");
                }else callapi(type,comp_id);
            }
        });
    }

    private void callapi(String comp_type, String comp_id){

        Call<RegisterComplaintResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().trackComplaint(utils.access_token,comp_id,comp_type);
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
                        ConstraintLayout c = d.findViewById(R.id.statuslayout);
                        c.setVisibility(View.VISIBLE);
                        TextView status = d.findViewById(R.id.compstatus);

                        try{
                            compid.setText(response.body().getData().getComplaint_id());
                            compassighed.setText(response.body().getData().getComplaint_assigned_to());
                            status.setText(response.body().getBbps_response().getComplaintStatus());
                            d.show();
                        }catch (Exception e){
                            e.printStackTrace();

                        }
                        binding.complaintid.setText("");
                    }else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                            Log.e("tag",response.body().getMessage());
                            binding.errormess.setText(response.body().getMessage());
                            binding.errormess.setVisibility(View.VISIBLE);
                            binding.parentdata.setVisibility(View.GONE);
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                            binding.errormess.setText("Error Occured ");
                            binding.errormess.setVisibility(View.VISIBLE);
                            binding.parentdata.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterComplaintResponse> call, Throwable t) {
                Toast.makeText(context, "Error Fetching Complaint", Toast.LENGTH_SHORT).show();
                Log.e("tag",t.getMessage());
                binding.errormess.setText(t.getMessage());
                binding.errormess.setVisibility(View.VISIBLE);
                binding.parentdata.setVisibility(View.GONE);
            }
        });
    }
}
