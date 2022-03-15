package com.mediustechnologies.payemi.activities

import android.content.Context
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mediustechnologies.payemi.DTO.GetBillerByBankDTO
import androidx.recyclerview.widget.LinearLayoutManager
import com.mediustechnologies.payemi.adapters.BankSublistAdapter
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.View
import com.mediustechnologies.payemi.ApiResponse.getBillerByBank
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import com.mediustechnologies.payemi.commons.utils
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mediustechnologies.payemi.databinding.ActivityBankSubCategoriesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.ArrayList

class BillerList : BaseAppCompatActivity() {
    private var binding: ActivityBankSubCategoriesBinding? = null
    private var bankSubListRecyclerview: RecyclerView? = null
    private var bankSubList: List<GetBillerByBankDTO>? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var adapter: BankSublistAdapter? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankSubCategoriesBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
        addItemsInRecyclerView()
    }

    private fun initRecyclerView() {
        bankSubListRecyclerview = binding!!.ListRecyclerView
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager!!.orientation = RecyclerView.VERTICAL
        bankSubListRecyclerview!!.layoutManager = linearLayoutManager
        adapter =
            BankSublistAdapter(bankSubList)
        bankSubListRecyclerview!!.adapter = adapter
        adapter!!.setOnItemClickListner { position: Int ->
            val i = Intent(context, AddLoanAccount::class.java)

            i.putExtra("url", bankSubList!![position].logo_url)
            i.putExtra("biller_id", bankSubList!![position].billerId)
            i.putExtra("biller_name", bankSubList!![position].billerName)
            startActivity(i)
        }
        binding!!.progressbar.visibility = View.GONE
    }

    private fun addItemsInRecyclerView() {
        bankSubList = ArrayList()
        val name = intent.getStringExtra("name")
        val call = RetrofitClient().getInstance(
            context,
            urlconstants.AuthURL
        ).api.getBillerByBank(utils.access_token, name)
        call.enqueue(object : Callback<getBillerByBank?> {
            override fun onResponse(
                call: Call<getBillerByBank?>,
                response: Response<getBillerByBank?>
            ) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body()!!.error == null || response.body()!!
                            .error.equals("false", ignoreCase = true)
                    ) {
                        bankSubList = response.body()!!.data
                        Log.d("tag", "biller list " + response.body())
                        initRecyclerView()
                    } else {
                        try {
                            utils.errortoast(context, response.body()!!.message)
                        } catch (e: Exception) {
                            Log.e("tag", e.toString())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<getBillerByBank?>, t: Throwable) {
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun init() {
        binding!!.progressbar.visibility = View.VISIBLE
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.ParentBankName.text = intent.getStringExtra("name")
        val url = intent.getStringExtra("imgurl")
        if (url != null) Glide.with(binding!!.financerlogo).load(url).into(binding!!.financerlogo)
    }
}