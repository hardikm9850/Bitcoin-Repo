package com.bitcoin.chart.util.extension

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun ConnectivityManager.isConnected(): Boolean {
    val activeNetwork = this.activeNetwork ?: return false
    val networkCapabilities = this.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}