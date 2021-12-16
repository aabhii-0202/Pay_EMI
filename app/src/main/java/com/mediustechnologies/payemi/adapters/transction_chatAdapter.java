package com.mediustechnologies.payemi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.Models.transaction_chat;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.TransactionPagePaymentItemBinding;
import com.mediustechnologies.payemi.databinding.TransactionPageScratchcardItemBinding;

import java.util.ArrayList;

public class transction_chatAdapter extends RecyclerView.Adapter{

    private Context context;
    private ArrayList<transaction_chat> chatlist;
    final int ITEM_SCRATCH =1;
    final int ITEM_PAYMENT=2;

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
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.transaction_page_payment_item,parent,false);
            return new paymentViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        transaction_chat chat = chatlist.get(position);
        if (chat.getStatus_date()==null){
            return ITEM_SCRATCH;
        }else {
            return ITEM_PAYMENT;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String name = chatlist.get(position).getName();
        String loanname = chatlist.get(position).getLoan_name();
        String status = chatlist.get(position).getStatus_date();
        String amount = chatlist.get(position).getAmount();

        if(status==null){
            rewardViewHolder vh = (rewardViewHolder) holder;
            vh.binding.titletext.setText(name);
            vh.binding.subtitle.setText(loanname);
        }else {
            paymentViewHolder vh = (paymentViewHolder) holder;
            vh.binding.cardamount.setText(amount);
            vh.binding.cardbankname.setText(name);
            vh.binding.cardloanname.setText(loanname);
            vh.binding.statusanddate.setText(status);
        }

    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public class rewardViewHolder extends RecyclerView.ViewHolder{

        TransactionPageScratchcardItemBinding binding;

        public rewardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TransactionPageScratchcardItemBinding.bind(itemView);
        }
    }

    public class paymentViewHolder extends RecyclerView.ViewHolder{

        TransactionPagePaymentItemBinding binding;

        public paymentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TransactionPagePaymentItemBinding.bind(itemView);
        }
    }

}
