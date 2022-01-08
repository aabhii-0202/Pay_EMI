package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.FetchBillRecyclerItem2Binding;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class fetchBillAdapter2 extends RecyclerView.Adapter<fetchBillAdapter2.viewHolder> {

    private LinkedHashMap<String,String> billItems;
    private List<String> keys;
    public fetchBillAdapter2(LinkedHashMap <String,String> billItems){
        this.billItems = billItems;
        keys = new ArrayList<>();
        for (Map.Entry<String, String> entry: billItems.entrySet()){
            String k = entry.getKey();
            keys.add(k);
        }
    }


    @NonNull
    @Override
    public fetchBillAdapter2.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fetch_bill_recycler_item2,parent,false);
        return new fetchBillAdapter2.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull fetchBillAdapter2.viewHolder holder, int position) {
        String lable = keys.get(position);
        String value = billItems.get(lable);

        holder.setData(lable,value);
    }

    @Override
    public int getItemCount() {
        return billItems.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        FetchBillRecyclerItem2Binding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FetchBillRecyclerItem2Binding.bind(itemView);
        }

        public void setData(String title, String value) {
            title = convert(title);

            binding.title.setText(title);
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
