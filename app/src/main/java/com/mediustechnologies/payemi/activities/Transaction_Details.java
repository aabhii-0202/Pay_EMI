package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.google.android.material.internal.ManufacturerUtils;
import com.mediustechnologies.payemi.ApiResponse.TransactionDetails;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.ActivityTransactionDetailsBinding;

import java.io.File;
import java.util.Date;

public class Transaction_Details extends AppCompatActivity {
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



        String time = date.substring(11,16);
        String hr = time.substring(0,2);
        String min = time.substring(3);

        String ampm;

        int hour = Integer.parseInt(hr);
        if(hour>12){
            ampm = "PM";
        }
        else ampm = "AM";
        hour = hour%12;
        ans = ans+"  "+hour+":"+min+" "+ampm;

        return ans;
    }

    private void init(){

        TransactionDetails item = getIntent().getParcelableExtra("item");

        try {
            if (item.getType().equals("transaction")) {
                binding.transactionId.setText(item.getRazorpay_transaction_id());
                binding.detailsbankname.setText("To - " + item.getBiller_name());
                binding.customerName.setText("From - " + item.getCustomer_name());
                binding.customeremail.setText("" + item.getCustomer_email());
                binding.billerid.setText("Bill Id - " + item.getBiller_id());
                binding.tobankname.setText("To " + item.getBiller_name());
                binding.paidAmount.setText(item.getAmount());
                String status = item.getBbps_transaction_status();
                String date = item.getTransaction_datetime();
                date = formatdate(date);

                try {
                    if (status.equals("Successful")) {
                        binding.statusdate.setText("  Completed | " + date);
                        binding.statusdate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick, 0, 0, 0);
                    } else {
                        binding.statusdate.setText("  Failed | " + date);
                    }
                } catch (Exception e) {
                    binding.statusdate.setText("  Failed | " + date);
                }

                String url = getIntent().getStringExtra("logo");
                Glide.with(binding.image).load(url).into(binding.image);


            }
        }catch (Exception e){}

        binding.backButton.setOnClickListener(view -> finish());
        binding.sharebutton.setOnClickListener(view -> share());

    }

    private void share(){
        if(!checkPermission()){
            return;
        }
        try{
            String path = Environment.getExternalStorageDirectory().toString()+"/PayEMI";
            File fileDir = new File(path);
            if(!fileDir.exists()){
               fileDir.mkdir();
            }

            String mpath = path+"Screenshot"+new Date().getTime()+".png";
            Bitmap bitmap = screenshot();


        }catch (Exception e){

        }
    }

    private Bitmap screenshot(){
        view v
        Bitmap bitmap = Bitmap.createBitmap();
    }

    private boolean checkPermission(){
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
            return false;
        }
        return true;
    }
}