package com.mediustechnologies.payemi.activities.payments;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mediustechnologies.payemi.activities.PaymentSuccessful;
import com.mediustechnologies.payemi.databinding.ActivityCardPaymentBinding;
import com.razorpay.PaymentResultListener;
import com.razorpay.Razorpay;
import com.razorpay.ValidateVpaCallback;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;

public class CardPayment extends AppCompatActivity implements PaymentResultListener {

    private ActivityCardPaymentBinding binding;
    private final Context context = this;
    private Razorpay razorpay;
    private JSONObject payload;
    private String amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        amount = getIntent().getStringExtra("amount");
        binding.amount.setText("₹ "+amount);

        razorpay = new Razorpay(this);
        razorpay.setWebView(binding.paymentWebview);
        razorpay.changeApiKey("rzp_test_a9DQDSDQfs10TS");



        binding.back.setOnClickListener(view -> finish());
        binding.proceedtopayment.setOnClickListener(view -> getcardDetails());
        binding.expirydate.setOnClickListener(view -> {
                datepicker();

        });

    }



    private void datepicker(){
        Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(context, (selectedMonth, selectedYear) -> {
            selectedMonth++;
            binding.expirydate.setText(selectedMonth+"/"+selectedYear);
        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
                builder.setMaxYear(3000).build().show();
    }



    private void getcardDetails() {
        String cardno = binding.cardnumber.getText().toString();
        String cardholdername = binding.cardholdername.getText().toString();
        String expiry = binding.expirydate.getText().toString();
        String cvv = binding.cvv.getText().toString();

        if(cardno.length()!=16){
            binding.cardnumber.setError("Enter proper card number");
        }else if(cardholdername.trim().length()<1||expiry.length()==0||cvv.trim().length()<3){
            Toast.makeText(context, "Please enter all the details.", Toast.LENGTH_LONG).show();
        }else{

            verify(cardno,cardholdername,expiry,cvv);
        }
    }

    private void verify(String cardno, String cardholdername, String expiry, String cvv) {

        cardholdername = "Abhishek Singh";
        cardno = "4844410110527770";
        String month = "10";
        String year = "26";

        try {
            payload = new JSONObject("{currency: 'INR'}");
            payload.put("amount", amount+"00");
            payload.put("contact", "9999999999");
            payload.put("email", "customer@name.com");
        } catch (Exception ex) {
            Log.e("tag", "Add card exception : "+ex.toString());
        }

        try {
            payload.put("method", "card");
            payload.put("card[name]", cardholdername);
            payload.put("card[number]", cardno);
            payload.put("card[expiry_month]", month);
            payload.put("card[expiry_year]", year);
            payload.put("card[cvv]", cvv);
            //customer id
            if(binding.securelysave.isChecked()){
                payload.put("customer_id", "cust_4lsdkfldlteskf");
                payload.put("save", 1);
            }
            sendRequest();
        } catch (Exception ex) {
            Log.e("tag", "Add card exception : "+ex.toString());
        }



    }

    private void sendRequest() {
        razorpay.validateFields(payload, new Razorpay.ValidationListener() {
            @Override
            public void onValidationSuccess() {
                try {
                    Log.d("tag","Validating success.");

                    binding.outerbox.setVisibility(View.VISIBLE);
                    binding.paymentWebview.setVisibility(View.VISIBLE);
                    razorpay.submit(payload, CardPayment.this);

                }  catch (Exception ex) {
                    Log.e("tag", "Add card exception : "+ex.toString());
                }
            }

            @Override
            public void onValidationError(Map<String, String> error) {
                Log.e("tag", "Card Validation failed: " + error.get("field") + " " + error.get("description"));
                Toast.makeText(context, "Validation: " + error.get("field") + " " + error.get("description"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(context, "Card payment success", Toast.LENGTH_SHORT).show();
        Log.d("tag", "Card PaymentSuccess: Success");
        binding.outerbox.setVisibility(View.GONE);
        binding.paymentWebview.setVisibility(View.GONE);

        Intent i = new Intent(context, PaymentSuccessful.class);
        i.putExtra("billerName",getIntent().getStringExtra("billerName"));
        i.putExtra("bill_id",getIntent().getStringExtra("bill_id"));
        startActivity(i);

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(context, "Card payment failed", Toast.LENGTH_SHORT).show();
        Log.d("tag", "Card Payment: Failed");
        binding.outerbox.setVisibility(View.GONE);
        binding.paymentWebview.setVisibility(View.GONE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        razorpay.onActivityResult(requestCode,resultCode,data);
    }
}