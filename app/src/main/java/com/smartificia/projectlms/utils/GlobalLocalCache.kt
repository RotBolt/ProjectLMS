package com.smartificia.projectlms.utils

import com.smartificia.projectlms.models.LeadInfo
import com.smartificia.projectlms.models.AccountInfo

object GlobalLocalCache {
    private var leadItems: List<LeadInfo>?=null

    private lateinit var thisAccountInfo: AccountInfo

    fun setAccountInfo(info: AccountInfo) {
        thisAccountInfo = info
    }

    fun getAccountInfo() = thisAccountInfo

    fun updateList(list: List<LeadInfo>) {
        leadItems = list
    }

    fun getList() = leadItems



}