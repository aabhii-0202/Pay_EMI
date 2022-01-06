package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.FetchBillLayoutBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class fetchBillAdapter extends RecyclerView.Adapter<fetchBillAdapter.viewHolder> {

    private LinkedHashMap <String,String> billItems;
    private List<String> keys;
    public fetchBillAdapter(LinkedHashMap <String,String> billItems){
        this.billItems = billItems;
        keys = new ArrayList<>();
        for (Map.Entry<String, String> entry: billItems.entrySet()){
            String k = entry.getKey();
                keys.add(k);
        }
    }


    @NonNull
    @Override
    public fetchBillAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fetch_bill_layout,parent,false);
        return new fetchBillAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull fetchBillAdapter.viewHolder holder, int position) {
        String lable = keys.get(position);
        String value = billItems.get(lable);

        holder.setData(lable,value);



    }

    @Override
    public int getItemCount() {
        return billItems.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        FetchBillLayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FetchBillLayoutBinding.bind(itemView);
        }

        public void setData(String title,String value){

            title = convert(title);

            binding.label.setText(title);
            binding.value.setText(value);
        }

        private String convert(String s){
            String ans ="";
            if(s.length()>0){
                s = s.replace('_', ' ');
                s = s.trim();

                String temp = s.charAt(0)+"";
                ans += temp.toUpperCase()+s.substring(1);

            }

            return ans;
        }
    }
}
