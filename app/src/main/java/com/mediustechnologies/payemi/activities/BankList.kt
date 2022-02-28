package com.mediustechnologies.payemi.activities

import android.content.Context
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import com.mediustechnologies.payemi.ApiResponse.banklistResponse
import androidx.recyclerview.widget.GridLayoutManager
import com.mediustechnologies.payemi.adapters.bankListAdapter
import com.mediustechnologies.payemi.ApiResponse.getAllBanks
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mediustechnologies.payemi.adapters.catagoryAdapter
import com.mediustechnologies.payemi.adapters.catagoryAdapter.oncatagoryClick
import android.content.Intent
import com.mediustechnologies.payemi.activities.BillerList
import android.text.TextWatcher
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mediustechnologies.payemi.commons.utils
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import com.mediustechnologies.payemi.databinding.ActivityBankListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.ArrayList

class BankList : BaseAppCompatActivity() {
    private var binding: ActivityBankListBinding? = null
    private var banklist: banklistResponse? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var adapter: bankListAdapter? = null
    private val context: Context = this
    private var catagories: ArrayList<String>? = null
    private var ids: ArrayList<String>? = null
    private var filteredList: MutableList<getAllBanks>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankListBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        val p = intent.getIntExtra("position", 0)
        initcatagoriesRecyclerview(p)
        addItemsInRecyclerView(intent.getStringExtra("loan_category_id"))
    }

    private fun initcatagoriesRecyclerview(p: Int) {
        catagories = intent.getStringArrayListExtra("catagories")
        ids = intent.getStringArrayListExtra("ids")
        val catagory = binding!!.catagory
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        catagory.layoutManager = linearLayoutManager
        catagory.scrollToPosition(p)
        val ad = catagoryAdapter(catagories, p)
        catagory.adapter = ad
        ad.setOnCatagoryClickListner { position ->
            addItemsInRecyclerView(ids!![position])
            initcatagoriesRecyclerview(position)
            println(p.toString() + "  " + catagories!![p])
        }
    }

    private fun initRecyclerView() {
        val bankRecyclerView = binding!!.listOfBanks
        gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager!!.orientation = RecyclerView.VERTICAL
        bankRecyclerView.layoutManager = gridLayoutManager
        adapter = bankListAdapter(banklist!!.data)
        bankRecyclerView.adapter = adapter
        searchbar()
        adapter!!.setOnItemClickListner { position: Int ->
            val i = Intent(context, BillerList::class.java)
            if (filteredList != null && !filteredList!!.isEmpty()) {
                i.putExtra("name", filteredList!![position].bank_name)
                i.putExtra("imgurl", filteredList!![position].bank_logo_url)
                i.putExtra("count", filteredList!!.size)
            } else {
                i.putExtra("name", banklist!!.data[position].bank_name)
                i.putExtra("imgurl", banklist!!.data[position].bank_logo_url)
                i.putExtra("count", banklist!!.data.size)
            }
            startActivity(i)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.clear()
        for (i in catagories!!.indices) {
            menu.add(0, i, Menu.NONE, catagories!![i])
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        for (i in catagories!!.indices) {
            if (i == item.itemId) {
                addItemsInRecyclerView(ids!![i])
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchbar() {
        binding!!.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
        })
    }

    private fun filter(text: String) {
        filteredList = ArrayList()
        for (item in banklist!!.data) {
            if (item.bank_name.toLowerCase().contains(text.toLowerCase())) {
                (filteredList as ArrayList<getAllBanks>).add(item)

            }
        }
        adapter!!.filterList(filteredList)
    }

    private fun addItemsInRecyclerView(loan_category_id: String?) {
        Log.d("tag", "Access Token Saved in Utils " + utils.access_token)
        val call = RetrofitClient().getInstance(
            context,
            urlconstants.AuthURL
        ).api.getAllBanks(utils.access_token, loan_category_id)
        call.enqueue(object : Callback<banklistResponse> {
            override fun onResponse(
                call: Call<banklistResponse>,
                response: Response<banklistResponse>
            ) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.isSuccessful && response.body()!!
                        .data != null
                ) {
                    if (response.body()!!.error == null || response.body()!!
                            .error.equals("false", ignoreCase = true)
                    ) {
                        banklist = response.body()
                        Log.d("tag", "Banklist setdata: " + banklist.toString())
                        initRecyclerView()
                    } else {
                        try {
                            utils.errortoast(context, response.body()!!.message)
                        } catch (e: Exception) {
                            Log.e("tag", e.toString())
                        }
                    }
                } else {
                    Log.d("tag", "Get BankList onResponse: " + response.code())
                }
            }

            override fun onFailure(call: Call<banklistResponse>, t: Throwable) {
                Log.d("tag", "Get BankList failed : $t")
            }
        })
    }
}