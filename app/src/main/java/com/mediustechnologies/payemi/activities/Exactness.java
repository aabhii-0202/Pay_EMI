package com.mediustechnologies.payemi.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.ApiResponse.CreateOrderIdResponse;
import com.mediustechnologies.payemi.DTO.HomepageDTO;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityPayEmiBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Exactness extends BaseAppCompatActivity implements PaymentResultListener {

    private ActivityPayEmiBinding binding;
    private final Context context = this;
    private HomepageDTO data;
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

        Checkout.preload(getApplicationContext());
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

                try {
                    boolean next = true;
                    String amont = binding.enterAmount.getText().toString();
                    String originalamount = getIntent().getStringExtra("amount");
                    double a = Double.parseDouble(amont);
                    double oa = Double.parseDouble(originalamount);

                    if (Exactness == "up") {
                        if (oa <= a) {
                            next = true;
                        } else {
                            Toast.makeText(context, "You are not allowed to pay any amount lower than Rs." + originalamount, Toast.LENGTH_SHORT).show();
                            next = false;
                        }
                    } else if (Exactness == "down") {
                        if (oa < a) {
                            Toast.makeText(context, "You are not allowed to pay any amount greater than Rs." + originalamount, Toast.LENGTH_SHORT).show();
                            next = false;
                        } else next = true;

                    } else if (Exactness == "any") {
                        next = true;
                    } else {
                        next = true;
                    }
                    if (next) getOrderId();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    private void getOrderId() {

        String amount = binding.enterAmount.getText().toString();
        int d = Integer.parseInt(amount);
        String note = binding.note.getText().toString();
        Call<CreateOrderIdResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getRazorpayOrderId(utils.access_token,utils.profileId,bill_id,d,note);

        call.enqueue(new Callback<CreateOrderIdResponse>() {
            @Override
            public void onResponse(Call<CreateOrderIdResponse> call, Response<CreateOrderIdResponse> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        String order_id = response.body().getOrder_id();
                        String amount = Integer.toString(d*100);
                        startPayment(utils.name,"Bill_id"+bill_id,order_id,amount,utils.phone);
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

    public void startPayment(String name,String description,String order_id
    ,String amount,String phone) {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_LdI1ob5rGXZDF6");
        checkout.setImage(R.drawable.cornericon);
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", description);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", order_id);//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", amount);//pass amount in currency subunits
            options.put("prefill.contact",phone);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("tag", "Error in starting Razorpay Checkout", e);
        }
    }





    @Override
    public void onPaymentSuccess(String s) {

        System.out.println("-----------------------------------Success");
//        Intent j = new Intent(context, PaymentSuccessful.class);
//        j.putExtra("billerName",getIntent().getStringExtra("billerName"));
//        j.putExtra("bill_id",getIntent().getStringExtra("bill_id"));
//        j.putExtra("status",true);
//        startActivity(j);
    }

    @Override
    public void onPaymentError(int i, String s) {

        System.out.println(i+"  -----------------------------------Failed "+s);

//        Intent j = new Intent(context, PaymentSuccessful.class);
//        j.putExtra("billerName",getIntent().getStringExtra("billerName"));
//        j.putExtra("bill_id",getIntent().getStringExtra("bill_id"));
//        j.putExtra("status",false);
//        startActivity(j);
    }
    private void nextScreen() {
//        Intent i = new Intent(context, SelectPaymentMethod.class);
//        i.putExtra("billerName",billerName);
//        i.putExtra("bill_id",bill_id);
//        i.putExtra("profile_id",profile_id);
//        i.putExtra("logo",url);
//        i.putExtra("amount",binding.enterAmount.getText().toString());
//        startActivity(i);
    }
}