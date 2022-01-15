package com.mediustechnologies.payemi.activities.payments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mediustechnologies.payemi.activities.PaymentSuccessful;
import com.mediustechnologies.payemi.databinding.ActivityAct12AddNewUpiBinding;
import com.razorpay.PaymentResultListener;
import com.razorpay.Razorpay;
import com.razorpay.ValidateVpaCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class UPIPayment extends AppCompatActivity implements PaymentResultListener {
    private ActivityAct12AddNewUpiBinding binding;
    private Razorpay razorpay;
    private final Context context = this;
    private JSONObject payload;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityAct12AddNewUpiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> finish());
        amount = getIntent().getStringExtra("amount");
        binding.amount.setText("₹ "+amount);

        razorpay = new Razorpay(this);
        razorpay.setWebView(binding.paymentWebview);
        razorpay.changeApiKey("rzp_test_a9DQDSDQfs10TS");


        binding.proceedtopayment.setOnClickListener(view -> verifyuip());

    }

    private void verifyuip() {

        String vpa = binding.vpa.getText().toString().trim();

        razorpay.isValidVpa(vpa, new ValidateVpaCallback() {
            @Override
            public void onResponse(boolean b) {
                if (b) {
//                    Toast.makeText(context, "VPA is valid", Toast.LENGTH_LONG).show();
                    sendPaymentRequest(vpa);
                }
                else
                    Toast.makeText(context, "VPA is Not valid", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(context, "Error in validating", Toast.LENGTH_LONG).show();
            }
        });





    }

    private void sendPaymentRequest(String vpa){
        try {
            payload = new JSONObject("{currency: 'INR'}");
            payload.put("amount", amount+"00");
            payload.put("contact", "9999999999");
            payload.put("email", "customer@name.com");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            payload.put("method", "upi");
            payload.put("vpa", vpa);

            sendRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendIntentPaymentRequest(String vpa){
        try {
            payload = new JSONObject("{currency: 'INR'}");
            payload.put("amount", "111");
            payload.put("contact", "9999999999");
            payload.put("email", "customer@name.com");
            //payload.put("upi_app_package_name", "com.google.android.apps.nbu.paisa.user");
            payload.put("display_logo", true);
        } catch (Exception e) {
            Log.e("tag","UPI exception"+e.toString());
        }

        try {
            JSONArray jArray = new JSONArray();
            jArray.put("in.org.npci.upiapp");
            jArray.put("com.snapwork.hdfc");
            payload.put("description","Credits towards consultation");
            //payload.put("key_id","rzp_test_kEVtCVFWAjUQPG");
            payload.put("method", "upi");
            payload.put("_[flow]", "intent");
            payload.put("preferred_apps_order", jArray);
            payload.put("other_apps_order", jArray);
            sendRequest();
        } catch (Exception e) {
            Log.e("tag","UPI exception"+e.toString());
        }
    }


    private void sendRequest() {
        razorpay.validateFields(payload, new Razorpay.ValidationListener() {
            @Override
            public void onValidationSuccess() {
                try {
                    Log.d("tag","UPI Validating success");

                    binding.outerbox.setVisibility(View.VISIBLE);
                    binding.paymentWebview.setVisibility(View.VISIBLE);
                    razorpay.submit(payload, UPIPayment.this);

                }  catch (Exception ex) {
                    Log.e("tag", "UPI exception : "+ex.toString());
                }
            }

            @Override
            public void onValidationError(Map<String, String> error) {
                Log.e("tag", "UPI Validation failed: " + error.get("field") + " " + error.get("description"));
                Toast.makeText(context, "Validation: " + error.get("field") + " " + error.get("description"), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(context, "UPI payment success", Toast.LENGTH_SHORT).show();
        Log.d("tag", "UPI PaymentSuccess: Success");
        binding.outerbox.setVisibility(View.GONE);
        binding.paymentWebview.setVisibility(View.GONE);

        Intent i = new Intent(context, PaymentSuccessful.class);
        i.putExtra("billerName",getIntent().getStringExtra("billerName"));
        i.putExtra("bill_id",getIntent().getStringExtra("bill_id"));
        startActivity(i);

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(context, "UPI payment failed", Toast.LENGTH_SHORT).show();
        Log.d("tag", "UPI Payment: Failed");
        binding.outerbox.setVisibility(View.GONE);
        binding.paymentWebview.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        razorpay.onActivityResult(requestCode,resultCode,data);



    }
}