package com.bitcoin.chart.network.service

import com.bitcoin.chart.network.beans.BitcoinRaw
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BitcoinService {

    @GET("charts/{chartName}")
    suspend fun retrieveBitcoinChart(
        @Path("chartName") chartName: String,
        @Query("timespan") timeSpan: String,
        @Query("rollingAverage") rollingAverage: String? = null
    ): BitcoinRaw
}