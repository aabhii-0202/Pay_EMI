package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.mediustechnologies.payemi.Models.bankListItem
import androidx.recyclerview.widget.GridLayoutManager
import com.mediustechnologies.payemi.adapters.bankListAdapter
import android.os.Bundle
import com.mediustechnologies.payemi.R
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import android.content.Intent
import android.view.View
import com.mediustechnologies.payemi.activities.act5BankSubCategories
import com.mediustechnologies.payemi.databinding.ActivityBankListBinding
import java.util.ArrayList

class act4BankList : AppCompatActivity() {
    private var binding: ActivityBankListBinding? = null
    private lateinit var banklist: MutableList<bankListItem>
    private var gridLayoutManager: GridLayoutManager? = null
    private var adapter: bankListAdapter? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankListBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
        addDummmyItemsInRecyclerView()
        initRecyclerView()
    }

    private fun addDummmyItemsInRecyclerView() {
        banklist = ArrayList()
        banklist.add(bankListItem(R.drawable.b1, "Axis Bank"))
        banklist.add(bankListItem(R.drawable.b2, "HDFC Bank"))
        banklist.add(bankListItem(R.drawable.b3, "SBI"))
        banklist.add(bankListItem(R.drawable.b4, "Kotak Bank"))
        banklist.add(bankListItem(R.drawable.b5, "Bank of Baroda"))
        banklist.add(bankListItem(R.drawable.b6, "City Bank"))
        banklist.add(bankListItem(R.drawable.b7, "HSBC Bank"))
        banklist.add(bankListItem(R.drawable.b8, "Edelweiss Bank"))
        banklist.add(bankListItem(R.drawable.b9, "ICICI Bank"))
    }

    private fun initRecyclerView() {
        val bankRecyclerView = binding!!.listOfBanks
        gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager!!.orientation = RecyclerView.VERTICAL
        bankRecyclerView.layoutManager = gridLayoutManager
        adapter = bankListAdapter(banklist)
        bankRecyclerView.adapter = adapter
        adapter!!.setOnItemClickListner { position ->
            Toast.makeText(
                context,
                "$position posion item clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun init() {
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.bharatBillLogo.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    this,
                    act5BankSubCategories::class.java
                )
            )
        }
    }
}