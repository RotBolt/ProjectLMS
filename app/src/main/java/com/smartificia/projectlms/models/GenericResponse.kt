package com.smartificia.projectlms.models

import com.google.gson.annotations.SerializedName


/**
 * A generic response of API requests
 *
 * Note : If API request response is differnt from this structure, then you may have to create different response model
 */

data class GenericResponse<T>(
    @SerializedName("response")
    val response: ArrayList<T>,
    @SerializedName("response_code")
    val response_code:String,
    @SerializedName("message")
    val message:String
)