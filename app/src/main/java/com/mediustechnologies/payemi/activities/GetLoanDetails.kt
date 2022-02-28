package com.mediustechnologies.payemi.activities

import android.content.Context
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import com.mediustechnologies.payemi.ApiResponse.fetchBill
import com.mediustechnologies.payemi.DTO.HomepageDTO
import android.os.Bundle
import com.bumptech.glide.Glide
import android.content.Intent
import android.util.Log
import android.view.View
import com.mediustechnologies.payemi.activities.Exactness
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import com.mediustechnologies.payemi.commons.utils
import android.widget.Toast
import com.mediustechnologies.payemi.DTO.billFetchDTO
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mediustechnologies.payemi.adapters.fetchBillAdapter2
import com.mediustechnologies.payemi.databinding.ActivityPayEmiDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.LinkedHashMap

class GetLoanDetails : BaseAppCompatActivity() {
    private val context: Context = this
    private var binding: ActivityPayEmiDetailsBinding? = null
    private var bill: fetchBill? = null
    private var data: HomepageDTO? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPayEmiDetailsBinding.inflate(
            layoutInflater
        )
        super.onCreate(savedInstanceState)
        setContentView(binding!!.root)
        init()
        fetchBill()
    }

    private fun init() {
        data = intent.getParcelableExtra("data")
        val totalemi = data!!.loan_amount
        val loanpaied = data!!.loan_paid
        val progress: Int
        progress = try {
            val total = totalemi.toDouble()
            val paied = loanpaied.toDouble()
            val p = (paied / total * 100).toInt()
            p
        } catch (e: Exception) {
            -1
        }
        if (progress == -1) binding!!.emiProgressbar.visibility = View.GONE else {
            binding!!.emiProgressbar.progress = progress
        }
        try {
            Glide.with(binding!!.financerlogo).load(data!!.biller__logo_url)
                .into(binding!!.financerlogo)
            binding!!.amount.text = "Rs." + data!!.amount
            binding!!.productname.text = data!!.loan_type
            var temp = data!!.loan_paid
            temp = formatinword(temp)
            binding!!.paidamount.text = temp
            temp = data!!.loan_amount
            temp = formatinword(temp)
            binding!!.totalLoan.text = temp
            binding!!.interestRate.text = "NA"
            binding!!.emi.text = data!!.emi
            binding!!.Tenure.text = "NA"
        } catch (e: Exception) {
        }
        binding!!.scrollviewshare.visibility = View.GONE
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.paybtn.setOnClickListener { view: View? -> nextscreen() }
        val bankname = intent.getStringExtra("bankname")
        binding!!.FinancerName.text = bankname
    }

    private fun nextscreen() {
        val i = Intent(context, Exactness::class.java)
        i.putExtra("data", data)
        i.putExtra("type", data!!.loan_type)
        i.putExtra("bill_id", bill!!.payload[0].id)
        i.putExtra("customer", bill!!.payload[0].customer_name)
        i.putExtra("amount", bill!!.payload[0].amount)
        i.putExtra("profile_id", bill!!.payload[0].profile_id)
        val name = intent.getStringExtra("billerName")
        i.putExtra("billerName", name)
        startActivity(i)
    }

    private fun fetchBill() {
        val loan_id = intent.getStringExtra("loan_id")
        val call = RetrofitClient().getInstance(
            context,
            urlconstants.AuthURL
        ).api.getLoanDetails(utils.access_token, loan_id, utils.profileId)
        call.enqueue(object : Callback<fetchBill?> {
            override fun onResponse(call: Call<fetchBill?>, response: Response<fetchBill?>) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body()!!.error == null || response.body()!!
                            .error.equals("false", ignoreCase = true)
                    ) {
                        bill = response.body()
                        utils.bill_id = response.body()!!.payload[0].id
                        binding!!.paybtn.visibility = View.VISIBLE
                        setData(bill)
                        binding!!.progress.visibility = View.GONE
                        binding!!.scrollviewshare.visibility = View.VISIBLE
                    } else {
                        try {
                            utils.errortoast(context, response.body()!!.message)
                        } catch (e: Exception) {
                            Log.e("tag", e.toString())
                        }
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(
                        context,
                        "Phone number not linked to loan, please enter with linked phone number",
                        Toast.LENGTH_LONG
                    ).show()
                    binding!!.progress.visibility = View.GONE
                } else if (response.code() == 401) {
                    Toast.makeText(context, "Token Expired", Toast.LENGTH_LONG).show()
                    binding!!.progress.visibility = View.GONE
                    finish()
                }
            }

            override fun onFailure(call: Call<fetchBill?>, t: Throwable) {
                Log.d("tag", "onFailure: bill fetch$t")
                Toast.makeText(context, "Error fetching Bill", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setData(bill: fetchBill?) {
        try {
            val data = bill!!.payload[0]
            if (data.amount != null) {
                binding!!.basebill.text = data.amount
            } else binding!!.basebillamountholder.visibility = View.GONE
            if (data.emi != null) {
                binding!!.emi.text = data.emi
            } else binding!!.emiholder.visibility = View.GONE
            if (data.tenure != null) {
                binding!!.Tenure.text = data.tenure
            } else binding!!.tenureholder.visibility = View.GONE
            if (data.respDueDate != null) {
                binding!!.duedate.text = data.respDueDate
            } else binding!!.duedateholder.visibility = View.GONE
            if (data.charges_levied != null) {
                binding!!.charges.text = data.charges_levied
            } else binding!!.chargeslevivedholder.visibility = View.GONE
            val variableData = LinkedHashMap<String, String>()
            variableData.putAll(data.amountOptions)
            variableData.putAll(data.inputparams_value)
            variableData.putAll(data.biller_additional_info)
            recyclerview(variableData)
        } catch (e: Exception) { }
    }

    private fun recyclerview(bill: LinkedHashMap<String, String>) {
        val recyclerView = binding!!.fetchBillRecyclerView2
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        val adapter = fetchBillAdapter2(bill)
        recyclerView.adapter = adapter
    }

    private fun formatinword(total_amount: String): String {
        var ans = "₹"
        ans += try {
            val length = total_amount.length
            if (length > 7) {
                total_amount.substring(0, length - 7) + "." + total_amount.substring(
                    length - 7,
                    length - 6
                ) + "Cr"
            } else if (length > 5) {
                total_amount.substring(0, length - 5) + "." + total_amount.substring(
                    length - 5,
                    length - 4
                ) + "L"
            } else if (length > 3) {
                total_amount.substring(0, length - 3) + "." + total_amount.substring(
                    length - 3,
                    length - 2
                ) + "K"
            } else total_amount
        } catch (e: Exception) {
            return "null"
        }
        return ans
    }
}