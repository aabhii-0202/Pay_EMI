package com.mediustechnologies.payemi.adapters;

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
        String message = list.get(position).getMsg();
        String time = list.get(position).getTime();
        String logo = list.get(position).getBank_logo();


        holder.setdata(logo,message,time);
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

        public void setdata(String logo, String message, String time) {
            binding.notificationText.setText(message);
            Glide.with(binding.image).load(logo).into(binding.image);
            binding.notificationTimeText.setText(format(time));
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
