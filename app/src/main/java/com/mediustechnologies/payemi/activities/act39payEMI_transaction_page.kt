package com.mediustechnologies.payemi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.mediustechnologies.payemi.Models.transaction_chat
import com.mediustechnologies.payemi.adapters.TransctionChatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import com.mediustechnologies.payemi.Models.AllCashbacks
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import android.content.Intent
import android.util.Log
import android.view.View
import com.mediustechnologies.payemi.databinding.ActivityPayEmiTransactionPageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class act39payEMI_transaction_page : AppCompatActivity() {
    private val context: Context = this
    private var binding: ActivityPayEmiTransactionPageBinding? = null
    private var chatlist: ArrayList<transaction_chat>? = null
    private var adapter: TransctionChatAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var recyclerView: RecyclerView? = null
    private val TAG = "tag"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayEmiTransactionPageBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
        dummydata()
        //        getAllCashbacks();
        initrecyclerview()
    }

    //dummy profile id for now
    private val allCashbacks: Unit
        private get() {
            val profile_id = 2.toString() + "" //dummy profile id for now
            val call =
                RetrofitClient.getInstance(urlconstants.AuthURL).api.getallcashback(profile_id)
            call.enqueue(object : Callback<List<AllCashbacks>> {
                override fun onResponse(
                    call: Call<List<AllCashbacks>>,
                    response: Response<List<AllCashbacks>>
                ) {
                    if (response.code() == 200) {
                        val cashbacksList = response.body()!!
                        var temp = ""
                        for (i in cashbacksList.indices) {
                            temp = """
Amount ${cashbacksList[i].cashback_amount} Date: ${cashbacksList[i].date} Status: ${cashbacksList[i].status}"""
                        }
                        Log.d(TAG, "all cashbacks response: $temp")
                    }
                }

                override fun onFailure(call: Call<List<AllCashbacks>>, t: Throwable) {}
            })
        }

    private fun dummydata() {
        chatlist = ArrayList()
        chatlist!!.add(transaction_chat(null, null, null, null, "15 Oct 6:24PM"))
        chatlist!!.add(
            transaction_chat(
                "Payment to HDFC Bank",
                "Car Loan",
                "₹ 14,288",
                "  Paid | 15 Oct",
                null
            )
        )
        chatlist!!.add(transaction_chat("Scratch Now", "Earn a reward!", "", null, null))
        chatlist!!.add(
            transaction_chat(
                "Payment to HDFC Bank",
                "Car Loan",
                "₹ 14,288",
                "  Paid | 15 Oct",
                null
            )
        )
        chatlist!!.add(
            transaction_chat(
                "Payment to HDFC Bank",
                "Car Loan",
                "₹ 14,288",
                "  Paid | 15 Oct",
                null
            )
        )
        chatlist!!.add(transaction_chat("Rs. 140", "You earned a reward!", "", null, null))
        chatlist!!.add(
            transaction_chat(
                "Payment to HDFC Bank",
                "Car Loan",
                "₹ 14,288",
                "  Paid | 15 Oct",
                null
            )
        )
        chatlist!!.add(
            transaction_chat(
                "Payment to HDFC Bank",
                "Car Loan",
                "₹ 14,288",
                "  Paid | 15 Oct",
                null
            )
        )
        chatlist!!.add(transaction_chat("Scratch Now", "Earn a reward!", "", null, null))
        chatlist!!.add(transaction_chat(null, null, null, null, "18 Oct 6:24PM"))
        chatlist!!.add(
            transaction_chat(
                "Payment to HDFC Bank",
                "Car Loan",
                "₹ 14,288",
                "  Paid | 15 Oct",
                null
            )
        )
        chatlist!!.add(
            transaction_chat(
                "Payment to HDFC Bank",
                "Car Loan",
                "₹ 14,288",
                "  Paid | 15 Oct",
                null
            )
        )
        chatlist!!.add(transaction_chat("Rs. 140", "You earned a reward!", "", null, null))
        chatlist!!.add(
            transaction_chat(
                "Payment to HDFC Bank",
                "Car Loan",
                "₹ 14,288",
                "  Paid | 15 Oct",
                null
            )
        )
    }

    private fun initrecyclerview() {
        recyclerView = binding!!.paymentRecyclerView
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager!!.orientation = RecyclerView.VERTICAL
        recyclerView!!.layoutManager = linearLayoutManager
        adapter = TransctionChatAdapter(context, chatlist)
        recyclerView!!.adapter = adapter
    }

    private fun init() {
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.dotts.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    act40transaction_Details::class.java
                )
            )
        }
    }
}