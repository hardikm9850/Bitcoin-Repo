package com.bitcoin.chart.network.beans

import com.google.gson.annotations.SerializedName

/**
 * Model class for the Bitcoin api response.
 * Example URLs :
 * https://api.blockchain.info/charts/total-bitcoins?timespan=30days&rollingAverage=8hours&format=json
 * https://api.blockchain.info/charts/market-price?timespan=30days&rollingAverage=8hours&format=json
 * https://api.blockchain.info/charts/block-size?timespan=30days&rollingAverage=8hours&format=json
 *
 */
data class BitcoinRaw(
    val status: String?,
    val name: String?,
    val unit: String?,
    val period: String?,
    val description: String?,
    val values: List<BitcoinValueRaw?>?
)

/**
 * Model class for the chart values:
 * x is the timestamp in epoch format
 * y is the value, which could be one of the following 4:
 * Total bitcoins
 * Market price
 * Block size
 *
 */
data class BitcoinValueRaw(
    @SerializedName("x")
    val timestamp: Long?,

    @SerializedName("y")
    val yAxisValue: Float?
)