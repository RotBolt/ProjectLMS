package com.smartificia.projectlms.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.smartificia.projectlms.R
import com.smartificia.projectlms.api.ApiService
import com.smartificia.projectlms.models.*
import com.smartificia.projectlms.utils.GlobalLocalCache
import com.smartificia.projectlms.utils.Labels
import kotlinx.android.synthetic.main.activity_update_lead_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateLeadInfoActivity : AppCompatActivity() {


    private lateinit var userMap: Map<String, String>
    private lateinit var statusMap: Map<String, String>
    private lateinit var leadInfo: LeadInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_lead_info)

        leadInfo = intent.getParcelableExtra<LeadInfo>(Labels.leadInfo)
        setUpDropDowns(leadInfo)
        bindInitialData(leadInfo)
        bindActions(leadInfo)

        setSupportActionBar(leadUpdateToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindActions(leadInfo: LeadInfo) {
        btnUpdate.setOnClickListener {
            if (validateNotEmpty()) {
                val email = etEmail.text.toString()
                val followupTime = etFollowUp.text.toString()
                val mailDel = etMailDel.text.toString()
                val phoneNumber = etPhoneNumber.text.toString()

                val statusId = statusMap[statusSpinner.selectedItem]
                val assignTo = if (GlobalLocalCache.getAccountInfo().userid == "1") {
                    userMap[assignedSpinner.selectedItem]
                } else {
                    null
                }

                val leadAdminUpdateInfo = LeadUpdateInfo(
                    userid = GlobalLocalCache.getAccountInfo().userid,
                    enq_propdet = leadInfo.pero_details,
                    leadid = leadInfo.leadid,
                    leadconverted_on = leadInfo.lead_converted_on,
                    leadname = leadInfo.leadname,
                    phone = phoneNumber,
                    emailid = email,
                    leadsource = leadInfo.leadsource,
                    status = statusId.toString(),
                    mail_deliv = mailDel,
                    assigned_to = assignTo.toString(),
                    followup_time = followupTime
                )
                makeUpdateRequest(leadAdminUpdateInfo)
            }
        }
    }

    private fun makeUpdateRequest(leadUpdateInfo: LeadUpdateInfo) {

        updateLoader.visibility = View.VISIBLE
        btnUpdate.visibility = View.GONE
        val leadsApi = ApiService.getInstance().getLeadsApi()

        leadsApi.updateLeadInfoAdmin(
            userid = leadUpdateInfo.userid,
            phone = leadUpdateInfo.phone,
            emailId = leadUpdateInfo.emailid,
            status = leadUpdateInfo.status,
            assigned_to = leadUpdateInfo.assigned_to,
            followup_time = leadUpdateInfo.followup_time,
            mail_deliv = leadUpdateInfo.mail_deliv,
            leadid = leadUpdateInfo.leadid,
            leadsource = leadUpdateInfo.leadsource,
            leadname = leadUpdateInfo.leadname,
            leadconverted_on = leadUpdateInfo.leadconverted_on,
            enq_propdet = leadUpdateInfo.enq_propdet
        )
            .enqueue(object : Callback<GenericResponse<LeadUpdateResponse>> {
                override fun onFailure(call: Call<GenericResponse<LeadUpdateResponse>>, t: Throwable) {
                    updateLoader.visibility = View.GONE

                    Log.i("PUI", "failre : ${t.cause} ")
                    toShowErrorView()
                }

                override fun onResponse(
                    call: Call<GenericResponse<LeadUpdateResponse>>,
                    response: Response<GenericResponse<LeadUpdateResponse>>
                ) {
                    updateLoader.visibility = View.GONE
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.message == "success") {
                            ivUpdated.visibility = View.VISIBLE

                            val assigned = assignedSpinner.selectedItem?.toString()?: "null"
                            val leadInfo = with(leadUpdateInfo) {
                                com.smartificia.projectlms.models.LeadInfo(
                                    leadid = leadid,
                                    leadname = leadname,
                                    leadsource = leadsource,
                                    lead_create_on = leadInfo.lead_create_on,
                                    lead_converted_on = leadconverted_on,
                                    phone = phone,
                                    emailid = emailid,
                                    pero_details = enq_propdet,
                                    statusid = status,
                                    statusname = statusSpinner.selectedItem.toString(),
                                    mail_del = mail_deliv,
                                    followup_time = followup_time,
                                    assigned = assigned
                                )
                            }


                            val data = Intent().putExtra(Labels.leadInfo, leadInfo)
                            setResult(Activity.RESULT_OK, data)

                            Handler().postDelayed({ finish() }, 750)
                        } else {
                            btnUpdate.visibility = View.VISIBLE
                            Toast.makeText(this@UpdateLeadInfoActivity, responseBody.message, Toast.LENGTH_LONG).show()
                        }
                    } else {
                        toShowErrorView()
                        Log.i("PUI", "response null")
                    }
                }
            })
    }

    private fun validateNotEmpty() = when {
        etEmail.text.isEmpty() -> {
            etEmail.hint = "Can't be Empty"
            false
        }
        etFollowUp.text.isEmpty() -> {
            etFollowUp.hint = "Can't be Empty"
            false
        }
        etMailDel.text.isEmpty() -> {
            etMailDel.hint = "Can't be Empty"
            false
        }
        etPhoneNumber.text.isEmpty() -> {
            etPhoneNumber.hint = "Can't be Empty"
            false
        }
        else -> true
    }


    private fun bindInitialData(leadInfo: LeadInfo) {

        etPhoneNumber.setText(leadInfo.phone, TextView.BufferType.EDITABLE)
        etMailDel.setText(leadInfo.mail_del, TextView.BufferType.EDITABLE)
        etFollowUp.setText(leadInfo.followup_time, TextView.BufferType.EDITABLE)
        etEmail.setText(leadInfo.emailid, TextView.BufferType.EDITABLE)
    }

    private fun setUpDropDowns(leadInfo: LeadInfo) {
        val infoApi = ApiService.getInstance().getInfoApi()

        if (GlobalLocalCache.getAccountInfo().userid == "1") {
            infoApi.getUserDetails().enqueue(object : Callback<GenericResponse<AccountInfo>> {
                override fun onFailure(call: Call<GenericResponse<AccountInfo>>, t: Throwable) {
                    toShowErrorView()
                }

                override fun onResponse(
                    call: Call<GenericResponse<AccountInfo>>,
                    response: Response<GenericResponse<AccountInfo>>
                ) {
                    val usersResponse = response.body()
                    if (usersResponse != null) {
                        val list = usersResponse.response

                        userMap = list.map { "${it.firstname} ${it.lastname}" to it.userid }.toMap()
                        val nameList = list.map { accountInfo -> "${accountInfo.firstname} ${accountInfo.lastname}" }
                        val userAdapter = ArrayAdapter(
                            this@UpdateLeadInfoActivity,
                            android.R.layout.simple_spinner_item,
                            nameList
                        )
                        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        assignedSpinner.adapter = userAdapter

                        val position = nameList.indexOf(leadInfo.assigned)
                        assignedSpinner.setSelection(position)
                    } else {
                        toShowErrorView()
                    }
                }
            })
        } else {
            tvAssignedLabel.visibility = View.GONE
            assignedSpinner.visibility = View.GONE
        }



        infoApi.getStatusDetails().enqueue(object : Callback<GenericResponse<StatusDetails>> {
            override fun onFailure(call: Call<GenericResponse<StatusDetails>>, t: Throwable) {
                toShowErrorView()
            }

            override fun onResponse(
                call: Call<GenericResponse<StatusDetails>>,
                response: Response<GenericResponse<StatusDetails>>
            ) {
                val statusResponse = response.body()
                if (statusResponse != null) {
                    val list = statusResponse.response
                    val statusNames = list.map { statusDetails -> statusDetails.statusname }
                    statusMap = list.map { it.statusname to it.statusid }.toMap()
                    val statusAdapter = ArrayAdapter(
                        this@UpdateLeadInfoActivity,
                        android.R.layout.simple_spinner_item,
                        statusNames
                    )
                    statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    statusSpinner.adapter = statusAdapter

                    val position = statusNames.indexOf(leadInfo.statusname)
                    statusSpinner.setSelection(position)
                } else {
                    toShowErrorView()
                }
            }

        })
    }

    private fun toShowErrorView() {
        nwFailView.visibility = View.VISIBLE
        updateContainer.visibility = View.GONE
        btnUpdate.visibility = View.GONE
    }
}