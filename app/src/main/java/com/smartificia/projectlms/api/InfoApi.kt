package com.smartificia.projectlms.api

import com.smartificia.projectlms.models.AccountInfo
import com.smartificia.projectlms.models.GenericResponse
import com.smartificia.projectlms.models.StatusDetails
import retrofit2.Call
import retrofit2.http.GET

interface InfoApi {

    @GET("/api/apisan.php?op=teamdet&usertype=1")
    fun getUserDetails(): Call<GenericResponse<AccountInfo>>

    @GET("/api/apisan.php?op=statusdet")
    fun getStatusDetails(): Call<GenericResponse<StatusDetails>>
}