package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.CatagoriesRecyclerItemBinding;

import java.util.ArrayList;


public class catagoryAdapter extends RecyclerView.Adapter<catagoryAdapter.ViewHolder> {

    private ArrayList<String> catagories ;
    private oncatagoryClick mListner;
    private int positionselected;

    public interface oncatagoryClick{
        void onCatagoryClick(int position);
    }

    public void setOnCatagoryClickListner(oncatagoryClick listner){
        mListner = listner;
    }

    public catagoryAdapter(ArrayList<String> catagories,int positionselected){
        this.positionselected=positionselected;
        this.catagories = catagories;
    }

    @NonNull
    @Override
    public catagoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.catagories_recycler_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull catagoryAdapter.ViewHolder holder, int position) {
        String name = catagories.get(position);

        if(positionselected==position)
            holder.binding.line.setVisibility(View.VISIBLE);
        holder.set(name);
    }

    @Override
    public int getItemCount() {
        return catagories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CatagoriesRecyclerItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CatagoriesRecyclerItemBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner!=null){
                        int pos = getAbsoluteAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            mListner.onCatagoryClick(pos);
                        }
                    }
                }
            });
        }

        public void set(String name) {
            binding.text.setText(name);
        }
    }
}
