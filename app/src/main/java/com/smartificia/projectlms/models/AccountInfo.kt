package com.smartificia.projectlms.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class AccountInfo(
    val usertype:String,
    val userid:String,
    val firstname:String,
    val lastname:String,
    val email:String
) : Parcelable