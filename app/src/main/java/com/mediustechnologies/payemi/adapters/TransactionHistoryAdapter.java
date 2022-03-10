package com.mediustechnologies.payemi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.recyclerItems.transaction_chat;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.databinding.TransactionPagePaymentItemBinding;
import com.mediustechnologies.payemi.databinding.TransactionPageScratchcardItemBinding;

import java.util.ArrayList;

public class TransactionHistoryAdapter extends RecyclerView.Adapter{

    private onItemClick mListner;
    private Context context;
    private final ArrayList<transaction_chat> chatlist;



    final int ITEM_SCRATCH =1;
    final int ITEM_PAYMENT=2;

    public interface onItemClick{
        void onItemClick (int postion);
    }
    public void setOnItemClickListner(TransactionHistoryAdapter.onItemClick listner){
        mListner = listner;
    }



    public TransactionHistoryAdapter(Context context, ArrayList<transaction_chat> chatlist){
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

        if(chatlist.get(position).getType().equals("transaction")) return ITEM_PAYMENT;
        return ITEM_SCRATCH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String name = chatlist.get(position).getName();
        String loanname = chatlist.get(position).getLoanname();
        String date = chatlist.get(position).getDate();
        String status = chatlist.get(position).getStatus();
        String amount = chatlist.get(position).getAmount();
        String type = chatlist.get(position).getType();
        String redeemed = chatlist.get(position).getRedeemed();


        if(type.equals("transaction")){
            paymentViewHolder vh = (paymentViewHolder) holder;
            vh.binding.cardbankname.setText(name);
            vh.binding.cardloanname.setText(loanname);
            vh.binding.cardamount.setText(amount);
            vh.binding.statusanddate.setText(date);
            if(status.equalsIgnoreCase("successful")||status.equalsIgnoreCase("su")||status.equalsIgnoreCase("success")){
                vh.binding.statusanddate.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_tick, 0, 0, 0);
            }
            else if(status.equalsIgnoreCase("failed")||status.equalsIgnoreCase("fa")){
                vh.binding.statusanddate.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_cross, 0, 0, 0);
            }
        }
        else if(type.equals("cashback")){

            rewardViewHolder vh = (rewardViewHolder) holder;
            if(redeemed.equals("false")){
                vh.binding.titletext.setText("Scratch Now");
                vh.binding.subtitle.setText("Earn a reward!");
            }
            else {
                vh.binding.titletext.setText(amount);
                vh.binding.subtitle.setText("You earned a reward!");
            }

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
