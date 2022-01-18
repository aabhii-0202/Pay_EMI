package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mediustechnologies.payemi.recyclerItems.emiListItem;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.helper.MyProgressBar;

import java.util.List;

public class emiListItemAdapter extends RecyclerView.Adapter<emiListItemAdapter.viewHolder> {

    private List<emiListItem> emiList;
    private onItemClicked mListner;
    private onButtonClickeListner btnListner;
    private onAddMissingListner missingListner;

    public interface onAddMissingListner{
        void onMissingClick(int pos);
    }
    public interface onItemClicked{
        void onItemClick(int position);
    }
    public interface onButtonClickeListner{
        void onButtonClick(int pos);
    }

    public void setOnMissingClickLIstner(onAddMissingListner lIstner){
        missingListner = lIstner;
    }

    public void setOnButtonClickListner(onButtonClickeListner listner){
        btnListner = listner;
    }

    public void setOnItemClickListner(onItemClicked listner){
        mListner = listner;
    }

    public emiListItemAdapter(List<emiListItem> emiList){this.emiList = emiList;}


    @NonNull
    @Override
    public emiListItemAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emi_list_item,parent,false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull emiListItemAdapter.viewHolder holder, int position) {
        String icon = emiList.get(position).getIcon();
        String EMIAmount = emiList.get(position).getEmiAmount();
        String BankName = emiList.get(position).getBank_Name();
        String LoanName = emiList.get(position).getLoan_Name();
        String PaidAmount = emiList.get(position).getPaid_Amount();
        String TotalAmount = emiList.get(position).getTotal_Amount();

        holder.set(icon,EMIAmount,BankName,LoanName,PaidAmount,TotalAmount);

    }

    @Override
    public int getItemCount() {
        return emiList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView emiamount,bankname,loanname,paidamount,totalamount,addmissing;
        private MyProgressBar progressBar;

        public viewHolder(@NonNull View view){
            super(view);
            icon = view.findViewById(R.id.banklogo);
            emiamount= view.findViewById(R.id.emiAmount);
            bankname= view.findViewById(R.id.FinancersName);
            loanname= view.findViewById(R.id.loansname);
            paidamount= view.findViewById(R.id.paidamount);
            totalamount= view.findViewById(R.id.total_Loan);
            progressBar = view.findViewById(R.id.emiProgressbar);
            addmissing = view.findViewById(R.id.addmissinginfo);


            itemView.findViewById(R.id.emilistpay).setOnClickListener(new View.OnClickListener() {
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListner!=null){
                        int pos = getAbsoluteAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            mListner.onItemClick(pos);
                        }
                    }
                }
            });

            itemView.findViewById(R.id.addmissinginfo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(missingListner!=null){
                        int position = getAbsoluteAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            missingListner.onMissingClick(position);
                        }
                    }
                }
            });

        }

        public void set(String img,String emiAmount,String Bank_Name,String Loan_Name,String Paid_Amount,String Total_Amount){
//            img ="";


            Glide.with(icon).load(img).into(icon);
            emiamount.setText(emiAmount);
            bankname.setText(Bank_Name);
            loanname.setText(Loan_Name);
            paidamount.setText(Paid_Amount);


            try{
                double total = Double.parseDouble(Total_Amount);
                double paied = Double.parseDouble(Paid_Amount);
                int progress = (int) ((total-paied)/total);
                progressBar.setProgress(progress);
            }catch (Exception e){
                progressBar.setVisibility(View.GONE);
                addmissing.setVisibility(View.VISIBLE);
            }

            String t = formatinword(Total_Amount);
            totalamount.setText(t);

        }

        private String formatinword(String total_amount) {
            String ans = "₹";
            try{
                int length = total_amount.length();
                if(length>7){
                    ans += total_amount.substring(0,length-7)+"."+total_amount.substring(length-7,length-6)+"CR";
                }
                else if(length>5){
                    ans += total_amount.substring(0,length-5)+"."+total_amount.substring(length-5,length-4)+"L";
                }else if(length>3){
                    ans += total_amount.substring(0,length-3)+"."+total_amount.substring(length-3,length-2)+"K";
                }
                else ans += total_amount;
            }
            catch (Exception e){
                return "null";
            }
            return ans;
        }


    }
}
