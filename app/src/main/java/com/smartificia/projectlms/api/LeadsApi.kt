package com.smartificia.projectlms.api

import com.smartificia.projectlms.models.GenericResponse
import com.smartificia.projectlms.models.LeadInfo
import com.smartificia.projectlms.models.LeadUpdateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query


/**
 *  API interface for accessing Leads Details
 *  All actions related to Leads details should go here
 */

interface LeadsApi {

    @GET("/api/apisan.php?op=leaddet")
    fun getLeads(@Query(value = "userid") userid: String): Call<GenericResponse<LeadInfo>>


    @PUT("/api/apisan.php?op=leadupdate")
    fun updateLeadInfo(
        @Query("userid") userid: String,
        @Query("leadid") leadid:String,
        @Query("leadname") leadname:String,
        @Query("leadconverted_on")leadconverted_on:String,
        @Query("leadsource")leadsource:String,
        @Query("enq_propdet")enq_propdet:String,
        @Query("mail_deliv") mail_deliv: String,
        @Query("phone") phone: String,
        @Query("emailid") emailId: String,
        @Query("status") status: String,
        @Query("assigned_to") assigned_to: String,
        @Query("followup_time") followup_time: String
    ): Call<GenericResponse<LeadUpdateResponse>>

}