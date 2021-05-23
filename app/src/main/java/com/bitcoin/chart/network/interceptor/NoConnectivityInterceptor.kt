package com.bitcoin.chart.network.interceptor

import android.net.ConnectivityManager
import com.bitcoin.chart.domain.exception.NoConnectivityException
import com.bitcoin.chart.util.extension.isConnected
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * An [Interceptor] that throws a [NoConnectivityException] if it is detected
 * that the [connectivityManager] has no active internet connection.
 */
class NoConnectivityInterceptor @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (isConnected()) {
            return chain.proceed(chain.request())
        }
        throw NoConnectivityException()
    }

    private fun isConnected() = connectivityManager.isConnected()
}