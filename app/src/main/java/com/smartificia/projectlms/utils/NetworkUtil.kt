package com.smartificia.projectlms.utils

import android.content.Context
import android.net.ConnectivityManager


/**
 *  Helper function to check the connectivity of Internet
 */
fun isConnected(context:Context?):Boolean{
    val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnected?:false
}