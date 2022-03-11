package com.mediustechnologies.payemi.activities

import android.content.Context
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.mediustechnologies.payemi.ApiResponse.fetchBill
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mediustechnologies.payemi.adapters.fetchBillAdapter
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mediustechnologies.payemi.activities.Exactness
import com.mediustechnologies.payemi.databinding.ActivityPaymentInfoBinding
import java.util.HashMap

class EMIDetailsBillFetch : BaseAppCompatActivity() {
    private var binding: ActivityPaymentInfoBinding? = null
    private val context: Context = this
    private var url: String? = null
    private var name: String? = null
    private var amount: String? = null
    private var customer: String? = null
    private var bill_id: String? = null
    private var profile_id: String? = null
    private var exactness: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentInfoBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        binding!!.scrollView.visibility = View.GONE
        url = intent.getStringExtra("url")
        name = intent.getStringExtra("biller_name")
        binding!!.FinancerName.text = name
        Glide.with(binding!!.financerlogo).load(url).into(binding!!.financerlogo)
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.payNow.setOnClickListener { view: View? -> nextscreen() }
        val bill: fetchBill? = intent.getParcelableExtra("bill")
        binding!!.payNow.visibility = View.VISIBLE
        setData(bill)
        bill_id = bill!!.payload[0].id
        profile_id = bill!!.payload[0].profile_id
        binding!!.progress!!.visibility = View.GONE
    }

    private fun setData(bill: fetchBill?) {

        if(bill!!.payload.isEmpty()){
            Log.e("tag","bill fetch pay load empty at line 52 EMIDetailFetchBill.kt")
            Toast.makeText(context,"Error fetching bill",Toast.LENGTH_LONG).show()
            return
        }
        binding!!.scrollView!!.visibility = View.VISIBLE
        amount = bill!!.payload[0].amount
        customer = bill.payload[0].customer_name
        if (bill.payload[0].service_tax!=null) binding!!.ServiceTax.text =
            bill.payload[0].service_tax else binding!!.serviceTaxholder.visibility = View.GONE
        if (bill.payload[0].respDueDate != null) binding!!.DueDate.text =
            bill.payload[0].respDueDate else binding!!.duedatelayout.visibility = View.GONE
        if (bill.payload[0].amount != null) binding!!.basebillamount.text =
            bill.payload[0].amount else binding!!.basebillamountcontainer.visibility = View.GONE
        if (bill.payload[0].respBillPeriod != null) binding!!.Tenure.text =
            bill.payload[0].respBillPeriod else binding!!.tenurelayout.visibility = View.GONE
        if (bill.payload[0].amount != null) binding!!.Amount.text =
            bill.payload[0].amount else binding!!.amountlayout.visibility = View.GONE
        if (bill.payload[0].amount != null) binding!!.TotalAmount.text =
            bill.payload[0].amount else binding!!.totalamountlayout.visibility = View.GONE
        if (bill.exactness != null) {
            exactness = bill.exactness
        }
        if (bill.payload[0].loan_idloan_id != null) binding!!.loanid.text =
            bill.payload[0].loan_idloan_id else binding!!.loanidcontainer.visibility = View.GONE
        if (bill.payload[0].loan_acc_no != null) binding!!.loanaccountnumber.text =
            bill.payload[0].loan_acc_no else binding!!.loanaccountnumbercontainer.visibility = View.GONE
        if (bill.payload[0].customer_name != null) binding!!.customerName.text =
            bill.payload[0].customer_name else binding!!.customerNamecontainer.visibility = View.GONE
        if (bill.payload[0].customer_mobile != null) binding!!.customermobile.text =
            bill.payload[0].customer_mobile else binding!!.customerNamecontainer.visibility = View.GONE
        if (bill.payload[0].requestId != null) binding!!.requestid.text =
            bill.payload[0].requestId else binding!!.requestidcontainer.visibility = View.GONE
        if (bill.payload[0].bill_date != null) binding!!.billdate.text =
            bill.payload[0].bill_date else binding!!.biloldatecontainer.visibility = View.GONE
        if (bill.payload[0].biller_name != null) binding!!.billername.text =
            bill.payload[0].biller_name else binding!!.billernamecontainer.visibility = View.GONE









        var variableData: HashMap<String?, String?>? = HashMap()
        variableData = intent.getSerializableExtra("variableData") as HashMap<String?, String?>?
        recyclerview(variableData)
    }

    private fun recyclerview(bill: HashMap<String?, String?>?) {
        val recyclerView = binding!!.variablerec
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        val adapter = fetchBillAdapter(bill)
        recyclerView.adapter = adapter
    }

    private fun nextscreen() {
        val i = Intent(context, Exactness::class.java)
        i.putExtra("logo", url)
        i.putExtra("customer", customer)
        i.putExtra("amount", amount)
        i.putExtra("billerName", name)
        i.putExtra("bill_id", bill_id)
        i.putExtra("profile_id", profile_id)
        i.putExtra("exact", exactness)
        startActivity(i)
    }
}