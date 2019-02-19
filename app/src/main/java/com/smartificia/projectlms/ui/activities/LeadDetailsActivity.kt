package com.smartificia.projectlms.ui.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.smartificia.projectlms.R
import com.smartificia.projectlms.models.LeadInfo
import com.smartificia.projectlms.utils.Labels
import com.smartificia.projectlms.utils.isConnected
import kotlinx.android.synthetic.main.activity_lead_details.*

class LeadDetailsActivity : AppCompatActivity() {

    private lateinit var leadInfo: LeadInfo

    private var updateFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lead_details)

        leadInfo = intent.getParcelableExtra<LeadInfo>(Labels.leadInfo)

        bindData(leadInfo)
        bindActions(leadInfo)

        setSupportActionBar(leadDetailsToolbar)
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


    private fun bindData(leadInfo: LeadInfo) {
        val initials = with(leadInfo.leadname) {
            val names = split(" ")

            if (names.size > 1) {
                " ${names[0][0]}${names[1][0]} ".toUpperCase()
            } else {
                "  ${names[0][0]}  ".toUpperCase()
            }
        }

        tvInitials.text = initials

        tvLeadName.text = leadInfo.leadname

        tvPhoneNumber.text = "  ${leadInfo.phone}"

        tvEmailAddress.text = "  ${leadInfo.emailid}"

        tvLeadSource.text = "Source: ${leadInfo.leadsource}"
        tvStatusDetails.text = "Status: ${leadInfo.statusname}"
        tvLeadCreated.text = "Created On: ${leadInfo.lead_create_on}"
        tvLeadConverted.text = "Converted On: ${leadInfo.lead_converted_on}"

        tvFollowUpTime.text = " Follow Up Time: ${leadInfo.followup_time}"
        tvMailDelivered.text = "  Mails Delivered: ${leadInfo.mail_del}"
        if (leadInfo.assigned != null)
            tvAssigned.text = "  Assigned To: ${leadInfo.assigned}"
        else
            tvAssigned.visibility = View.GONE
    }

    private fun bindActions(leadInfo: LeadInfo) {
        tvPhoneNumber.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${leadInfo.phone}")
            }
            startActivity(intent)
        }

        tvEmailAddress.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${leadInfo.emailid}")
            }
            startActivity(Intent.createChooser(intent, "Choose your option"))
        }

        fabEdit.setOnClickListener {
            if (isConnected(this)) {
                val intent = Intent(this, UpdateLeadInfoActivity::class.java).apply {
                    putExtra(Labels.leadInfo, leadInfo)
                }
                startActivityForResult(intent, 2505)
            } else {
                Toast.makeText(this, "You are offline", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2505 && resultCode == Activity.RESULT_OK) {
            data?.let {
                val leadUpdateInfo = it.getParcelableExtra<LeadInfo>(Labels.leadInfo)
                bindActions(leadUpdateInfo)
                bindData(leadUpdateInfo)
            }
        }
    }
}

