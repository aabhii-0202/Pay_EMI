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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mediustechnologies.payemi.ApiResponse.RedeemScratchCard;
import com.mediustechnologies.payemi.ApiResponse.getCashback;
import com.mediustechnologies.payemi.BuildConfig;
import com.mediustechnologies.payemi.DTO.billFetchDTO;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.activities.scratchCard.listener.ScratchListener;
import com.mediustechnologies.payemi.activities.scratchCard.ui.ScratchCardLayout;
import com.mediustechnologies.payemi.adapters.GetBillDetailsAdapter;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.commons.utils;
import com.mediustechnologies.payemi.databinding.ActivityPaymentSuccessfulBinding;
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

public class PaymentSuccessful extends AppCompatActivity {

    private ActivityPaymentSuccessfulBinding binding;
    private final Context context = this;
    private String cashback,bill_id,profile_id;
    private boolean scratched;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentSuccessfulBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        getbilldetails();

    }

    private void setData(billFetchDTO data) {

        Log.d("tag", "Payment Successful " + data.toString());

        String bankname = getIntent().getStringExtra("billerName");
        binding.bankName.setText("To "+bankname);
        binding.loanname.setText("Not in API");
        binding.paidAmount.setText(data.getAmount());
        binding.timeanddate.setText("  Completed | "+data.getTransaction_date_and_time());

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
            binding.TransactionStatus.setText(data.getTransation_status());
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
        String token = utils.access_token;

        Call<List<billFetchDTO>> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getBillDetails(token,bill_id);

        call.enqueue(new Callback<List<billFetchDTO>>() {
            @Override
            public void onResponse(Call<List<billFetchDTO>> call, Response<List<billFetchDTO>> response) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    billFetchDTO data = response.body().get(0);
                    setData(data);



                    scratch();
                }
                else{
                    Log.d("tag", "onResponse: getbill detail "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<billFetchDTO>> call, Throwable t) {
                Log.d("tag","getbill details "+t.toString());
            }
        });
    }

    private void scratch() {

        Call<getCashback> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().getCashback(utils.access_token,utils.bill_id,utils.profileId);

        call.enqueue(new Callback<getCashback>() {
            @Override
            public void onResponse(Call<getCashback> call, Response<getCashback> response) {
                if(response.code()==utils.RESPONSE_SUCCESS&&response.body()!=null){
                    cashback = response.body().getCashback_amount();

                    binding.scratchpopup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!scratched){
                            Dialog d = new Dialog(context);
                            d.setContentView(R.layout.scratchcardlayout);
                            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            TextView scratchamount = d.findViewById(R.id.scratch_cashback_amount);
                            scratchamount.setText("Rs. "+cashback);
                            d.show();

                            com.mediustechnologies.payemi.activities.scratchCard.ui.ScratchCardLayout card = d.findViewById(R.id.scratchCard1);
                            card.setScratchListener(new ScratchListener() {
                                @Override
                                public void onScratchStarted() {

                                }

                                @Override
                                public void onScratchProgress(@NonNull ScratchCardLayout scratchCardLayout, int atLeastScratchedPercent) {
                                    Log.d("tag", "onScratchProgress: "+atLeastScratchedPercent);
                                    if(atLeastScratchedPercent>8){
                                        Log.d("tag","scratched");
                                        scratched = true;
                                        redeem();
                                    }
                                }

                                @Override
                                public void onScratchComplete() {

                                }
                            });


                        }else{
                                Dialog d = new Dialog(context);
                                d.setContentView(R.layout.already_redeemed_scratchcard);
                                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                TextView scratchamount = d.findViewById(R.id.scratch_cashback_amount);
                                scratchamount.setText("Rs. "+cashback);
                                d.show();
                            }
                        }
                    });

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

        String token = utils.access_token;
        Call<RedeemScratchCard> call = new RetrofitClient().getInstance(context, urlconstants.AuthURL).getApi().redeemscratch(token,profile_id,bill_id);

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
//        binding.share.setVisibility(View.VISIBLE);
        scratched = false;
        bill_id = getIntent().getStringExtra("bill_id");
        profile_id = getIntent().getStringExtra("profile_id");
        binding.crossButton.setOnClickListener(view -> nextScreen());
        binding.share.setOnClickListener(View -> verifyStoragePermission(PaymentSuccessful.this));
    }


    private void takeScreenShot(View view) {

        //This is used to provide file name with Date a format
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        //It will make sure to store file to given below Directory and If the file Directory dosen't exist then it will create it.
        try {
            File mainDir = new File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }

            //Providing file name along with Bitmap to capture screenview
            String path = mainDir + "/" + "PayEMI" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            //This logic is used to save file at given location with the given filename and compress the Image Quality.
            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            //Create New Method to take ScreenShot with the imageFile.
            shareScreenShot(imageFile);
        } catch (IOException e) {
           Log.e("tag","Exception in taking screen shot."+e.toString());
        }
    }

    //Share ScreenShot
    private void shareScreenShot(File imageFile) {

        //Using sub-class of Content provider
        Uri uri = FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + "." + getLocalClassName() + ".provider",
                imageFile);

        //Explicit intent
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Pay EMI and earn cashback.");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        //It will show the application which are available to share Image; else Toast message will throw.
        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    //Permissions Check

    private  void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }else{
            takeScreenShot(getWindow().getDecorView());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextScreen();
    }

    private void nextScreen(){
        Intent i = new Intent(context,DashBoard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}