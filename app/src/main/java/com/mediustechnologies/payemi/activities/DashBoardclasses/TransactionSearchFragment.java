package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.content.Context;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mediustechnologies.payemi.databinding.TransactionSearchFragmentBinding;

import java.util.Calendar;


public class TransactionSearchFragment extends Fragment {

    TransactionSearchFragmentBinding binding;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TransactionSearchFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init();
        //todo on create


    }

    private void init() {


        binding.layoutfrom.setOnClickListener(View -> {
            datePicker();
        });

        binding.layoutto.setOnClickListener(view -> {
            datePicker();
        });

    }

    private void datePicker() {

        MaterialDatePicker.Builder<Pair<Long,Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Date Range Picker");

        final MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        materialDatePicker.show(getParentFragmentManager(), "Date_Picker");


        materialDatePicker.addOnPositiveButtonClickListener(selection -> {


            String s = materialDatePicker.getHeaderText();
            Log.d("date","Date range:  "+s);







            if(s.length()>21){

                //Dec 17, 2021 – Jan 24, 2022

                String month = s.substring(0,3);
                int monthFrom = getMonth(month);

                month = s.substring(15,18);
                int monthTo = getMonth(month);

                String yearFrom = s.substring(8,12);
                String yearTo = s.substring(22);

                String dateFrom = s.substring(4,6);
                String dateTo = s.substring(19,21);

                String From = yearFrom+"-"+monthFrom+"-"+dateFrom;
                String To = yearTo+"-"+monthTo+"-"+dateTo;

                System.out.println(From);
                System.out.println(To);

                binding.fromtext.setText(From);
                binding.totext.setText(To);

            }else if(s.length()==20){



            }else{
                //Feb 17 – Feb 24

                String month = s.substring(0,3);
                int monthFrom = getMonth(month);

                month = s.substring(9,12);
                int monthTo = getMonth(month);

                String yearFrom = "2022";
                String yearTo = "2022";

                String dateFrom = s.substring(4,6);
                String dateTo = s.substring(12);

                String From = yearFrom+"-"+monthFrom+"-"+dateFrom;
                String To = yearTo+"-"+monthTo+"-"+dateTo;

                binding.fromtext.setText(From);
                binding.totext.setText(To);
            }




        });


    }

    private int getMonth(String month){
        String[] cal = {
          "Jan", "Feb", "Mar" , "Apr" , "May", "Jun", "Jul" , "Aug", "Sep", "Oct", "Nov", "Dec"
        };

        int ans = -1;
        for(int i=0;i<cal.length;i++){
            if(month.equalsIgnoreCase(cal[i])){
                ans = (i+1);
                break;
            }
        }

        return ans;
    }


}
