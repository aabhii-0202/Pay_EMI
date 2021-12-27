package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.mediustechnologies.payemi.Models.emiListItem
import android.os.Bundle
import com.mediustechnologies.payemi.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mediustechnologies.payemi.adapters.emiListItemAdapter
import android.content.Intent
import android.view.View
import com.mediustechnologies.payemi.activities.act34pay_EMI_Details
import com.mediustechnologies.payemi.databinding.ActivityPayEmiHomeBinding
import java.util.ArrayList

class act33payEMI_home : AppCompatActivity() {
    private var binding: ActivityPayEmiHomeBinding? = null
    private lateinit var emilist: MutableList<emiListItem>
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayEmiHomeBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
        dummydata()
        initrecycler()
    }

    private fun dummydata() {
        emilist = ArrayList()
        emilist.add(
            emiListItem(
                "Rs 15,000",
                "ICICI Bank Limited",
                "Home Loan",
                "₹ 52L",
                "₹ 80L",
                R.drawable.b1,
                60
            )
        )
        emilist.add(emiListItem("Rs 1,000", "SBI", "Edu Loan", "₹ 1.2L", "₹ 2L", R.drawable.b6, 60))
        emilist.add(
            emiListItem(
                "Rs 8,000",
                "HDFC Bank Limited",
                "Car Loan",
                "₹ 5.2L",
                "₹ 12L",
                R.drawable.b5,
                45
            )
        )
        emilist.add(emiListItem("Rs 1,000", "SBI", "Edu Loan", "₹ 1.2L", "₹ 2L", R.drawable.b7, 60))
        emilist.add(
            emiListItem(
                "Rs 10,000",
                "ICICI Bank Limited",
                "Car Loan",
                "₹ 5.2L",
                "₹ 12L",
                R.drawable.b6,
                48
            )
        )
        emilist.add(
            emiListItem(
                "Rs 19,000",
                "ICICI Bank Limited",
                "Home Loan",
                "₹ 52L",
                "₹ 80L",
                R.drawable.b1,
                60
            )
        )
        emilist.add(
            emiListItem(
                "Rs 12,000",
                "SBI",
                "Edu Loan",
                "₹ 1.2L",
                "₹ 2L",
                R.drawable.b5,
                70
            )
        )
        emilist.add(
            emiListItem(
                "Rs 10,000",
                "HDFC Bank Limited",
                "Car Loan",
                "₹ 6L",
                "₹ 12L",
                R.drawable.b7,
                50
            )
        )
        emilist.add(emiListItem("Rs 3,000", "SBI", "Edu Loan", "₹ 1.2L", "₹ 2L", R.drawable.b1, 80))
    }

    private fun initrecycler() {
        val emilistRecycler = binding!!.loanrecyclerview
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        emilistRecycler.layoutManager = linearLayoutManager
        val adapter = emiListItemAdapter(emilist)
        emilistRecycler.adapter = adapter
    }

    private fun init() {
        binding!!.addbutton.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    act34pay_EMI_Details::class.java
                )
            )
        }
    }
}