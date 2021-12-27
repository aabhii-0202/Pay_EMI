package com.mediustechnologies.payemi.helper

import android.os.Bundle
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val yr = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val date = c[Calendar.DATE]
        return DatePickerDialog(requireActivity(), activity as OnDateSetListener?, yr, month, date)
    }
}