package com.mediustechnologies.payemi.activities;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.mediustechnologies.payemi.activities.login.OnBording;
import com.mediustechnologies.payemi.databinding.ActivityTaransactionSearchBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.DatePickerFragment;

public class act20transaction_Search extends BaseAppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ActivityTaransactionSearchBinding binding;
    private boolean from;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTaransactionSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();


    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
        String s = date+"-"+month+"-"+year;
        if(from){
            binding.fromtext.setText(s);
        }else {
            binding.totext.setText(s);
        }

    }

    private void init(){

        binding.details.setVisibility(View.VISIBLE);

        binding.radioTransaction.setOnClickListener(view -> {
            if(binding.radioTransaction.isChecked()){
                binding.mobileNumberSelected.setVisibility(View.GONE);
                binding.transactionIdselected.setVisibility(View.VISIBLE);
                binding.details.setVisibility(View.GONE);
            }
        });

        binding.radioMobile.setOnClickListener(view -> {
            if(binding.radioMobile.isChecked()){
                binding.mobileNumberSelected.setVisibility(View.VISIBLE);
                binding.transactionIdselected.setVisibility(View.GONE);
                binding.details.setVisibility(View.GONE);
            }
        });

        binding.search.setOnClickListener(view -> startActivity(new Intent(context, OnBording.class)));

        binding.layoutfrom.setOnClickListener(View ->{
            from = true;
            DialogFragment datepicker = new DatePickerFragment();
            datepicker.show(getSupportFragmentManager(),"date picker");
        });

        binding.layoutto.setOnClickListener(view -> {
            from = false;
            DialogFragment datepicker = new DatePickerFragment();
            datepicker.show(getSupportFragmentManager(),"date picker");
        });

    }
}