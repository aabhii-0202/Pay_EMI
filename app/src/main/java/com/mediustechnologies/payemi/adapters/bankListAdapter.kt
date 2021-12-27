package com.mediustechnologies.payemi.adapters

import com.mediustechnologies.payemi.Models.bankListItem
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.mediustechnologies.payemi.R
import android.widget.TextView

class bankListAdapter(private val bankListItem: List<bankListItem>) :
    RecyclerView.Adapter<bankListAdapter.ViewHolder>() {
    private var mListner: onItemClicked? = null

    interface onItemClicked {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(listner: onItemClicked?) {
        mListner = listner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bank_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageRes = bankListItem[position].bank_Logo
        val name = bankListItem[position].bank_Name
        holder.setData(imageRes, name)
    }

    override fun getItemCount(): Int {
        return bankListItem.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logo: ImageView
        private val Bank_name: TextView
        fun setData(imageRes: Int, name: String?) {
            logo.setImageResource(imageRes)
            Bank_name.text = name
        }

        init {
            logo = itemView.findViewById(R.id.bankLogo)
            Bank_name = itemView.findViewById(R.id.Bank_Name)
            itemView.setOnClickListener {
                if (mListner != null) {
                    val pos = absoluteAdapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        mListner!!.onItemClick(pos)
                    }
                }
            }
        }
    }
}