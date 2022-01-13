package com.mediustechnologies.payemi.activities.payments;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mediustechnologies.payemi.databinding.ActivityPaymentPageBinding;
import com.razorpay.Razorpay;

public class act35payment_Page extends AppCompatActivity {
    
    
    private ActivityPaymentPageBinding binding;
    private final Context context = this;
    private String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getpaymentmethods();
    }

    private void getpaymentmethods(){
        Razorpay razorpay = new Razorpay(this);

        razorpay.getPaymentMethods(new Razorpay.PaymentMethodsCallback() {
            @Override
            public void onPaymentMethodsReceived(String result) {
                Log.d("Result", "" + result);
                res = result;
                init();
            }

            @Override
            public void onError(String error) {
                Log.d("Get Payment error",error);
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
        startActivity(i);
    }

    private void openUPIPayment(){
        Intent i = new Intent(context,UPIPayment.class);
        i.putExtra("feild",res);
        startActivity(i);
    }

    private void openWalletPayment(){
        Intent i = new Intent(context, LinkWallet.class);
        i.putExtra("feild",res);
        startActivity(i);
    }

    private void openNetBacnkin(){
        Intent i = new Intent(context,NetBanking.class);
        i.putExtra("feild",res);
        startActivity(i);
    }


}