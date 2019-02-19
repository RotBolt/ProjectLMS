package com.smartificia.projectlms.ui.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(context, this, year, month, day)
    }

    private lateinit var onDateSet: (yyyy: Int, mm: Int, dd: Int) -> Unit

    fun setOnDateSet(p: (Int, Int, Int) -> Unit) {
        onDateSet = p
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        onDateSet(year, month, day)
    }
}