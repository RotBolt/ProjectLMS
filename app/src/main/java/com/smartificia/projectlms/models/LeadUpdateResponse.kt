package com.smartificia.projectlms.models

import com.google.gson.annotations.SerializedName

data class LeadUpdateResponse(
    @SerializedName("leadid")
    val leadid:String
)