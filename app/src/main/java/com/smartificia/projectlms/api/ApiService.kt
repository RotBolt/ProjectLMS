package com.smartificia.projectlms.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService private constructor() {

    private var loginApi: LoginApi
    private var leadsApi: LeadsApi
    private var infoApi: InfoApi

    fun getLoginApi() = loginApi
    fun getLeadsApi() = leadsApi
    fun getInfoApi() = infoApi

    companion object {
        @Volatile
        private var apiInstance: ApiService? = null

        fun getInstance(): ApiService = apiInstance ?: ApiService()
    }

    init {

        val gson = GsonBuilder().setLenient().create()
        val retrofitInstance = Retrofit.Builder()
            .baseUrl("http://www.shanthiinfra.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        loginApi = retrofitInstance.create(LoginApi::class.java)
        leadsApi = retrofitInstance.create(LeadsApi::class.java)
        infoApi = retrofitInstance.create(InfoApi::class.java)
    }
}