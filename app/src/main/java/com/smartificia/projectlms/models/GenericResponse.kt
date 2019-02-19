package com.smartificia.projectlms.models

import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(
    @SerializedName("response")
    val response: ArrayList<T>,
    @SerializedName("response_code")
    val response_code:String,
    @SerializedName("message")
    val message:String
)