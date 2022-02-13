package com.mediustechnologies.payemi.activities.DashBoardclasses;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.mediustechnologies.payemi.databinding.ComplaintRegistrationFrragmentBinding;
import com.mediustechnologies.payemi.databinding.ComplaintTrackingFragmentBinding;

public class ComplaintTrackingFragment extends Fragment {

    ComplaintTrackingFragmentBinding binding;
    private Context context ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ComplaintTrackingFragmentBinding.inflate(getLayoutInflater());
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


        //todo on create


    }
}
