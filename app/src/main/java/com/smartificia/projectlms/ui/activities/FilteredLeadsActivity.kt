package com.smartificia.projectlms.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.smartificia.projectlms.R
import com.smartificia.projectlms.ui.adapters.LeadsAdapter
import com.smartificia.projectlms.utils.GlobalLocalCache
import com.smartificia.projectlms.utils.Labels
import com.smartificia.projectlms.utils.convertToDatedAssignedList
import com.smartificia.projectlms.utils.convertToDatedList
import kotlinx.android.synthetic.main.activity_filtered_leads.*

class FilteredLeadsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_leads)

        val statusType = intent.getStringExtra(Labels.statusType)
        toolbarFiltered.title = "${toolbarFiltered.title} $statusType"

        val leadsAdapter = LeadsAdapter()
        rvFilteredLeads.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        val filteredList = GlobalLocalCache.getList()?.filter { it.statusname == statusType }

        if (filteredList != null && filteredList.isNotEmpty()) {
            val filteredDatedList =
                if (GlobalLocalCache.getAccountInfo().userid != "1")
                    convertToDatedList(filteredList)
                else
                    convertToDatedAssignedList(filteredList)
            rvFilteredLeads.adapter = leadsAdapter
            leadsAdapter.submitList(filteredDatedList)
            toggleViews(filteredDatedList.isNotEmpty())
        } else {
            toggleViews(false)
        }

        setSupportActionBar(toolbarFiltered)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }

    private fun toggleViews(isNotEmpty: Boolean) {
        if (isNotEmpty) {
            rvFilteredLeads.visibility = View.VISIBLE
            ivEmptyBin.visibility = View.GONE
            tvNoLeads.visibility = View.GONE
        } else {
            rvFilteredLeads.visibility = View.GONE
            ivEmptyBin.visibility = View.VISIBLE
            tvNoLeads.visibility = View.VISIBLE
        }
    }


}
