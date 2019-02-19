package com.smartificia.projectlms.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.smartificia.projectlms.R
import com.smartificia.projectlms.ui.adapters.LeadsAdapter
import com.smartificia.projectlms.ui.fragments.DatePickerFragment
import com.smartificia.projectlms.utils.GlobalLocalCache
import com.smartificia.projectlms.utils.convertToDatedList
import kotlinx.android.synthetic.main.activity_calendar_filter.*
import java.util.*


data class Date(val YYYY: Int, val MM: Int, val DD: Int) {
    companion object {
        fun fromString(dateString: String): Date {
            val (YYYY, MM, DD) = dateString.split("-")
            return Date(YYYY.toInt(), MM.toInt(), DD.toInt())
        }
    }
}

class CalendarFilterActivity : AppCompatActivity() {

    private var toDate: Date = Date(-1, -1, -1)
    private var fromDate: Date = Date(-1, -1, -1)

    private lateinit var leadsAdapter: LeadsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_filter)


        setUpComponents()
        val list = GlobalLocalCache.getList()

        list?.let {
            val datedList = convertToDatedList(it)
            leadsAdapter = LeadsAdapter()
            leadsAdapter.submitList(datedList)

            rvFilteredLeads.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            rvFilteredLeads.adapter = leadsAdapter
        }
    }

    private fun setUpComponents() {
        ivBack.setOnClickListener { finish() }

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }
        tvFromDate.text = with(calendar) {
            "${get(Calendar.YEAR)}-" +
                    "${get(Calendar.MONTH) + 1}-".padStart(3, '0') +
                    "${get(Calendar.DAY_OF_MONTH)}".padStart(2, '0')
        }
        tvToDate.text = "Not Set"

        fromDate = with(calendar) {
            Date(get(Calendar.YEAR), get(Calendar.MONTH) + 1, get(Calendar.DAY_OF_MONTH))
        }
        tvFromDate.setOnClickListener {
            val picker = DatePickerFragment()
            picker.setOnDateSet { yyyy, mm, dd ->
                val dateString = "$yyyy-" + "${mm + 1}-".padStart(3, '0') + "$dd".padStart(2, '0')
                tvFromDate.text = dateString
                fromDate = Date(yyyy, mm + 1, dd)
            }
            picker.show(supportFragmentManager, "datepick")

        }
        tvToDate.setOnClickListener {
            val picker = DatePickerFragment()
            picker.setOnDateSet { yyyy, mm, dd ->
                if (validateToDate(yyyy, mm+1, dd)) {
                    val dateString = "$yyyy-" + "${mm + 1}-".padStart(3, '0') + "$dd".padStart(2, '0')
                    tvToDate.text = dateString
                    toDate = Date(yyyy, mm + 1, dd)
                } else {
                    tvToDate.text = "Can't Set Date in Reverse"
                }
            }
            picker.show(supportFragmentManager, "datepick")
        }

        btnFilter.setOnClickListener {
            if (fromDate.YYYY != -1 && toDate.YYYY != -1) {
                val list = GlobalLocalCache.getList()
                list?.let {
                    val filtered = it.filter {
                        val thisDate = Date.fromString(it.lead_create_on.substring(0, 10))

                        val res = fromDate.DD <= thisDate.DD && thisDate.DD <= toDate.DD &&
                                fromDate.MM <= thisDate.MM && thisDate.MM <= toDate.MM &&
                                fromDate.YYYY <= thisDate.YYYY && thisDate.YYYY <= toDate.YYYY

                        res
                    }

                    val datedList = convertToDatedList(filtered)
                    leadsAdapter.submitList(datedList)

                    if (filtered.isEmpty()) {
                        tvNoResults.visibility = View.VISIBLE
                    } else {
                        tvNoResults.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun validateToDate(yyyy: Int, mm: Int, dd: Int): Boolean {
        return yyyy >= fromDate.YYYY && mm >= fromDate.MM && dd >= fromDate.DD
    }
}



