package com.mediustechnologies.payemi.activities

import android.content.Context
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import android.os.Bundle
import com.mediustechnologies.payemi.ApiResponse.loancategory
import com.mediustechnologies.payemi.ApiResponse.getAllBanks
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.mediustechnologies.payemi.adapters.bankListAdapter
import android.content.Intent
import android.util.Log
import android.view.View
import com.mediustechnologies.payemi.activities.BankList
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import com.mediustechnologies.payemi.commons.utils
import android.widget.Toast
import com.mediustechnologies.payemi.databinding.ActivityEmiCategoriesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class EmiCategories : BaseAppCompatActivity() {
    private var binding: ActivityEmiCategoriesBinding? = null
    private val context: Context = this
    private var catagories: ArrayList<String>? = null
    private var ids: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmiCategoriesBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
        api()
    }

    private fun setdata(data: List<loancategory>?) {
        val banklist: MutableList<getAllBanks> = ArrayList()
        for (i in data!!.indices) {
            catagories!!.add(data[i].category_name)
            ids!!.add(data[i].id)
            val temp = getAllBanks()
            temp.bank_name = data[i].category_name
            temp.id = data[i].id
            temp.bank_logo_url = data[i].url
            banklist.add(temp)
        }
        val category = binding!!.rec
        val gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        category.layoutManager = gridLayoutManager
        val adapter = bankListAdapter(banklist)
        category.adapter = adapter
        adapter.setOnItemClickListner { position: Int ->
            val i = Intent(context, BankList::class.java)
            i.putExtra("loan_category_id", banklist[position].id)
            i.putStringArrayListExtra("catagories", catagories)
            i.putStringArrayListExtra("ids", ids)
            i.putExtra("position", position)
            startActivity(i)
        }
    }

    private fun api() {
        val call = RetrofitClient().getInstance(
            context,
            urlconstants.AuthURL
        ).api.getLoanCategory(utils.access_token)
        call.enqueue(object : Callback<List<loancategory>?> {
            override fun onResponse(
                call: Call<List<loancategory>?>,
                response: Response<List<loancategory>?>
            ) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    val data = response.body()
                    if (data!!.size > 0) setdata(data)
                } else {
                    Log.e("tag", "" + response.code())
                }
            }

            override fun onFailure(call: Call<List<loancategory>?>, t: Throwable) {
                Log.e("tag", "Error in loading loan categories, $t")
                Toast.makeText(
                    context,
                    "Error fetching data, Check your internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun init() {
        ids = ArrayList()
        catagories = ArrayList()
        binding!!.back.setOnClickListener { view: View? -> finish() }
    }
}