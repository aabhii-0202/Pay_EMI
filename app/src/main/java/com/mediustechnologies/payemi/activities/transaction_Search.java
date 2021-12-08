package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.mediustechnologies.payemi.commons.DatePickerFragment;
import com.mediustechnologies.payemi.databinding.ActivityTaransactionSearchBinding;

public class transaction_Search extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ActivityTaransactionSearchBinding binding;
    private boolean from;

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

        binding.search.setOnClickListener(view -> binding.details.setVisibility(View.VISIBLE));

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