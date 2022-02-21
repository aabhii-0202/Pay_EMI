package com.mediustechnologies.payemi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mediustechnologies.payemi.ApiResponse.GetBillDetailsResponse;
import com.mediustechnologies.payemi.ApiResponse.RedeemScratchCard;
import com.mediustechnologies.payemi.ApiResponse.getCashback;
import com.mediustechnologies.payemi.BuildConfig;
import com.mediustechnologies.payemi.DTO.billFetchDTO;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.DashBoardclasses.Home_Nav;
import com.mediustechnologies.payemi.activities.scratchCard.listener.ScratchListener;
import com.mediustechnologies.payemi.activities.scratchCard.ui.ScratchCardLayout;
import com.mediustechnologies.payemi.adapters.GetBillDetailsAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityPaymentSuccessfulBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentSuccessful extends BaseAppCompatActivity {

    private ActivityPaymentSuccessfulBinding binding;
    private final Context context = this;
    private String cashback,bill_id;
    private boolean scratched,paymentstatus;
    private billFetchDTO data;
    private int nooftrials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentSuccessfulBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getbilldetails();

    }

    private void setData() {


        Log.d("tag", "Bill fetch API call successful " + data.toString());

        String bankname = getIntent().getStringExtra("billerName");
        binding.bankName.setText("To "+bankname);
        binding.loanname.setText("Not in API");
        binding.paidAmount.setText(data.getAmount());
        if(paymentstatus)
            binding.timeanddate.setText("  Completed | "+data.getTransaction_date_and_time());
        else {
            binding.timeanddate.setText("  Failed | " + data.getTransaction_date_and_time());
            binding.timeanddate.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_crosspopupred, 0, 0, 0);
        }
        binding.recieptBankName.setText(bankname);

        if(data.getRespBillPeriod()!=null)
            binding.BillPeriod.setText(data.getRespBillPeriod());
        else binding.billperiodholder.setVisibility(View.GONE);

        if(data.getTransaction_date()!=null)
            binding.BillDate.setText(data.getTransaction_date());
        else binding.billdateholder.setVisibility(View.GONE);

        if(data.getId()!=null)
            binding.BillNumber.setText(data.getId());
        else binding.billnumberholder.setVisibility(View.GONE);

        if(data.getBill_number()!=null)
            binding.BillerID.setText(data.getBill_number());
        else binding.billeridholder.setVisibility(View.GONE);

        if(data.getAmount()!=null)
            binding.TotalAmount.setText(data.getAmount());
        else binding.totalamountholder.setVisibility(View.GONE);

        if(data.getTransation_status()!=null)
            binding.TransactionStatus.setText("Pending "+data.getTransation_status());
        else binding.transactionstatusholder.setVisibility(View.GONE);

        if(data.getTransaction_id()!=null)
            binding.TransactionID.setText(data.getTransaction_id());
        else binding.transactionidholder.setVisibility(View.GONE);

        if(data.getTransaction_date_and_time()!=null)
            binding.TransactionDateTime.setText(data.getTransaction_date_and_time());
        else binding.transactiondateholder.setVisibility(View.GONE);

        if(data.getCustomer_name()!=null)
            binding.CName.setText(data.getCustomer_name());
        else binding.custnameholder.setVisibility(View.GONE);

        if(data.getCustomer_mobile()!=null)
            binding.CNumber.setText(data.getCustomer_mobile());
        else binding.customernumberholder.setVisibility(View.GONE);

        if(data.getInitiation_channel()!=null)
            binding.InitiatingChannel.setText(data.getInitiation_channel());
        else binding.channelholder.setVisibility(View.GONE);

        if(data.getPayment_mode()!=null)
            binding.paymentMode.setText(data.getPayment_mode());
        else binding.modelholder.setVisibility(View.GONE);

        if(data.getAmount()!=null)
            binding.BillAmount.setText(data.getAmount());
        else binding.billamountholder.setVisibility(View.GONE);

        if(data.getService_tax()!=null)
            binding.serviceTax.setText(data.getService_tax());
        else binding.servicetaxholder.setVisibility(View.GONE);

//        binding.ApprovalNumber.setText("Not in API");
//        binding.convineanceFee.setText(data.getcustomer_convinience_fees());

        binding.totalAmount.setText(data.getAmount());

        LinkedHashMap<String,String> variableData = new LinkedHashMap<>();
        variableData.putAll(data.getAmountOptions());
        variableData.putAll(data.getInputparams_value());
        variableData.putAll(data.getBiller_additional_info());
        recyclerview(variableData);

    }

    private void recyclerview(LinkedHashMap<String, String> variableData) {
        RecyclerView recyclerView = binding.variablerec2;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        GetBillDetailsAdapter adapter = new GetBillDetailsAdapter(variableData);
        recyclerView.setAdapter(adapter);

    }

    private void getbilldetails() {
        nooftrials++;
        System.out.println(nooftrials);
        String token = utils.access_token;

        Call<GetBillDetailsResponse> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getBillDetails(token,bill_id);

        call.enqueue(new Callback<GetBillDetailsResponse>() {
            @Override
            public void onResponse(Call<GetBillDetailsResponse> call, Response<GetBillDetailsResponse> response) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")) {
                        data = response.body().getData().get(0);
                        setData();
                        String status = data.getTransation_status();
                        status = status.toLowerCase();
                        if(status.equalsIgnoreCase("SU")||status.contains("success")){
                            paymentsuccess();
                        }else{
                            if(nooftrials<10){
                                new Handler().postDelayed(() -> {
                                    getbilldetails();
                                },5000);
                            }

                            else {
                                paymentsuccess();
                            }
                        }
                        binding.scrollView.setVisibility(View.VISIBLE);
                        binding.progress.setVisibility(View.GONE);
                    }
                    else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                        }
                    }

                }
                else{
                    Log.d("tag", "onResponse: getbill detail "+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetBillDetailsResponse> call, Throwable t) {
                Log.d("tag","getbill details "+t.toString());
            }
        });
    }

    private void scratch() {

        Call<getCashback> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getCashback(utils.access_token,utils.bill_id,utils.profileId);

        call.enqueue(new Callback<getCashback>() {
            @Override
            public void onResponse(Call<getCashback> call, Response<getCashback> response) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body().getError() == null || response.body().getError().equalsIgnoreCase("false")){
                        cashback = response.body().getCashback_amount();
                        binding.scratchpopup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!scratched) {
                                    Dialog d = new Dialog(context);
                                    d.setContentView(R.layout.scratchcardlayout);
                                    d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    TextView scratchamount = d.findViewById(R.id.scratch_cashback_amount);
                                    scratchamount.setText("Rs. " + cashback);
                                    d.show();

                                    com.mediustechnologies.payemi.activities.scratchCard.ui.ScratchCardLayout card = d.findViewById(R.id.scratchCard1);
                                    card.setScratchListener(new ScratchListener() {
                                        @Override
                                        public void onScratchStarted() {

                                        }

                                        @Override
                                        public void onScratchProgress(@NonNull ScratchCardLayout scratchCardLayout, int atLeastScratchedPercent) {
                                            Log.d("tag", "onScratchProgress: " + atLeastScratchedPercent);
                                            if (atLeastScratchedPercent > 8) {
                                                Log.d("tag", "scratched");
                                                scratched = true;
                                                redeem();
                                            }
                                        }

                                        @Override
                                        public void onScratchComplete() {

                                        }
                                    });


                                } else {
                                    Dialog d = new Dialog(context);
                                    d.setContentView(R.layout.already_redeemed_scratchcard);
                                    d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    TextView scratchamount = d.findViewById(R.id.scratch_cashback_amount);
                                    scratchamount.setText("Rs. " + cashback);
                                    d.show();
                                }
                            }
                        });
                    }else{
                        try {
                            utils.errortoast(context,response.body().getMessage());
                        }catch (Exception e){
                            Log.e("tag",e.toString());
                        }
                    }

            }







                else{
                    Log.d("tag","Get Cashback:  "+response.code());
                }
            }

            @Override
            public void onFailure(Call<getCashback> call, Throwable t) {

            }
        });

    }

    private void redeem() {

        Call<RedeemScratchCard> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().redeemscratch(utils.access_token,utils.profileId,bill_id);

        call.enqueue(new Callback<RedeemScratchCard>() {
            @Override
            public void onResponse(Call<RedeemScratchCard> call, Response<RedeemScratchCard> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    if(response.body().getMessage().equals("Success")){
                        Log.d("tag","Redeemed cashback successfull");
                        Toast.makeText(context,"Cashback Added Successfully",Toast.LENGTH_LONG);


                    }
                }
                else if(response.code()==400){
                    System.out.println(response.message());
                }else{
                    Log.e("tag","Error ar redeem code "+response.code());
                }
            }

            @Override
            public void onFailure(Call<RedeemScratchCard> call, Throwable t) {

            }
        });
    }

    private void init() {
        nooftrials = 0;
        binding.scrollView.setVisibility(View.GONE);
        binding.progress.setVisibility(View.VISIBLE);
        paymentstatus = getIntent().getBooleanExtra("status",false);
        scratched = false;
        bill_id = getIntent().getStringExtra("bill_id");
        binding.crossButton.setOnClickListener(view -> nextScreen());
        if(!paymentstatus){
            binding.paymentstatus.setText("Payment Failed");
            binding.paymentstatus.setTextColor(getResources().getColor(R.color.errormessagecolor));
            Drawable res = getResources().getDrawable(R.drawable.ic_crosspopupred);
            binding.failedimg.setImageDrawable(res);
            binding.timeanddate.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_crosspopupred, 0, 0, 0);
        }

        binding.share.setOnClickListener(view -> {
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
        intent.putExtra(Intent.EXTRA_TEXT,getResources().getString(R.string.Share_Message));
        intent.putExtra(Intent.EXTRA_STREAM,uri);

        Intent chooser = Intent.createChooser(intent, "Share Payment Image");
        try{
            List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);

            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                this.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            startActivity(chooser);
        }
        catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void paymentsuccess(){
        scratch();
        binding.paymentstatus.setText("Payment Successful");
        binding.gif.setVisibility(View.VISIBLE);
        binding.failedimg.setVisibility(View.GONE);
        binding.paymentstatus.setTextColor(Color.parseColor("#0c8f2d"));
        binding.timeanddate.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_tick, 0, 0, 0);
        binding.timeanddate.setText("  Completed | " + data.getTransaction_date_and_time());
        binding.scratchpopup.setVisibility(View.VISIBLE);

    }

    private File save(){

        if(!checkPermission()){
            return null;
        }
        try{
//            String path = Environment.getExternalStorageDirectory().toString()+"/PayEMI";
            String path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/.PayEMI").toString();
            File fileDir = new File(path);

            if(!fileDir.exists()){
                fileDir.mkdir();

            }

            String mpath = path+"PayEmi"+".png";
//            mpath.replaceAll(":", ".");
            Bitmap bitmap = screenshot();
            File file = new File(mpath);
            FileOutputStream fout = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fout);
            fout.flush();
            fout.close();

            Toast.makeText(context,"Image saved successfully.",Toast.LENGTH_LONG).show();

            return file;

        }catch (Exception e){
            e.printStackTrace();
            Log.e("tag","While taking screen shot "+e.toString());
        }
        return null;
    }

    private Bitmap screenshot() {

        View v = binding.ss;
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(),v.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private boolean checkPermission(){
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED||permission2 != PackageManager.PERMISSION_GRANTED){
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextScreen();
    }

    private void nextScreen(){
        Intent i = new Intent(context, Home_Nav.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}