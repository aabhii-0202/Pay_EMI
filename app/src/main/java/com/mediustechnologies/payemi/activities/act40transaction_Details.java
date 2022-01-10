package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.internal.ManufacturerUtils;
import com.mediustechnologies.payemi.ApiResponse.TransactionDetails;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.ActivityTransactionDetailsBinding;

public class act40transaction_Details extends AppCompatActivity {
    private ActivityTransactionDetailsBinding binding;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
    }

    private String formatdate(String date) {
        String ans = "";
        String day = date.substring(8, 10);
        ans += day + " ";
        String month = date.substring(5, 7);
        String months[] = {
                "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep",
                "Oct", "Nov", "Dec"
        };

        int m = Integer.parseInt(month);
        try {
            ans += months[m - 1];
        } catch (Exception e) {
            ans = "Error finding month";
        }
        ans+=" "+date.substring(2,4);

//        2022-01-07 07:26:22.418915+00:00

        String time = date.substring(11,16);
        String hr = time.substring(0,2);
        String min = time.substring(3);

        String ampm;

        int hour = Integer.parseInt(hr);
        if(hour>12){
            ampm = "PM";
        }else ampm = "AM";
        hour = hour%12;

        ans = ans+"  "+hour+":"+min+" "+ampm;


        return ans;
    }

    private void init(){

        TransactionDetails item = getIntent().getParcelableExtra("item");

        if(item.getType().equals("transaction")){
            binding.transactionId.setText("Not in api");
            binding.detailsbankname.setText("To - "+item.getBiller_name());
            binding.customerName.setText("From - not in api");
            binding.customeremail.setText("not in api");
            binding.billerid.setText("Bill Id - "+item.getBill_id());
            binding.tobankname.setText("To "+item.getBiller_name());
            binding.paidAmount.setText(item.getAmount());
            String status = item.getBbps_transaction_status();
            String date = item.getTransaction_datetime();
            date = formatdate(date);

            if(status.equals("Successful")){
                binding.statusdate.setText("  Completed | "+date);
                binding.statusdate.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_tick, 0, 0, 0);
            }
            else{
                binding.statusdate.setText("  Failed | "+date);
            }

            String url = getIntent().getStringExtra("logo");
            Glide.with(binding.image).load(url).into(binding.image);


        }

        binding.backButton.setOnClickListener(view -> finish());

    }
}