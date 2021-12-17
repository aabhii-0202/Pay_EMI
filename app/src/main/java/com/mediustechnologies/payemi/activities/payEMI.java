package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mediustechnologies.payemi.databinding.ActivityPayEmiBinding;

import org.json.JSONException;
import org.json.JSONObject;
import com.mediustechnologies.payemi.commons.constants;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;


public class payEMI extends AppCompatActivity  implements PaymentResultWithDataListener {

    private ActivityPayEmiBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayEmiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();

    }

    private void init(){
        binding.payButton.setOnClickListener(view ->
//                startActivity(new Intent(context, paypmentSuccessful.class)));
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
            checkout.open(payEMI.this,object);

        }
        catch (JSONException e){
            e.printStackTrace();
        }


    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        startActivity(new Intent(context,paypmentSuccessful.class));
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        startActivity(new Intent(context,paypmentSuccessful.class));
    }


}