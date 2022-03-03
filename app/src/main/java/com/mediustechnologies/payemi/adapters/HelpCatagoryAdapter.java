package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.HelpCatagoryItemBinding;
import com.mediustechnologies.payemi.recyclerItems.HelpCatagoryList;

import java.util.List;

public class HelpCatagoryAdapter extends RecyclerView.Adapter<HelpCatagoryAdapter.viewHolder> {

    private List<HelpCatagoryList> catlist;
    private OnItemClickListner listner;

    public interface OnItemClickListner{
        void OnItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        this.listner= listner;
    }




    public HelpCatagoryAdapter(List<HelpCatagoryList> catlist){
        this.catlist = catlist;
    }

    @NonNull
    @Override
    public HelpCatagoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_catagory_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpCatagoryAdapter.viewHolder holder, int position) {

        String title = catlist.get(position).getTitle();

        holder.set(title);
    }

    @Override
    public int getItemCount() {
        return catlist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        HelpCatagoryItemBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = HelpCatagoryItemBinding.bind(itemView);
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
            binding.catagoryname.setText(title);
        }
    }
}
