package com.mediustechnologies.payemi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mediustechnologies.payemi.ApiResponse.verifyOTPresponse;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity;
import com.mediustechnologies.payemi.helper.RetrofitClient;
import com.mediustechnologies.payemi.commons.urlconstants;
import com.mediustechnologies.payemi.databinding.ActivityComplaintRegistrationBinding;
import com.mediustechnologies.payemi.ApiResponse.sendOTPResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act12complaintRegistration extends BaseAppCompatActivity {
    private ActivityComplaintRegistrationBinding binding;
    private final Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComplaintRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){













    }
}