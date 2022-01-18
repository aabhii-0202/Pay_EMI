package com.mediustechnologies.payemi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.internal.ManufacturerUtils;
import com.mediustechnologies.payemi.ApiResponse.TransactionDetails;
import com.mediustechnologies.payemi.BuildConfig;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.ActivityTransactionDetailsBinding;

import java.io.File;
import java.io.FileOutputStream;
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
        binding.sharebutton.setOnClickListener(view -> {
            File file = save();
            if(file != null) share(file);
        });

    }

    private void share(File file){

        Uri uri;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(context,getPackageName()+".provider",file);
        }else{
            uri = Uri.fromFile(file);
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Screenshot");
        intent.putExtra(Intent.EXTRA_TEXT,"Share this with you");
        intent.putExtra(Intent.EXTRA_STREAM,uri);

        try{
            startActivity(Intent.createChooser(intent,"Share using"));
        }
        catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private File save(){
        if(!checkPermission()){
            return null;
        }
        try{
            String path = Environment.getExternalStorageDirectory().toString()+"/PayEMI";
            File fileDir = new File(path);
            if(!fileDir.exists()){
               fileDir.mkdir();
            }

            String mpath = path+"Screenshot"+new Date().getTime()+".png";
            Bitmap bitmap = screenshot();
            File file = new File(mpath);
            FileOutputStream fout = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fout);
            fout.flush();
            fout.close();

            Toast.makeText(context,"Image saved successfully.",Toast.LENGTH_LONG).show();

            return file;

        }catch (Exception e){
            Log.e("tag","While taking screen shot "+e.toString());
        }
        return null;
    }

    private Bitmap screenshot() {
        View v = findViewById(R.id.transactiondetails);
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(),v.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private boolean checkPermission(){
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
            File file = save();
            if(file != null) share(file);
        }

        else Toast.makeText(context, "Grant permission to share image.", Toast.LENGTH_SHORT).show();

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

