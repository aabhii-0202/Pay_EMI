package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiBinding;

import org.json.JSONException;
import org.json.JSONObject;
import com.mediustechnologies.payemi.commons.constants;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;


public class act8payEMI extends AppCompatActivity  implements PaymentResultWithDataListener {

    private ActivityPayEmiBinding binding;
    private final Context context = this;
    private String billerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();

    }

    private void init(){

        String url = getIntent().getStringExtra("logo");
//        String exactness = getIntent().getStringExtra("Exactness");
        String customer = getIntent().getStringExtra("customer");
        String amount = getIntent().getStringExtra("amount");
        billerName = getIntent().getStringExtra("billerName");

        Glide.with(binding.bankImage).load(url).into(binding.bankImage);
        binding.amount.setText("Rs. "+amount);
        binding.enterAmount.setText(amount);
        binding.customerName.setText(customer);
        binding.billername.setText("Paying to "+billerName);
        binding.LoanName.setText("Loan Name");

//        if(exactness.equals("Exact")){
//            binding.enterAmount.setEnabled(false);
//        }


        binding.payButton.setOnClickListener(view ->
                openRazorpay());
    }



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
            checkout.open(act8payEMI.this,object);

        }
        catch (JSONException e){
            e.printStackTrace();
        }


    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        nextScreen();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        nextScreen();
    }

    private void nextScreen(){
        Intent i = new Intent(context,act9paymentSuccessful.class);
        i.putExtra("billerName",billerName);
        startActivity(i);
    }


}