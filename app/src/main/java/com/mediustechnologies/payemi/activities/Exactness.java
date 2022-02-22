package com.mediustechnologies.payemi.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.ApiResponse.CreateOrderIdResponse;
import com.mediustechnologies.payemi.DTO.HomepageDTO;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Exactness extends BaseAppCompatActivity {

    private ActivityPayEmiBinding binding;
    private final Context context = this;
    private HomepageDTO data;
    private String billerName,bill_id,profile_id,url,order_id;
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
            binding.LoanName.setText(getIntent().getStringExtra("type"));
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

        bill_id = getIntent().getStringExtra("bill_id");
        profile_id = getIntent().getStringExtra("profile_id");

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
                if(next) getOrderId();
            }
        });
    }

    private void getOrderId() {

        String amount = binding.enterAmount.getText().toString();
        int d = Integer.parseInt(amount);
        d = d*100;
        String note = binding.note.getText().toString();
        Call<CreateOrderIdResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getRazorpayOrderId(utils.access_token,utils.profileId,bill_id,d,note);

        call.enqueue(new Callback<CreateOrderIdResponse>() {
            @Override
            public void onResponse(Call<CreateOrderIdResponse> call, Response<CreateOrderIdResponse> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        order_id = response.body().getOrder_id();
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
            public void onFailure(Call<CreateOrderIdResponse> call, Throwable t) {
                Toast.makeText(context, "Failed "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("tag",t.getMessage());
            }
        });


    }


    private void nextScreen() {


//        https://razorpay.com/docs/payments/payment-gateway/android-integration/standard/build-integration/


        //todo initialise razorpay again
//        checkout.setKeyID("rzp_test_LdI1ob5rGXZDF6");


//        Intent i = new Intent(context, SelectPaymentMethod.class);
//        i.putExtra("billerName",billerName);
//        i.putExtra("bill_id",bill_id);
//        i.putExtra("profile_id",profile_id);
//        i.putExtra("logo",url);
//        i.putExtra("amount",binding.enterAmount.getText().toString());
//        startActivity(i);

    }

    private void razorpaypaymentfailed(){
        Toast.makeText(context, "Card payment failed", Toast.LENGTH_SHORT).show();
        Log.d("tag", "Card Payment: Failed ");

        Intent j = new Intent(context, PaymentSuccessful.class);
        j.putExtra("billerName",getIntent().getStringExtra("billerName"));
        j.putExtra("bill_id",getIntent().getStringExtra("bill_id"));
        j.putExtra("status",false);
        startActivity(j);

    }


}