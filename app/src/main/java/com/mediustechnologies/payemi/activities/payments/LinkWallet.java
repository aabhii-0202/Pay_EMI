package com.mediustechnologies.payemi.activities.payments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.adapters.WalletAdapter;
import com.mediustechnologies.payemi.adapters.bankListAdapter;
import com.mediustechnologies.payemi.databinding.ActivitySelectWallectBinding;
import com.mediustechnologies.payemi.recyclerItems.walletItem;
import com.razorpay.PaymentResultListener;
import com.razorpay.Razorpay;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LinkWallet extends AppCompatActivity implements PaymentResultListener {

    private ActivitySelectWallectBinding binding;
    private Context context = this;
    private Razorpay razorpay;
    private JSONObject payload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectWallectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();

        razorpay = new Razorpay(this);
        razorpay.setWebView(binding.paymentWebview);

        String results = getIntent().getStringExtra("feild");
        inflateLists(results);



    }

    private void inflateLists(String result) {
        try {
            JSONObject paymentMethods = new JSONObject(result);
            JSONObject walletListJSON = paymentMethods.getJSONObject("wallet");
            ArrayList<String> walletsList = new ArrayList<>();
            Iterator<String> itr2 = walletListJSON.keys();
            while (itr2.hasNext()) {
                String key = itr2.next();
                try {
                    if (walletListJSON.getBoolean(key)) {
                        walletsList.add(key);
                    }
                } catch (JSONException e) {
                    Log.d("Reading Wallets List", "" + e.getMessage());
                }
            }


            recyclerview(walletsList);

        } catch (Exception e) {
            Log.e("tag", "Parsing Result For Card " + e.toString());
        }
    }

    private void recyclerview(ArrayList<String> walletsList) {
        List<walletItem> walletItem = new ArrayList<>();

        for(int i=0;i<walletsList.size();i++){
            walletItem.add(new walletItem(R.drawable.paytmwallet,walletsList.get(i)));
        }

//        walletItem.add(new walletItem(R.drawable.paytmwallet,"Paytm"));
//        walletItem.add(new walletItem(R.drawable.amazonpay,"Amazon Pay"));
//        walletItem.add(new walletItem(R.drawable.mobiwik,"Mobiwik"));
//        walletItem.add(new walletItem(R.drawable.phonepewallet,"Phone Pe"));

        RecyclerView recyclerView = binding.walletlist;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        WalletAdapter adapter = new WalletAdapter(walletItem);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListner(new WalletAdapter.onItemClicked(){

            @Override
            public void onItemClick(int position) {

                submitWalletDetails(walletsList.get(position));

            }
        });





    }

    public void submitWalletDetails(String walletName) {


        try {
            payload = new JSONObject("{currency: 'INR'}");
            payload.put("amount", "100");
            payload.put("contact", "9999999999");
            payload.put("email", "customer@name.com");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            payload.put("method", "wallet");
            payload.put("wallet", walletName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendRequest();
    }

    private void sendRequest() {
        razorpay.validateFields(payload, new Razorpay.ValidationListener() {
            @Override
            public void onValidationSuccess() {
                try {
                    binding.paymentWebview.setVisibility(View.VISIBLE);
                    binding.outerbox.setVisibility(View.VISIBLE);
                    razorpay.submit(payload, LinkWallet.this);
                } catch (Exception e) {
                    Log.e("tag", "Exception: at wallet link "+e.toString());
                }
            }

            @Override
            public void onValidationError(Map<String, String> error) {
                Log.d("tag", "Validation failed: " + error.get("field") + " " + error.get("description"));
                Toast.makeText(context, "Validation: " + error.get("field") + " " + error.get("description"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        binding.back.setOnClickListener(view -> finish());

    }

    @Override
    public void onPaymentSuccess(String s) {
        binding.paymentWebview.setVisibility(View.GONE);
        binding.outerbox.setVisibility(View.GONE);

        Log.d("tag", "onPaymentSuccess: wallet payment successful");
    }

    @Override
    public void onPaymentError(int i, String s) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        razorpay.onActivityResult(requestCode,resultCode,data);
    }
}