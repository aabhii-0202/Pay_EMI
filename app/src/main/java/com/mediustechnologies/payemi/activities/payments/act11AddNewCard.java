package com.mediustechnologies.payemi.activities.payments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.mediustechnologies.payemi.databinding.ActivityAct11AddNewCardBinding;
import com.mediustechnologies.payemi.helper.DatePickerFragment;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.Calendar;

public class act11AddNewCard extends AppCompatActivity {

    private ActivityAct11AddNewCardBinding binding;
    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAct11AddNewCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



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
//            if(binding.securelysave.isChecked()){
//                savecard();
//            }
            verify(cardno,cardholdername,expiry,cvv);
        }
    }

    private void verify(String cardno, String cardholdername, String expiry, String cvv) {




    }


}