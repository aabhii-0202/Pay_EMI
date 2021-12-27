package com.mediustechnologies.payemi.adapters

import com.mediustechnologies.payemi.Models.emiListItem
import androidx.recyclerview.widget.RecyclerView
import com.mediustechnologies.payemi.adapters.emiListItemAdapter.viewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.mediustechnologies.payemi.R
import android.widget.TextView
import com.mediustechnologies.payemi.helper.MyProgressBar

class emiListItemAdapter(private val emiList: List<emiListItem>) :
    RecyclerView.Adapter<viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.emi_list_item, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val icon = emiList[position].icon
        val EMIAmount = emiList[position].emiAmount
        val BankName = emiList[position].bank_Name
        val LoanName = emiList[position].loan_Name
        val PaidAmount = emiList[position].paid_Amount
        val TotalAmount = emiList[position].total_Amount
        val progress = emiList[position].progress
        holder[icon, EMIAmount, BankName, LoanName, PaidAmount, TotalAmount] = progress
    }

    override fun getItemCount(): Int {
        return emiList.size
    }

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon: ImageView
        private val emiamount: TextView
        private val bankname: TextView
        private val loanname: TextView
        private val paidamount: TextView
        private val totalamount: TextView
        private val progressBar: MyProgressBar
        operator fun set(
            img: Int,
            emiAmount: String?,
            Bank_Name: String?,
            Loan_Name: String?,
            Paid_Amount: String?,
            Total_Amount: String?,
            progress: Int
        ) {
            icon.setImageResource(img)
            emiamount.text = emiAmount
            bankname.text = Bank_Name
            loanname.text = Loan_Name
            paidamount.text = Paid_Amount
            totalamount.text = Total_Amount
            progressBar.progress = progress
        }

        init {
            icon = view.findViewById(R.id.banklogo)
            emiamount = view.findViewById(R.id.emiAmount)
            bankname = view.findViewById(R.id.FinancersName)
            loanname = view.findViewById(R.id.loansname)
            paidamount = view.findViewById(R.id.paidamount)
            totalamount = view.findViewById(R.id.total_Loan)
            progressBar = view.findViewById(R.id.emiProgressbar)
        }
    }
}