package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.HelpSubcatagoryItemBinding;
import com.mediustechnologies.payemi.recyclerItems.HelpSubCatagoryItem;

import java.util.List;

public class HelpSubcatagoryAdapter extends RecyclerView.Adapter<HelpSubcatagoryAdapter.vh> {

    List<HelpSubCatagoryItem> list;
    private onSubCatClickListner listner;

    public interface onSubCatClickListner{
        void OnItemClick(int position);
    }
    public void setOnItemClick(onSubCatClickListner listner){
        this.listner = listner;
    }

    public HelpSubcatagoryAdapter(List<HelpSubCatagoryItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public HelpSubcatagoryAdapter.vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.help_subcatagory_item,parent,false);
        return new vh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpSubcatagoryAdapter.vh holder, int position) {
        String title = list.get(position).getTitle();
        holder.set(title);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class vh extends RecyclerView.ViewHolder {
        HelpSubcatagoryItemBinding binding;
        public vh(@NonNull View itemView) {
            super(itemView);
            binding = HelpSubcatagoryItemBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listner!=null){
                        int position  = getAbsoluteAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listner.OnItemClick(position);
                        }
                    }
                }
            });
        }

        public void set(String title) {
            binding.title.setText(title);
        }
    }
}
