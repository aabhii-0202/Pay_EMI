package com.mediustechnologies.payemi.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mediustechnologies.payemi.ApiResponse.AllTransactions
import com.mediustechnologies.payemi.ApiResponse.RedeemScratchCard
import com.mediustechnologies.payemi.ApiResponse.TransactionDetails
import com.mediustechnologies.payemi.R
import com.mediustechnologies.payemi.activities.DashBoardclasses.Home_Nav
import com.mediustechnologies.payemi.activities.scratchCard.listener.ScratchListener
import com.mediustechnologies.payemi.activities.scratchCard.ui.ScratchCardLayout
import com.mediustechnologies.payemi.adapters.TransactionHistoryAdapter
import com.mediustechnologies.payemi.commons.urlconstants
import com.mediustechnologies.payemi.commons.utils
import com.mediustechnologies.payemi.databinding.ActivityPayEmiTransactionPageBinding
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.recyclerItems.transaction_chat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EMITransactionHistory : BaseAppCompatActivity(), PopupMenu.OnMenuItemClickListener {
    private val context: Context = this
    private var binding: ActivityPayEmiTransactionPageBinding? = null
    private var chatlist: ArrayList<transaction_chat>? = null
    private var logo: String? = null
    private var name: String? = null
    private var loanname: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayEmiTransactionPageBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
        allTransaction
    }

    private val allTransaction: Unit
        private get() {
            val biller_id = intent.getStringExtra("biller_id")
            val token = utils.access_token
            val call = RetrofitClient().getInstance(
                context,
                urlconstants.AuthURL
            ).api.allTransaction(token, utils.profileId, biller_id)
            call.enqueue(object : Callback<AllTransactions?> {
                override fun onResponse(
                    call: Call<AllTransactions?>,
                    response: Response<AllTransactions?>
                ) {
                    if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                        if (response.body()!!.error == null || response.body()!!
                                .error.equals("false", ignoreCase = true)
                        ) {
                            val data = response.body()!!
                                .data
                            addata(data)
                        } else {
                            try {
                                utils.errortoast(context, response.body()!!.message)
                            } catch (e: Exception) {
                                Log.e("tag", e.toString())
                            }
                        }
                    } else {
                        Toast.makeText(context, "Response " + response.code(), Toast.LENGTH_SHORT)
                            .show()
                        Log.d("tag", "Transaction Details response " + response.code())
                    }
                }

                override fun onFailure(call: Call<AllTransactions?>, t: Throwable) {}
            })
        }

    private fun addata(data: List<TransactionDetails>) {
        try {
            chatlist = ArrayList()
            if (data.isEmpty()) Toast.makeText(
                context,
                "No transaction history.",
                Toast.LENGTH_SHORT
            ).show()
            for (i in data.indices) {
                var date = data[i].transaction_datetime
                date = formatdate(date)
                val item = data[i]
                var status = item.bbps_transaction_status
                if (status != null) {
                    date = if (status.equals("successful", true) ||
                        status.equals("success", true) ||
                        status.equals("su", true)
                    ) {
                        "  Paid | $date"
                    } else if  (status.equals("failed", true) ||
                        status.equals("fa", true)
                    ) {
                        "  Failed | $date"
                    } else {
                        " Pending | $date"
                    }
                } else {
                    status = "Pending"
                    date = " Pending | $date"
                }

                var product_name: String? = null
                if (!item.product_name.isEmpty())
                    product_name = item.product_name[0]
                else product_name = "Not available"

                chatlist!!.add(
                    transaction_chat(
                        "Payment to " + item.biller_name,
                        product_name,
                        "₹ " + item.amount,
                        status,
                        date,
                        item.is_redeemed,
                        item.type
                    )
                )
            }
            initrecyclerview(data)
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Toast.makeText(context, "No Transaction History", Toast.LENGTH_SHORT).show()
            Log.e("tag", "" + e.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("tag", "" + e.toString())
        }
    }

    private fun formatdate(date: String): String {
        var ans = ""
        val day = date.substring(8, 10)
        ans += "$day "
        val month = date.substring(5, 7)
        val months = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep",
            "Oct", "Nov", "Dec"
        )
        val m = month.toInt()
        try {
            ans += months[m - 1]
        } catch (e: Exception) {
            ans = "Error finding month"
        }
        return ans
    }

    private fun initrecyclerview(data: List<TransactionDetails>) {
        val recyclerView = binding!!.paymentRecyclerView
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        val adapter = TransactionHistoryAdapter(context, chatlist)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListner { postion ->
            val item = data[postion]
            if (item.type == "transaction") {
                val i = Intent(context, Transaction_Details::class.java)
                i.putExtra("item", item)
                i.putExtra("logo", logo)
                startActivity(i)
            } else {
                // code for cashback
                if (item.is_redeemed == "false") {
                    val d = Dialog(context)
                    d.setContentView(R.layout.scratchcardlayout)
                    d.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    val scratchamount = d.findViewById<TextView>(R.id.scratch_cashback_amount)
                    scratchamount.text = "Rs. " + item.amount
                    d.show()
                    val card: ScratchCardLayout = d.findViewById(R.id.scratchCard1)
                    card.setScratchListener(object : ScratchListener {
                        override fun onScratchStarted() {}
                        override fun onScratchProgress(
                            scratchCardLayout: ScratchCardLayout,
                            atLeastScratchedPercent: Int
                        ) {
                            Log.d("tag", "onScratchProgress: $atLeastScratchedPercent")
                            if (atLeastScratchedPercent > 7) {
                                Log.d("tag", "scratched")
                                redeem(item.bill_id)
                            }
                        }

                        override fun onScratchComplete() {}
                    })
                } else {
                }
            }
        }
    }

    private fun redeem(bill_id: String) {
        Log.d("tag", " --> " + utils.profileId + "--------" + bill_id)
        val call = RetrofitClient().getInstance(
            context,
            urlconstants.AuthURL
        ).api.redeemscratch(utils.access_token, utils.profileId, bill_id)
        call.enqueue(object : Callback<RedeemScratchCard?> {
            override fun onResponse(
                call: Call<RedeemScratchCard?>,
                response: Response<RedeemScratchCard?>
            ) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body()!!.message == "Success") {
                        Log.d("tag", "Redeemed cashback successful")
                        Toast.makeText(context, "Cashback Added Successfully", Toast.LENGTH_LONG)
                            .show()
                        allTransaction
                    }
                } else if (response.code() == 400) {
                    println(response.message())
                } else {
                    Log.e("tag", "Error ar redeem code " + response.code())
                }
            }

            override fun onFailure(call: Call<RedeemScratchCard?>, t: Throwable) {}
        })
    }

    private fun init() {
        name = intent.getStringExtra("name")
        logo = intent.getStringExtra("logo")
        loanname = intent.getStringExtra("lonaname")
        binding!!.loanName.text = loanname
        Glide.with(binding!!.image).load(logo).into(binding!!.image)
        binding!!.bankname.text = name
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
    }

    fun menu(v: View?) {
        val popup = PopupMenu(context, v)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.transaction_history)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.gethelp -> {
                val i = Intent(context, Home_Nav::class.java)
                i.putExtra("fragment",Home_Nav.POS_HELP)
                startActivity(i)
                true
            }
            R.id.refreshhistory -> {
                allTransaction
                true
            }
            else -> false
        }
    }
}