package com.mediustechnologies.payemi.activities.payments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mediustechnologies.payemi.activities.PaymentSuccessful;
import com.mediustechnologies.payemi.adapters.NetBankingListAdapter;
import com.mediustechnologies.payemi.databinding.ActivityNetBankingBinding;
import com.mediustechnologies.payemi.recyclerItems.NetBankingListItem;
import com.razorpay.PaymentResultListener;
import com.razorpay.Razorpay;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NetBanking extends AppCompatActivity implements PaymentResultListener {

    private ActivityNetBankingBinding binding;
    private Context context = this;
    private Razorpay razorpay;
    private JSONObject payload;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNetBankingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        init();
        razorpay = new Razorpay(this);
        razorpay.changeApiKey("rzp_test_a9DQDSDQfs10TS");
        razorpay.setWebView(binding.paymentWebview);

        String results = getIntent().getStringExtra("feild");
        inflateLists(results);





    }

    private void recyclerview(ArrayList<String> banksCodesList, ArrayList<String> banksList){

        List<NetBankingListItem> banklist = new ArrayList<>();

        for(int i = 0;i<banksList.size();i++){
            banklist.add(new NetBankingListItem(banksList.get(i)));
        }


        RecyclerView recyclerView = binding.netbankinglist;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context   );
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        NetBankingListAdapter adapter = new NetBankingListAdapter(banklist);
        recyclerView.setAdapter(adapter);

        initsearchbar(adapter);
        adapter.setonitemclicklistner(new NetBankingListAdapter.onitemclicklistner() {
            @Override
            public void onitemclick(int position) {
                Toast.makeText(context, banklist.get(position).getName()+" Clicked", Toast.LENGTH_SHORT).show();
                submitNetbankingDetails(banksCodesList.get(position));
            }
        });

    }

    public void submitNetbankingDetails(String bankName) {

        try {
            payload = new JSONObject("{currency: 'INR'}");
            payload.put("amount", amount+"00");
            payload.put("contact", "9999999999");
            payload.put("email", "customer@name.com");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            payload.put("method", "netbanking");
            payload.put("bank", bankName);
            sendRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRequest() {
        razorpay.validateFields(payload, new Razorpay.ValidationListener() {
            @Override
            public void onValidationSuccess() {
                try {
                    binding.paymentWebview.setVisibility(View.VISIBLE);
                    binding.outerbox.setVisibility(View.VISIBLE);
                    razorpay.submit(payload, NetBanking.this);
                } catch (Exception e) {
                    Log.e("tag", "Net Bankin Exception: "+ e.toString());
                }
            }

            @Override
            public void onValidationError(Map<String, String> error) {
                Log.d("tag", "Validation failed: " + error.get("field") + " " + error.get("description"));
                Toast.makeText(context, "Validation: " + error.get("field") + " " + error.get("description"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inflateLists(String result) {

        ArrayList<String> banksCodesList = new ArrayList<>();
        ArrayList<String> banksList = new ArrayList<>();
        try {
            JSONObject paymentMethods = new JSONObject(result);
            JSONObject banksListJSON = paymentMethods.getJSONObject("netbanking");

            Iterator<String> itr1 = banksListJSON.keys();
            while (itr1.hasNext()) {
                String key = itr1.next();
                banksCodesList.add(key);
                try {
                    banksList.add(banksListJSON.getString(key));
                } catch (JSONException e) {
                    Log.d("Reading Banks List", "--->" + e.getMessage());
                }
            }
            recyclerview(banksCodesList,banksList);

        } catch (Exception e) {
            Log.e("tag", "Parcing results for netbanking" + e.getMessage());
        }
    }



    private void initsearchbar(NetBankingListAdapter adapter) {

        binding.searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = binding.searchbar.getText().toString();
                adapter.getFilter().filter(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    private void init(){
        binding.back.setOnClickListener(view -> finish());

        amount = getIntent().getStringExtra("amount");


    }

    @Override
    public void onPaymentSuccess(String s) {
        binding.paymentWebview.setVisibility(View.GONE);
        binding.outerbox.setVisibility(View.GONE);

//        Log.d("tag","Net Banking Payment Success");
        Intent i = new Intent(context, PaymentSuccessful.class);
        i.putExtra("billerName",getIntent().getStringExtra("billerName"));
        i.putExtra("bill_id",getIntent().getStringExtra("bill_id"));
        startActivity(i);
    }

    @Override
    public void onPaymentError(int i, String s) {
        binding.paymentWebview.setVisibility(View.GONE);
        binding.outerbox.setVisibility(View.GONE);
        Log.d("tag","Net Banking Payment Failed");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        razorpay.onActivityResult(requestCode,resultCode,data);
    }
}