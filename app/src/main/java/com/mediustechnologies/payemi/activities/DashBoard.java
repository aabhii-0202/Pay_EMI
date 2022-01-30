package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mediustechnologies.payemi.ApiResponse.homePage;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.AddMissingInfoBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.recyclerItems.emiListItem;
import com.mediustechnologies.payemi.adapters.emiListItemAdapter;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiHomeBinding;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard extends BaseAppCompatActivity {
    private ActivityPayEmiHomeBinding binding;
    private List<emiListItem> emilist;
    private final Context context = this;
    private  List<homePage> data;
    private AlertDialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        callapi();



    }

    private void setData() {
        emilist = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            String totalemi = data.get(i).getAmount();
            String loanpaied = data.get(i).getLoan_paid();

            int progress;

            try{
                double total = Double.parseDouble(totalemi);
                double paied = Double.parseDouble(loanpaied);
                int p = (int) (total/paied);
                progress = p;
            }catch (Exception e){
                progress = -1;
            }

            String s = "";
            s = "EMI "+data.get(i).getEmi();
            s+= "\nBiller Name "+data.get(i).getBiller__billerName();
            s+= "\nLoan type "+data.get(i).getLoan_type();
            s+= "\nPaid "+data.get(i).getLoan_paid();
            s+= "\nAmount "+data.get(i).getAmount();
            s+= "\nProgress "+progress;

            Log.d("tag", "Emi List index: "+i+" data: "+s);



            emilist.add(new emiListItem(data.get(i).getEmi(),data.get(i).getBiller__billerName(),data.get(i).getLoan_type(),data.get(i).getLoan_paid(),data.get(i).getAmount(),data.get(i).getBiller__logo_url(),progress));

        }


        initrecycler();
    }

    private void initrecycler() {
        RecyclerView emilistRecycler = binding.loanrecyclerview;
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

            binding.month.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar today = Calendar.getInstance();
                        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(context, (selectedMonth, selectedYear) -> {
                            selectedMonth+=1;
                            binding.month.setText(selectedMonth+"");
                            binding.year.setText(selectedYear+"");

                        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
                        builder.setMaxYear(3000).build().show();
                }
            });

            binding.year.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar today = Calendar.getInstance();
                        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(context, (selectedMonth, selectedYear) -> {
                            selectedMonth+=1;
                            binding.month.setText(selectedMonth+"");
                            binding.year.setText(selectedYear+"");

                        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
                        builder.setMaxYear(3000).build().show();
                }
            });




            d.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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

                }
            });


        });
    }

    private AtomicReference<String> adddate(){

        AtomicReference<String> s = new AtomicReference<>("");

        Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(context, (selectedMonth, selectedYear) -> {

            s.set(selectedMonth + " " + selectedYear);



        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
        builder.setMaxYear(3000).build().show();

        return s;

    }

    private void fillmissingdata(String loan_acc_no, String loanType, String loanAmount, String emi, String month, String year) {

        Call<String> call = new RetrofitClient().getInstance(context,urlconstants.AuthURL).getApi().addMissingInfo(utils.access_token,loan_acc_no,loanType,loanAmount,emi,month,year);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()== utils.RESPONSE_SUCCESS ){
                    Toast.makeText(context, "Data updated successfully.", Toast.LENGTH_SHORT).show();
                    Log.d("tag","Missing info uploaded successfully.");

                }else {
                    Log.e("tag","Missing info api "+response.code());
                    Toast.makeText(context, "Unable to add missing data", Toast.LENGTH_SHORT).show();



                }
                d.dismiss();
                callapi();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("tag","Missing info API "+t.toString());
//                Toast.makeText(context, "Unable to add missing data", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                d.dismiss();
                callapi();
            }
        });

    }



    private void callapi(){

        Call<List<homePage>> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().homepage(utils.access_token,utils.phone,utils.profileId );

        call.enqueue(new Callback<List<homePage>>() {
            @Override
            public void onResponse(Call<List<homePage>> call, Response<List<homePage>> response) {

                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    data = response.body();



                    setData();
                    try {
                        Log.d("tag", "-->>" + data.get(0).toString());
                    }catch (Exception e){
                        Log.d("tag","New User");
                        startActivity(new Intent(context, EmiCategories.class));

                    }

                }else {
                    Log.e("tag","Home "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<homePage>> call, Throwable t) {
                Toast.makeText(context, "Unable to fetch Loans", Toast.LENGTH_SHORT).show();
                Log.e("tag","API home: "+t.toString());
            }
        });
    }

    private void init(){


        binding.addbutton.setOnClickListener(view -> startActivity(new Intent(context, EmiCategories.class)));
    }
}