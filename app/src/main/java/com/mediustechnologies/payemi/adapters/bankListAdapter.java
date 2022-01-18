package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.ApiResponse.getAllBanks;
import com.mediustechnologies.payemi.R;

import java.util.List;

public class bankListAdapter extends RecyclerView.Adapter<bankListAdapter.ViewHolder> {

    private List<getAllBanks> bankListItem;
    private onItemClicked mListner;


    public interface onItemClicked{
        void onItemClick(int position);
    }

    public void setOnItemClickListner(onItemClicked listner){
        mListner = listner;
    }
    public bankListAdapter(List<getAllBanks> bankListItem){
        this.bankListItem=bankListItem;
    }



    @NonNull
    @Override
    public bankListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bankListAdapter.ViewHolder holder, int position) {
        String imageRes = bankListItem.get(position).getBank_logo_url();
        String name = bankListItem.get(position).getBank_name();
        holder.setData(imageRes,name);

    }



    @Override
    public int getItemCount() {
        return bankListItem.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView logo;
        private TextView Bank_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            logo = itemView.findViewById(R.id.bankLogo);
            Bank_name = itemView.findViewById(R.id.Bank_Name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner!=null){
                        int pos = getAbsoluteAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            mListner.onItemClick(pos);
                        }
                    }
                }
            });


        }

        public void setData(String url, String name) {

            Glide.with(logo).load(url).into(logo);
            Bank_name.setText(name);

        }
    }
}
