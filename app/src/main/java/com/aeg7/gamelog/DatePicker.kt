package com.aeg7.gamelog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePicker:DialogFragment(),DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c=Calendar.getInstance()
        val year= c.get(Calendar.YEAR)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month= c.get(Calendar.MONTH)
        return activity?.let { DatePickerDialog(it,this,day,month,year) }!!
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }
}