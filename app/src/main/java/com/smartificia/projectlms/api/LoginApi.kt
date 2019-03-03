package com.smartificia.projectlms.api

import com.smartificia.projectlms.models.AccountInfo
import com.smartificia.projectlms.models.GenericResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 *  API Interface for Logging in User
 *
 *  actions related to login (like validate) should go here
 *
 */

interface LoginApi {

    @GET("/api/apisan.php?op=login")
    fun validateLogin(
        @Query(value = "username") username: String,
        @Query(value = "password") password: String
    ): Call<GenericResponse<AccountInfo>>
}