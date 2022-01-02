package com.mediustechnologies.payemi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.recyclerItems.transaction_chat;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.TransactionPageDatelineBinding;
import com.mediustechnologies.payemi.databinding.TransactionPagePaymentItemBinding;
import com.mediustechnologies.payemi.databinding.TransactionPageScratchcardItemBinding;

import java.util.ArrayList;

public class transction_chatAdapter extends RecyclerView.Adapter{




    private onItemClick mListner;


    private Context context;
    private ArrayList<transaction_chat> chatlist;



    final int ITEM_SCRATCH =1;
    final int ITEM_PAYMENT=2;
    final int ITEM_DATE =3;

    public interface onItemClick{
        void onItemClick (int postion);
    }

    public void setOnItemClickListner(transction_chatAdapter.onItemClick listner){
        mListner = listner;
    }



    public transction_chatAdapter(Context context, ArrayList<transaction_chat> chatlist){
        this.chatlist=chatlist;
        this.context=context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SCRATCH){
            View view = LayoutInflater.from(context).inflate(R.layout.transaction_page_scratchcard_item,parent,false);
            return new rewardViewHolder(view);
        }

        else if(viewType==ITEM_DATE){
            View view  = LayoutInflater.from(context).inflate(R.layout.transaction_page_dateline,parent,false);
            return new dateLineViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.transaction_page_payment_item,parent,false);
            return new paymentViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        transaction_chat chat = chatlist.get(position);

        if(chat.getDateline()!=null) return ITEM_DATE;

        if (chat.getStatus_date()==null)return ITEM_SCRATCH;

        return ITEM_PAYMENT;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String name = chatlist.get(position).getName();
        String loanname = chatlist.get(position).getLoan_name();
        String status = chatlist.get(position).getStatus_date();
        String amount = chatlist.get(position).getAmount();
        String dateline = chatlist.get(position).getDateline();


        if(dateline==null) {
            if (status == null) {
                rewardViewHolder vh = (rewardViewHolder) holder;
                vh.binding.titletext.setText(name);
                vh.binding.subtitle.setText(loanname);
            } else {
                paymentViewHolder vh = (paymentViewHolder) holder;
                vh.binding.cardamount.setText(amount);
                vh.binding.cardbankname.setText(name);
                vh.binding.cardloanname.setText(loanname);
                vh.binding.statusanddate.setText(status);
            }
        }
        else {
            dateLineViewHolder vh = (dateLineViewHolder) holder;
            vh.binding.date.setText(dateline);
        }

    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public class dateLineViewHolder extends RecyclerView.ViewHolder{
        TransactionPageDatelineBinding binding;

        public dateLineViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TransactionPageDatelineBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner!=null){
                        int position = getAbsoluteAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }


    public class rewardViewHolder extends RecyclerView.ViewHolder{

        TransactionPageScratchcardItemBinding binding;

        public rewardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TransactionPageScratchcardItemBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner!=null){
                        int position = getAbsoluteAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public class paymentViewHolder extends RecyclerView.ViewHolder{

        TransactionPagePaymentItemBinding binding;

        public paymentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TransactionPagePaymentItemBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner!=null){
                        int position = getAbsoluteAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
