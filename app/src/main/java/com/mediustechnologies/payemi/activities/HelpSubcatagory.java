package com.mediustechnologies.payemi.activities;


import android.content.Context;
import android.os.Bundle;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.ActivityHelpSubcatagoryBinding;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;

public class HelpSubcatagory extends BaseAppCompatActivity {

    private final Context context = this;
    private ActivityHelpSubcatagoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpSubcatagoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}