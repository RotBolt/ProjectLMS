package com.smartificia.projectlms.models

import com.google.gson.annotations.SerializedName


/**
 * Response Model of Updating Lead details
 */

data class LeadUpdateResponse(
    @SerializedName("leadid")
    val leadid:String
)