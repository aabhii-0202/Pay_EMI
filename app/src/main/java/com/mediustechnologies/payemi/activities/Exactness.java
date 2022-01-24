package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.ApiResponse.homePage;
import com.mediustechnologies.payemi.activities.payments.SelectPaymentMethod;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiBinding;


public class Exactness extends AppCompatActivity  {

    private ActivityPayEmiBinding binding;
    private final Context context = this;
    private homePage data;
    private String billerName,bill_id,profile_id,url;
    private String Exactness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();

    }

    private void init(){


        String exactness = null;


        try {
            data = getIntent().getParcelableExtra("data");
            url = data.getBiller__logo_url();
            exactness = data.getBiller__billerPaymentExactness();
        }catch (Exception e){
            url = getIntent().getStringExtra("logo");
            exactness = getIntent().getStringExtra("exact");
        }

        String customer = getIntent().getStringExtra("customer");
        String amount = getIntent().getStringExtra("amount");
        billerName = getIntent().getStringExtra("billerName");

        try{
//            Log.d("tag","Exactness : "+exactness);
            if(exactness == null){
                Exactness = "any";
            }else if (exactness.toLowerCase().contains("up")) {
                Exactness = "up";
            } else if (exactness.toLowerCase().contains("down")||exactness.toLowerCase().contains("below")) {
                Exactness = "down";
            } else {
                Exactness = "exact";
                binding.enterAmount.setEnabled(false);
            }
        }
        catch (Exception e){
        }

        Glide.with(binding.bankImage).load(url).into(binding.bankImage);
        binding.amount.setText("Rs. "+amount);
        binding.enterAmount.setText(amount);
        binding.customerName.setText(customer);
        binding.billername.setText("Paying to "+billerName);
        binding.LoanName.setText("Loan Name");

        bill_id = getIntent().getStringExtra("bill_id");
        profile_id = getIntent().getStringExtra("profile_id");

//        if(exactness.equals("Exact")){
//            binding.enterAmount.setEnabled(false);
//        }


        binding.payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean next = true;
                String amont = binding.enterAmount.getText().toString();
                String originalamount = getIntent().getStringExtra("amount");
                double a = Double.parseDouble(amont);
                double oa = Double.parseDouble(originalamount);

                if(Exactness == "up"){
                    if(oa<=a){
                        next = true;
                    }
                    else {
                        Toast.makeText(context, "You are not allowed to pay any amount lower than Rs."+originalamount, Toast.LENGTH_SHORT).show();
                        next = false;
                    }
                }else if (Exactness == "down"){
                    if(oa<a){
                        Toast.makeText(context, "You are not allowed to pay any amount greater than Rs."+originalamount, Toast.LENGTH_SHORT).show();
                        next = false;
                    }else next = true;

                }else if (Exactness == "any"){
                        next = true;
                }else {
                    next = true;
                }



                if(next) nextScreen();
            }
        });
    }



    private void nextScreen(){

        Intent i = new Intent(context, SelectPaymentMethod.class);
        i.putExtra("billerName",billerName);
        i.putExtra("bill_id",bill_id);
        i.putExtra("profile_id",profile_id);
        i.putExtra("logo",url);
        i.putExtra("amount",binding.enterAmount.getText().toString());
        startActivity(i);
    }


}