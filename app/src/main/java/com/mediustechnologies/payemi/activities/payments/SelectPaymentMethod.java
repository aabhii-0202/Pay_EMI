package com.mediustechnologies.payemi.activities.payments;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.activities.PaymentSuccessful;
import com.mediustechnologies.payemi.databinding.ActivityPaymentPageBinding;
import com.razorpay.Razorpay;

public class SelectPaymentMethod extends AppCompatActivity {
    
    
    private ActivityPaymentPageBinding binding;
    private final Context context = this;
    private String res,amount,logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        amount = getIntent().getStringExtra("amount");
        logo = getIntent().getStringExtra("logo");
        Glide.with(binding.financerlogo).load(logo).into(binding.financerlogo);
        binding.titleamount.setText("₹ "+amount);
        binding.backButton.setOnClickListener(view -> finish());
        getpaymentmethods();
    }

    private void getpaymentmethods(){
        Razorpay razorpay = new Razorpay(this);

        razorpay.getPaymentMethods(new Razorpay.PaymentMethodsCallback() {
            @Override
            public void onPaymentMethodsReceived(String result) {
                Log.d("tag ", "Razorpay initialise: " + result);
                res = result;
                init();
            }

            @Override
            public void onError(String error) {
                Log.d("Get Payment error",error);
                Toast.makeText(context, "Razorpay initialisation error "+ error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init(){

        binding.backButton.setOnClickListener(view -> finish());
        binding.paywithcard.setOnClickListener(view -> openCardPayment());
        binding.paywithupi.setOnClickListener(view -> openUPIPayment());
        binding.paywithwallet.setOnClickListener(view -> openWalletPayment());
        binding.paywithnetbanking.setOnClickListener(view ->openNetBacnkin());
    }

    private void openCardPayment(){
        Intent i = new Intent(context,CardPayment.class);
        i.putExtra("feild",res);
        i.putExtra("billerName",getIntent().getStringExtra("billerName"));
        i.putExtra("bill_id",getIntent().getStringExtra("bill_id"));
        i.putExtra("amount",amount);

        startActivity(i);
    }

    private void openUPIPayment(){
        Intent i = new Intent(context,UPIPayment.class);
        i.putExtra("feild",res);
        i.putExtra("billerName",getIntent().getStringExtra("billerName"));
        i.putExtra("bill_id",getIntent().getStringExtra("bill_id"));
        i.putExtra("amount",amount);

        startActivity(i);
    }

    private void openWalletPayment(){
        Intent i = new Intent(context, LinkWallet.class);
        i.putExtra("feild",res);
        i.putExtra("billerName",getIntent().getStringExtra("billerName"));
        i.putExtra("bill_id",getIntent().getStringExtra("bill_id"));
        i.putExtra("amount",amount);

        startActivity(i);
    }

    private void openNetBacnkin(){
        Intent i = new Intent(context,NetBanking.class);
        i.putExtra("feild",res);
        i.putExtra("billerName",getIntent().getStringExtra("billerName"));
        i.putExtra("bill_id",getIntent().getStringExtra("bill_id"));
        i.putExtra("amount",amount);

        startActivity(i);
    }


}