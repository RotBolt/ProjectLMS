package com.smartificia.projectlms.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class LeadUpdateInfo(
    val userid:String,
    val leadid: String,
    val leadname: String,
    val leadconverted_on: String,
    val phone: String,
    val emailid: String,
    val leadsource: String,
    val enq_propdet: String, // pero_details in LeadInfo
    val status: String,
    val mail_deliv: String,
    val assigned_to: String,
    val followup_time: String
) : Parcelable