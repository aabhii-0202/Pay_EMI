package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mediustechnologies.payemi.Models.bankSubItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.mediustechnologies.payemi.adapters.bankSublistAdapter
import android.os.Bundle
import com.mediustechnologies.payemi.adapters.bankListAdapter
import android.widget.Toast
import com.mediustechnologies.payemi.R
import android.content.Intent
import android.view.View
import com.mediustechnologies.payemi.activities.act6addLoadAccount
import com.mediustechnologies.payemi.databinding.ActivityBankSubCategoriesBinding
import java.util.ArrayList

class act5BankSubCategories : AppCompatActivity() {
    private var binding: ActivityBankSubCategoriesBinding? = null
    private var bankSubListRecyclerview: RecyclerView? = null
    private lateinit var bankSubList: MutableList<bankSubItem>
    private var linearLayoutManager: LinearLayoutManager? = null
    private var adapter: bankSublistAdapter? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankSubCategoriesBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
        addDummmyItemsInRecyclerView()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        bankSubListRecyclerview = binding!!.ListRecyclerView
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager!!.orientation = RecyclerView.VERTICAL
        bankSubListRecyclerview!!.layoutManager = linearLayoutManager
        adapter = bankSublistAdapter(bankSubList)
        bankSubListRecyclerview!!.adapter = adapter
        adapter!!.setOnItemClickListner { position ->
            Toast.makeText(
                context,
                "$position position item clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun addDummmyItemsInRecyclerView() {
        bankSubList = ArrayList()
        bankSubList.add(bankSubItem(R.drawable.axis_financne))
        bankSubList.add(bankSubItem(R.drawable.axisfinance3))
    }

    private fun init() {
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.bharatBillLogo.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    this,
                    act6addLoadAccount::class.java
                )
            )
        }
    }
}