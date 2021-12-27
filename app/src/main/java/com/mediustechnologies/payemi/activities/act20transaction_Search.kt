package com.mediustechnologies.payemi.activities

import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import android.content.Intent
import android.view.View
import androidx.fragment.app.DialogFragment
import com.mediustechnologies.payemi.activities.login.act28onBording
import com.mediustechnologies.payemi.databinding.ActivityTaransactionSearchBinding
import com.mediustechnologies.payemi.helper.DatePickerFragment

class act20transaction_Search : AppCompatActivity(), OnDateSetListener {
    private var binding: ActivityTaransactionSearchBinding? = null
    private var from = false
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaransactionSearchBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, date: Int) {
        val s = "$date-$month-$year"
        if (from) {
            binding!!.fromtext.text = s
        } else {
            binding!!.totext.text = s
        }
    }

    private fun init() {
        binding!!.details.visibility = View.VISIBLE
        binding!!.radioTransaction.setOnClickListener { view: View? ->
            if (binding!!.radioTransaction.isChecked) {
                binding!!.mobileNumberSelected.visibility = View.GONE
                binding!!.transactionIdselected.visibility = View.VISIBLE
                binding!!.details.visibility = View.GONE
            }
        }
        binding!!.radioMobile.setOnClickListener { view: View? ->
            if (binding!!.radioMobile.isChecked) {
                binding!!.mobileNumberSelected.visibility = View.VISIBLE
                binding!!.transactionIdselected.visibility = View.GONE
                binding!!.details.visibility = View.GONE
            }
        }
        binding!!.search.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context,
                    act28onBording::class.java
                )
            )
        }
        binding!!.layoutfrom.setOnClickListener { View: View? ->
            from = true
            val datepicker: DialogFragment = DatePickerFragment()
            datepicker.show(supportFragmentManager, "date picker")
        }
        binding!!.layoutto.setOnClickListener { view: View? ->
            from = false
            val datepicker: DialogFragment = DatePickerFragment()
            datepicker.show(supportFragmentManager, "date picker")
        }
    }
}