package com.mediustechnologies.payemi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediustechnologies.payemi.Models.emiListItem;
import com.mediustechnologies.payemi.R;
import com.mediustechnologies.payemi.helper.MyProgressBar;

import java.util.List;

public class emiListItemAdapter extends RecyclerView.Adapter<emiListItemAdapter.viewHolder> {

    private List<emiListItem> emiList;


    public emiListItemAdapter(List<emiListItem> emiList){this.emiList = emiList;}


    @NonNull
    @Override
    public emiListItemAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emi_list_item,parent,false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull emiListItemAdapter.viewHolder holder, int position) {
        int icon = emiList.get(position).getIcon();
        String EMIAmount = emiList.get(position).getEmiAmount();
        String BankName = emiList.get(position).getBank_Name();
        String LoanName = emiList.get(position).getLoan_Name();
        String PaidAmount = emiList.get(position).getPaid_Amount();
        String TotalAmount = emiList.get(position).getTotal_Amount();
        int progress = emiList.get(position).getProgress();


        holder.set(icon,EMIAmount,BankName,LoanName,PaidAmount,TotalAmount,progress);

    }

    @Override
    public int getItemCount() {
        return emiList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView emiamount,bankname,loanname,paidamount,totalamount;
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

        }

        public void set(int img,String emiAmount,String Bank_Name,String Loan_Name,String Paid_Amount,String Total_Amount,int progress){
            icon.setImageResource(img);
            emiamount.setText(emiAmount);
            bankname.setText(Bank_Name);
            loanname.setText(Loan_Name);
            paidamount.setText(Paid_Amount);
            totalamount.setText(Total_Amount);
            progressBar.setProgress(progress);



        }




    }
}
