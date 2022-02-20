package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mediustechnologies.payemi.ApiResponse.AddMissingCategoryResponse;
import com.mediustechnologies.payemi.ApiResponse.HomepageResponse;
import com.mediustechnologies.payemi.DTO.HomepageDTO;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.EMITransactionHistory;
import com.mediustechnologies.payemi.activities.EmiCategories;
import com.mediustechnologies.payemi.activities.GetLoanDetails;
import com.mediustechnologies.payemi.adapters.emiListItemAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.AddMissingInfoBinding;
import com.mediustechnologies.payemi.databinding.DashboardFragmentBinding;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.recyclerItems.emiListItem;
import com.whiteelephant.monthpicker.MonthPickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardFragment extends Fragment {

    DashboardFragmentBinding binding;
    private List<emiListItem> emilist;
    private Context context ;
    private  List<HomepageDTO> data;
    private AlertDialog d;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DashboardFragmentBinding.inflate(getLayoutInflater());
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
        callapi();
    }

    private void setData() {
        emilist = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            String totalemi = data.get(i).getLoan_amount();
            String loanpaied = data.get(i).getLoan_paid();

            int progress;

            try{
                double total = Double.parseDouble(totalemi);
                double paied = Double.parseDouble(loanpaied);
                int p = (int) ((paied/total)*100);
                progress = p;
            }catch (Exception e){
                progress = -1;
            }

            String s = "";
            s = "EMI "+data.get(i).getEmi();
            s+= "\nBiller Name "+data.get(i).getBiller__billerName();
            s+= "\nLoan type "+data.get(i).getLoan_type();
            s+="\nLoan amount "+data.get(i).getLoan_amount();
            s+= "\nPaid "+data.get(i).getLoan_paid();
            s+= "\nAmount "+data.get(i).getAmount();
            s+= "\nProgress "+progress;

            Log.d("tag", "Emi List index: "+i+" data: "+s);



            emilist.add(new emiListItem(data.get(i).getEmi(),data.get(i).getBiller__billerName(),data.get(i).getLoan_type(),data.get(i).getLoan_paid(),data.get(i).getLoan_amount(),data.get(i).getBiller__logo_url(),progress));

        }


        initrecycler();
    }

    private void initrecycler() {
        RecyclerView emilistRecycler = binding.listofloan;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        emilistRecycler.setLayoutManager(linearLayoutManager);
        emiListItemAdapter adapter = new emiListItemAdapter(emilist);
        emilistRecycler.setAdapter(adapter);

        adapter.setOnButtonClickListner(pos -> {
            Intent i = new Intent(context, GetLoanDetails.class);
            i.putExtra("loan_id",data.get(pos).getId());
            i.putExtra("emi",data.get(pos).getEmi());
            i.putExtra("bankname",emilist.get(pos).getBank_Name());
            i.putExtra("billerName",data.get(pos).getBiller__billerName());
            i.putExtra("logo",data.get(pos).getBiller__logo_url());
            i.putExtra("data", data.get(pos));
            startActivity(i);
        });
        adapter.setOnItemClickListner(position -> {
            Intent i = new Intent(context, EMITransactionHistory.class);
            i.putExtra("name",emilist.get(position).getBank_Name());
            i.putExtra("id",data.get(position).getId());
            i.putExtra("biller_id",data.get(position).getBiller__billerId());

            startActivity(i);
        });


        adapter.setOnMissingClickLIstner(pos -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context,R.style.fullscreenalert);
            View view = getLayoutInflater().inflate(R.layout.add_missing_info,null);
            mBuilder.setView(view);

            d = mBuilder.create();
            TextView name = view.findViewById(R.id.name);
            name.setText(data.get(pos).getBiller__billerName()+" Loan Account Number "+data.get(pos).getLoan_acc_no());

            d.show();
            view.findViewById(R.id.cross).setOnClickListener(view1 -> d.cancel());
            AddMissingInfoBinding binding = AddMissingInfoBinding.bind(view);

            binding.month.setOnClickListener(view13 -> {
                Calendar today = Calendar.getInstance();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(context, (selectedMonth, selectedYear) -> {
                    selectedMonth+=1;
                    binding.month.setText(String.valueOf(selectedMonth));
                    binding.year.setText(String.valueOf(selectedYear));

                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
                builder.setMaxYear(3000).build().show();
            });

            binding.year.setOnClickListener(view12 -> {
                Calendar today = Calendar.getInstance();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(context, (selectedMonth, selectedYear) -> {
                    selectedMonth+=1;
                    binding.month.setText(String.valueOf(selectedMonth));
                    binding.year.setText(String.valueOf(selectedYear));

                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
                builder.setMaxYear(3000).build().show();
            });




            d.findViewById(R.id.update).setOnClickListener(view14 -> {

                String loanType = binding.loantype.getText().toString();
                String loanAmount = binding.loanamount.getText().toString();
                String emi = binding.emi.getText().toString();
                String month = binding.month.getText().toString();
                String year = binding.year.getText().toString();


                boolean call = true;
                if(loanType.trim().length()<1){
                    binding.loantype.setError("Please enter loan type.");
                    call = false;
                }
                if(loanAmount.trim().length()<1){
                    binding.loanamount.setError("Please enter loan amount.");
                    call = false;
                }
                if(emi.trim().length()<1){
                    binding.emi.setError("Please enter EMI.");
                    call = false;
                }
                if(month.trim().length()<1){
                    binding.month.setError("Please select month.");
                    call = false;
                }
                if(year.trim().length()<1){
                    binding.year.setError("Please select year.");
                    call = false;
                }

                if(call) fillmissingdata(data.get(pos).getLoan_acc_no(),loanType,loanAmount,emi,month,year);

            });


        });
    }

    private void fillmissingdata(String loan_acc_no, String loanType, String loanAmount, String emi, String month, String year) {

        Call<AddMissingCategoryResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().addMissingInfo(utils.access_token,loan_acc_no,loanType,loanAmount,emi,month,year);
        call.enqueue(new Callback<AddMissingCategoryResponse>() {
            @Override
            public void onResponse(Call<AddMissingCategoryResponse> call, Response<AddMissingCategoryResponse> response) {
                if(response.code()== utils.RESPONSE_SUCCESS && response.body()!=null){
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        Toast.makeText(context, "Data updated successfully.", Toast.LENGTH_SHORT).show();
                        Log.d("tag", "Missing info uploaded successfully.");
                    }
                    else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                        }
                    }

                }else {
                    Log.e("tag","Missing info api "+response.code());
                    Toast.makeText(context, "Unable to add missing data", Toast.LENGTH_SHORT).show();
                }
                d.dismiss();
                callapi();
            }


            @Override
            public void onFailure(Call<AddMissingCategoryResponse> call, Throwable t) {
                Log.e("tag","Missing info API "+t.toString());
//                Toast.makeText(context, "Unable to add missing data", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                d.dismiss();
                callapi();

            }
        });

    }

    private void callapi(){

        Call<HomepageResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().homepage(utils.access_token,utils.phone,utils.profileId );

        call.enqueue(new Callback<HomepageResponse>() {
            @Override
            public void onResponse(Call<HomepageResponse> call, Response<HomepageResponse> response) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        data = response.body().getData();
                        setData();
                        if(!data.isEmpty()){
                            String s = "";
                            for(int i=0;i<data.size();i++){
                                s+=(1+i)+" "+data.get(i).toString()+"\n";
                            }
                            Log.d("tag","EMI List \n"+s);
                        }
                        else{
                            Log.d("tag", "No saved data.");
                            startActivity(new Intent(context, EmiCategories.class));
                        }
                    } else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                        }
                    }
                }else {
                    Log.e("tag", "Home " + response.code());
                }
            }

            @Override
            public void onFailure(Call<HomepageResponse> call, Throwable t) {
                Toast.makeText(context, "Unable to fetch Loans", Toast.LENGTH_SHORT).show();
                Log.e("tag","API home: "+t.toString());
            }
        });
    }


}
