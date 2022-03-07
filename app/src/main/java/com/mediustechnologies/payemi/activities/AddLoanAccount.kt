package com.mediustechnologies.payemi.activities

import android.content.Context
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import com.mediustechnologies.payemi.adapters.inputParametersAdapter
import com.mediustechnologies.payemi.DTO.mandatoryParmsDTO
import com.google.gson.JsonObject
import android.os.Bundle
import com.mediustechnologies.payemi.commons.utils
import com.mediustechnologies.payemi.ApiResponse.inputParameterFeilds
import com.mediustechnologies.payemi.helper.RetrofitClient
import com.mediustechnologies.payemi.commons.urlconstants
import com.bumptech.glide.Glide
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONObject
import com.google.gson.JsonParser
import org.json.JSONException
import com.mediustechnologies.payemi.ApiResponse.fetchBill
import android.content.Intent
import android.util.Log
import android.view.View
import com.mediustechnologies.payemi.activities.EMIDetailsBillFetch
import com.mediustechnologies.payemi.databinding.ActivityInputParameterFeildsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.LinkedHashMap
import java.util.regex.Pattern

class AddLoanAccount : BaseAppCompatActivity() {
    private var binding: ActivityInputParameterFeildsBinding? = null
    private val context: Context = this
    private var url: String? = null
    private var biller_id: String? = null
    private var exactness: String? = null
    private var adapter: inputParametersAdapter? = null
    private var data: LinkedHashMap<String, mandatoryParmsDTO>? = null
    private val gsonObject: JsonObject? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputParameterFeildsBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
        inputParameters
    }

    private val inputParameters: Unit
        private get() {
            biller_id = intent.getStringExtra("biller_id")
            val token = utils.access_token
            val call = RetrofitClient().getInstance(
                context,
                urlconstants.AuthURL
            ).api.inputparameterfeilds(token, biller_id)
            call.enqueue(object : Callback<inputParameterFeilds?> {
                override fun onResponse(
                    call: Call<inputParameterFeilds?>,
                    response: Response<inputParameterFeilds?>
                ) {
                    if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                        if (response.body()!!.error == null || response.body()!!
                                .error.equals("false", ignoreCase = true)
                        ) {
                            val inputParameterFeilds = response.body()
                            val s = inputParameterFeilds.toString()
                            Log.d("tag", "InputParameterFeilds: $s")
                            binding!!.linearLayout.visibility = View.VISIBLE
                            binding!!.progressbar.visibility = View.GONE
                            recyclerview(inputParameterFeilds)
                            exactness = inputParameterFeilds!!.data.billerPaymentExactness
                            val url = inputParameterFeilds.data.logo
                            Glide.with(binding!!.Image).load(url).into(binding!!.Image)
                            Log.d("tag", "onResponse: Input Parameter Feilds$inputParameterFeilds")
                        } else {
                            try {
                                utils.errortoast(context, response.body()!!.message)
                            } catch (e: Exception) {
                                Log.e("tag", e.toString())
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<inputParameterFeilds?>, t: Throwable) {
                    Toast.makeText(context, "Fail to load input fields.", Toast.LENGTH_SHORT).show()
                    Log.e("tag", "add Loan Account api response failed")
                    binding!!.getDetails.visibility = View.GONE
                    binding!!.text.text = "Failed to load."
                }
            })
        }

    private fun recyclerview(inputParameterFeilds: inputParameterFeilds?) {
        val recyclerView = binding!!.inputparmsrecycler
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        data = inputParameterFeilds!!.data.mandatory
        if (data != null && data!!.size > 0) {
            adapter = inputParametersAdapter(data)
            recyclerView.adapter = adapter
        } else {
            binding!!.text.text = "No Mandatory Input Parameter Feilds"
        }
    }

    private fun init() {
        binding!!.linearLayout.visibility = View.GONE
        binding!!.progressbar.visibility = View.VISIBLE
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        url = intent.getStringExtra("url")
        Glide.with(binding!!.Image).load(url).into(binding!!.Image)
        binding!!.getDetails.setOnClickListener { view: View? -> nextScreen() }
    }

    private fun nextScreen() {
        binding!!.linearLayout.visibility = View.GONE
        binding!!.progressbar.visibility = View.VISIBLE
        val feilds = adapter!!.getfeilds()
        println("Feilds $feilds")
        if (verifydata(feilds)) //        if(true)
        {
            FetchBill(ApiJsonMap(feilds))
        }
    }

    private fun ApiJsonMap(feilds: LinkedHashMap<String, String>): JsonObject {
        var gsonObject = JsonObject()
        try {
            val jsonObj_ = JSONObject()
            for ((key1, value) in feilds) {
                val key = key1
                key.replace("_", " ")
                jsonObj_.put(key, value)
            }
            val jsonParser = JsonParser()
            gsonObject = jsonParser.parse(jsonObj_.toString()) as JsonObject

            //print parameter
            Log.d("tag", "AS PARAMETER  $gsonObject")
        } catch (e: JSONException) {
            Log.d("tag", "Add loan Account JSON exception line 154")
        }
        return gsonObject
    }

    private fun verifydata(feilds: LinkedHashMap<String, String>): Boolean {
        println(feilds)
        for ((key1) in data!!) {
            val key = key1
            val regex = data!![key]!!.regex
            val value = feilds[data!![key]!!.key]
            Log.d(
                "tag",
                "Pattern: " + regex + " Matcher Value: " + value + " Return: " + check(value, regex)
            )
            if (!check(value, regex)) {
                Log.d("tag", "Regex error for $key")
                Toast.makeText(context, "Pattern didn't matched for $key", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        }
        return true
    }

    private fun check(value: String?, regex: String?): Boolean {
        if (regex == null || regex == "null") {
            return true
        }
        val p = Pattern.compile(regex)
        val m = p.matcher(value)
        return m.matches()
    }

    private fun FetchBill(jsonObject: JsonObject) {
        val biller_id = intent.getStringExtra("biller_id")
        val id = utils.profileId
        val phone = utils.phone
        var s = ""
        s += "id -> $id\n"
        s += "biller_idbiller_id -> $biller_id\n"
        s += "phone -> $phone\n\n"
        s += "Body\n$jsonObject"
        Log.d("tag", "Bill Fetch Api call: $s")
        val call = RetrofitClient().getInstance(
            context,
            urlconstants.AuthURL
        ).api.billfetch(utils.access_token, id, biller_id, phone, jsonObject)
        call.enqueue(object : Callback<fetchBill?> {
            override fun onResponse(call: Call<fetchBill?>, response: Response<fetchBill?>) {
                if (response.code() == utils.RESPONSE_SUCCESS && response.body() != null) {
                    if (response.body()!!.error == null || response.body()!!
                            .error.equals("false", ignoreCase = true)
                    ) {
                        val bill = response.body()
                        utils.bill_id = bill!!.payload[0].id
                        val variableData = LinkedHashMap<String, String>()
                        variableData.putAll(bill.payload[0].amountOptions)
                        variableData.putAll(bill.payload[0].inputparams_value)
                        variableData.putAll(bill.payload[0].biller_additional_info)
                        val i = Intent(context, EMIDetailsBillFetch::class.java)
                        i.putExtra("url", url)
                        i.putExtra("variableData", variableData)
                        i.putExtra("bill", bill)
                        i.putExtra("exact", exactness)
                        i.putExtra("biller_name", intent.getStringExtra("biller_name"))
                        i.putExtra("biller_id", biller_id)
                        startActivity(i)
                    } else {
                        try {
                            utils.errortoast(context, response.body()!!.message)
                            Log.e("tagg", response.body()!!.message)
                        } catch (e: Exception) {
                            Log.e("tag", e.toString())
                        }
                    }
                } else {
                    Log.e("tag", "onResponse: " + response.code())
                }
                binding!!.linearLayout.visibility = View.VISIBLE
                binding!!.progressbar.visibility = View.GONE
            }

            override fun onFailure(call: Call<fetchBill?>, t: Throwable) {
                Log.d("tag", "onFailure: bill fetch$t")
                Toast.makeText(context, "Error fetching Bill", Toast.LENGTH_SHORT).show()
            }
        })
    }
}