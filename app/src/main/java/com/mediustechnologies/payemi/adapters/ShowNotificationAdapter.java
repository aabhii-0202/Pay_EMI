package com.mediustechnologies.payemi.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.DTO.ShowNotificationDTO;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.NotificaionItemBinding;

import java.util.List;

public class ShowNotificationAdapter extends RecyclerView.Adapter<ShowNotificationAdapter.vh> {

    List<ShowNotificationDTO> list;
    private onButtonClickeListner btnListner;
    public interface onButtonClickeListner{
        void onButtonClick(int pos);
    }
    public void setOnButtonClickListner(onButtonClickeListner listner){
        btnListner = listner;
    }

    public ShowNotificationAdapter(List<ShowNotificationDTO> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ShowNotificationAdapter.vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificaion_item,parent,false);
        return new ShowNotificationAdapter.vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowNotificationAdapter.vh holder, int position) {
        String message = list.get(position).getNotification_name();
        String time = list.get(position).getNotification_time();
        String logo = list.get(position).getNotification_logo();
        String type = list.get(position).getNotification_type();
        boolean status = list.get(position).isNotification_status();
        boolean action = list.get(position).isNotification_action();
        boolean seen = list.get(position).isNotification_is_seen();


        holder.setdata(logo,message,time,type,status,action,seen);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class vh extends RecyclerView.ViewHolder {
        NotificaionItemBinding binding;
        public vh(@NonNull View itemView) {
            super(itemView);
            binding = NotificaionItemBinding.bind(itemView);
            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(btnListner!=null){
                        int position = getAbsoluteAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            btnListner.onButtonClick(position);
                        }
                    }
                }
            });

        }

        public void setdata(String logo, String message, String time, String type, boolean status, boolean action, boolean seen) {

            if(status) {
                binding.notificationText.setText(message);
                Glide.with(binding.image).load(logo).into(binding.image);
                binding.notificationTimeText.setText(format(time));
                binding.button.setVisibility(View.GONE);

                if(!seen){
                    binding.card.setCardBackgroundColor(Color.parseColor("#eceaf3"));
                }

                if(action){
                    binding.button.setVisibility(View.VISIBLE);
                    if(type!=null){
                        type = type.toLowerCase();
                        if(type.contains("transaction")){
                            binding.button.setText("Pay Now & Earn Cashback");
                        }else if(type.contains("cash")){
                            binding.button.setText("Redeem Now");
                        }else{
                            binding.button.setText("-------");
                        }
                    }
                }

            }

        }

        private String format(String s){

            //  2022-02-02T18:00:00Z

            String date = s.substring(0,10);
            String time = s.substring(11,16);

            String ans = date+" | "+time;


            return ans;
        }
    }
}
