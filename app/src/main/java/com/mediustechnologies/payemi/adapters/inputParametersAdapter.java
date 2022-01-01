package com.mediustechnologies.payemi.adapters;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.InputParamsRecyclerItemBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class inputParametersAdapter extends RecyclerView.Adapter<inputParametersAdapter.viewHolder> {

    private HashMap<String,String> inputList;
    private List<String> keys;
    public inputParametersAdapter(HashMap<String,String> inputList) {
        this.inputList = inputList;

        keys = new ArrayList<>();
        for (String key : inputList.keySet()) keys.add(key);
    }



    @NonNull
    @Override
    public inputParametersAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.input_params_recycler_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull inputParametersAdapter.viewHolder holder, int position) {
        String title = keys.get(position);
        String type = inputList.get(title);

        holder.setData(title,type);
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        InputParamsRecyclerItemBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = InputParamsRecyclerItemBinding.bind(itemView);
        }

        public void setData(String title,String type) {
            binding.title.setText(title);
            binding.errorMessage2.setText("Please enter proper "+title);
            if(type.equals("NUMERIC")) binding.input.setInputType(InputType.TYPE_CLASS_NUMBER);

            else binding.input.setInputType(InputType.TYPE_CLASS_TEXT);

        }
    }
}
