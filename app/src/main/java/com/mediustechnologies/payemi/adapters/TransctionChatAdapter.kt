package com.mediustechnologies.payemi.adapters

import android.content.Context
import com.mediustechnologies.payemi.Models.transaction_chat
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.mediustechnologies.payemi.R
import com.mediustechnologies.payemi.databinding.TransactionPageDatelineBinding
import com.mediustechnologies.payemi.databinding.TransactionPagePaymentItemBinding
import com.mediustechnologies.payemi.databinding.TransactionPageScratchcardItemBinding
import java.util.ArrayList

class TransctionChatAdapter(
    private val context: Context,
    private val chatlist: ArrayList<transaction_chat>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_SCRATCH = 1
    val ITEM_PAYMENT = 2
    val ITEM_DATE = 3
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SCRATCH) {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.transaction_page_scratchcard_item, parent, false)
            rewardViewHolder(view)
        } else if (viewType == ITEM_DATE) {
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.transaction_page_dateline, parent, false)
            dateLineViewHolder(view)
        } else {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.transaction_page_payment_item, parent, false)
            paymentViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chat = chatlist[position]
        if (chat.dateline != null) return ITEM_DATE
        return if (chat.status_date == null) ITEM_SCRATCH else ITEM_PAYMENT
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val name = chatlist[position].name
        val loanname = chatlist[position].loan_name
        val status = chatlist[position].status_date
        val amount = chatlist[position].amount
        val dateline = chatlist[position].dateline
        if (dateline == null) {
            if (status == null) {
                val vh = holder as rewardViewHolder
                vh.binding.titletext.text = name
                vh.binding.subtitle.text = loanname
            } else {
                val vh = holder as paymentViewHolder
                vh.binding.cardamount.text = amount
                vh.binding.cardbankname.text = name
                vh.binding.cardloanname.text = loanname
                vh.binding.statusanddate.text = status
            }
        } else {
            val vh = holder as dateLineViewHolder
            vh.binding.date.text = dateline
        }
    }

    override fun getItemCount(): Int {
        return chatlist.size
    }

    inner class dateLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: TransactionPageDatelineBinding

        init {
            binding = TransactionPageDatelineBinding.bind(itemView)
        }
    }

    inner class rewardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: TransactionPageScratchcardItemBinding

        init {
            binding = TransactionPageScratchcardItemBinding.bind(itemView)
        }
    }

    inner class paymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: TransactionPagePaymentItemBinding

        init {
            binding = TransactionPagePaymentItemBinding.bind(itemView)
        }
    }
}