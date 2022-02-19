package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.afsal.rangedialog.PickerDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.mediustechnologies.payemi.ApiResponse.TransactionSearchResponse;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.TransactionSearchFragmentBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransactionSearchFragment extends Fragment {

    TransactionSearchFragmentBinding binding;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TransactionSearchFragmentBinding.inflate(getLayoutInflater());
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

    private void init() {


        binding.layoutfrom.setOnClickListener(View -> {
            datePicker();
        });

        binding.layoutto.setOnClickListener(view -> {
            datePicker();
        });


        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = binding.mobilenumber.getText().toString();
                String refid = binding.transactionId.getText().toString();
                String dateto = binding.totext.getText().toString();
                String datefrom = binding.fromtext.getText().toString();


                datefrom ="2020-01-15";
                dateto = "2022-02-10";


                if(refid!=null&&refid.length()>1){
                    callapiforrefid(refid);
                }
                else{
                    if(phone==null||phone.length()<10){
                        binding.mobilenumber.setError("Please enter a valid 10 digit phone number.");
                    }
                    else if(datefrom == null||datefrom.length()<2){
                        binding.totext.setError("Please Select Date Range");
                        binding.fromtext.setError("Please Select Date Range");
                    }
                    else{
                        callapiforphone(phone,datefrom,dateto);
                    }
                }

            }
        });

    }

    private void showTransactionDetails(TransactionSearchResponse response){



        if(response.getData().isEmpty()) {
            Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Dialog d = new Dialog(context);
        d.setContentView(R.layout.transcation_details_popup);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        TextView agentid,amount,billername,transactiondate,txnrefid,status;

        try {
            agentid = d.findViewById(R.id.agentid);
            amount = d.findViewById(R.id.popamount);
            billername = d.findViewById(R.id.popbillername);
            transactiondate = d.findViewById(R.id.poptransactiondate);
            txnrefid = d.findViewById(R.id.poprefid);
            status = d.findViewById(R.id.popstatus);

            agentid.setText(response.getData().get(0).getAgentId());
            billername.setText(response.getData().get(0).getBiller_name());
            amount.setText(response.getData().get(0).getAmount());
            transactiondate.setText(response.getData().get(0).getTransaction_date());
            txnrefid.setText(response.getData().get(0).getTransaction_ref_id());
            status.setText(response.getData().get(0).getTransation_status());

            d.setOnDismissListener(dialogInterface -> {
                binding.mobilenumber.setText("");
                binding.transactionId.setText("");
                binding.fromtext.setText("");
                binding.totext.setText("");
            });
            d.findViewById(R.id.popcancelbtn).setOnClickListener(view -> d.dismiss());

            d.show();
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(context, "Exception "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void callapiforrefid(String refid) {

        Call<TransactionSearchResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().transactionSearchWithRefId(utils.access_token,refid);
        call.enqueue(new Callback<TransactionSearchResponse>() {
            @Override
            public void onResponse(Call<TransactionSearchResponse> call, Response<TransactionSearchResponse> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                        showTransactionDetails(response.body());

                    }else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                        }
                    }
                }else {
                    Toast.makeText(context, "Failed "+response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("tag",""+response.code());
                }
            }

            @Override
            public void onFailure(Call<TransactionSearchResponse> call, Throwable t) {
                Toast.makeText(context, "Failed "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("tag",""+t.getMessage());
            }
        });
    }

    private void callapiforphone(String phone, String datefrom, String dateto) {

        Call<TransactionSearchResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().transactionSearchwithMobile(utils.access_token,phone,datefrom,dateto);

        call.enqueue(new Callback<TransactionSearchResponse>() {
            @Override
            public void onResponse(Call<TransactionSearchResponse> call, Response<TransactionSearchResponse> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {

                        showTransactionDetails(response.body());

                    }else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                        }
                    }
                }
                else {
                    Toast.makeText(context, "Failed "+response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("tag",""+response.code());
                }
            }

            @Override
            public void onFailure(Call<TransactionSearchResponse> call, Throwable t) {
                Toast.makeText(context, "Failed "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("tag",""+t.getMessage());
            }
        });

    }

    private void datePicker() {

        PickerDialog dialog=new PickerDialog(context);
        dialog.showPicker();

        dialog.setRangeSelected(new PickerDialog.OnRangeSelect() {
            @Override
            public void OnSelect(Date StartDate, Date EndDate) {

                //Do Something here
                Calendar cal = Calendar.getInstance();

                cal.setTime(StartDate);
                int date = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);

                String m = ++month+"";
                if (month<10) m = "0"+m;
                String d = date+"";
                if(date<10) d = "0"+d;
                String from = year+"-"+m+"-"+d;
                binding.fromtext.setText(from);

                cal.setTime(EndDate);
                date = cal.get(Calendar.DAY_OF_MONTH);
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);

                m = ++month+"";
                if (month<10) m = "0"+m;
                d = date+"";
                if(date<10) d = "0"+d;
                String to = year+"-"+m+"-"+d;
                binding.totext.setText(to);

            }
        });



    }

    private int getMonth(String month){
        String[] cal = {
          "Jan", "Feb", "Mar" , "Apr" , "May", "Jun", "Jul" , "Aug", "Sep", "Oct", "Nov", "Dec"
        };

        int ans = -1;
        for(int i=0;i<cal.length;i++){
            if(month.equalsIgnoreCase(cal[i])){
                ans = (i+1);
                break;
            }
        }

        return ans;
    }


}
