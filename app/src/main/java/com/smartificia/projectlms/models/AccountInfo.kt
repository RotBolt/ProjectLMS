package com.smartificia.projectlms.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Current Logged in User account info
 */
@Parcelize
data class AccountInfo(
    val usertype:String,
    val userid:String,
    val firstname:String,
    val lastname:String,
    val email:String
) : Parcelable