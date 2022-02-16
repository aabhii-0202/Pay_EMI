package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.mediustechnologies.payemi.activities.login.OnBording;
import com.mediustechnologies.payemi.databinding.TransactionSearchFragmentBinding;
import com.mediustechnologies.payemi.helper.DatePickerFragment;

public class TransactionSearchFragment extends Fragment {

    TransactionSearchFragmentBinding binding;
    private Context context ;
    private boolean from;

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

    private void init(){


        binding.layoutfrom.setOnClickListener(View ->{
            from = true;
            DialogFragment datepicker = new DatePickerFragment();
            datepicker.show(getParentFragmentManager(),"date picker");
        });

        binding.layoutto.setOnClickListener(view -> {
            from = false;
            DialogFragment datepicker = new DatePickerFragment();
            datepicker.show(getParentFragmentManager(),"date picker");
        });

    }





}
