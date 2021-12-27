package com.mediustechnologies.payemi.adapters

import com.mediustechnologies.payemi.Models.bankSubItem
import androidx.recyclerview.widget.RecyclerView
import com.mediustechnologies.payemi.adapters.bankSublistAdapter.Viewholder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.mediustechnologies.payemi.R

class bankSublistAdapter(private val bankSubList: List<bankSubItem>) :
    RecyclerView.Adapter<Viewholder>() {
    private var mListner: bankListAdapter.onItemClicked? = null

    interface onItemClicked {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(listner: bankListAdapter.onItemClicked?) {
        mListner = listner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bank_sublist_items, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val imageRes = bankSubList[position].image
        holder.setData(imageRes)
    }

    override fun getItemCount(): Int {
        return bankSubList.size
    }

    inner class Viewholder(view: View) : RecyclerView.ViewHolder(view) {
        private val SubImage: ImageView
        fun setData(imageRes: Int) {
            SubImage.setImageResource(imageRes)
        }

        init {
            SubImage = view.findViewById(R.id.sub_image)
            itemView.setOnClickListener {
                if (mListner != null) {
                    val pos = absoluteAdapterPosition
                    mListner!!.onItemClick(pos)
                }
            }
        }
    }
}