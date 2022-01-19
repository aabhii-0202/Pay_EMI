package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.ApiResponse.homePage;
import com.mediustechnologies.payemi.activities.payments.act35payment_Page;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiBinding;

import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;


public class Exactness extends AppCompatActivity  {

    private ActivityPayEmiBinding binding;
    private final Context context = this;
    private homePage data;
    private String billerName,bill_id,profile_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();

    }

    private void init(){

        data = getIntent().getParcelableExtra("data");

        String url = data.getBiller__logo_url();
        String exactness = getIntent().getStringExtra("exact");
        String customer = getIntent().getStringExtra("customer");
        String amount = getIntent().getStringExtra("amount");
        billerName = getIntent().getStringExtra("billerName");

        try{
            if (exactness.equalsIgnoreCase("EXACT_UP")) {

            } else if (exactness.equalsIgnoreCase("EXACT_DOWN")) {

            } else {
                binding.enterAmount.setEnabled(false);
            }
//            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }
        catch (Exception e){
//            System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
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


        binding.payButton.setOnClickListener(view ->
//                openRazorpay());
                nextScreen());
    }


/*
    private void openRazorpay(){

        Checkout checkout = new Checkout();
        checkout.setKeyID(constants.RAZOR_PAY_KEY);

//        checkout.setImage(R.drawable.);

        JSONObject object = new JSONObject();
        try {
            object.put("name","Pay EMI");
            object.put("description","Test payment");
            object.put("theme.color","#0093DD");
            object.put("currency","INR");
            object.put("amount","500");
            object.put("prefill.contact","9087654321");
            object.put("prefill.email","abc@gmail.com");
            checkout.open(Exactness.this,object);

        }
        catch (JSONException e){
            e.printStackTrace();
        }


    }*/


    private void nextScreen(){
        Intent i = new Intent(context, act35payment_Page.class);
        i.putExtra("billerName",billerName);
        i.putExtra("bill_id",bill_id);
        i.putExtra("profile_id",profile_id);
        i.putExtra("logo",data.getBiller__logo_url());
        i.putExtra("amount",binding.enterAmount.getText().toString());
        startActivity(i);
    }


}