package com.smartificia.projectlms.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *  Data Model holding LEAD DETAILS
 *  These are details to be shown to users
 */
@Parcelize
data class LeadInfo(
    val leadid: String,
    val leadname: String,
    val lead_create_on: String,
    val lead_converted_on: String,
    val phone: String,
    val emailid: String,
    val leadsource: String,
    val pero_details: String,
    val statusid: String,
    val statusname: String,
    val mail_del: String,
    val assigned: String?,
    val followup_time: String
) : ListItem(), Parcelable {
    override fun id() = leadid

    override fun getType() = ListItem.TYPE_LEAD
}