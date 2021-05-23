package com.bitcoin.chart.domain.feature.bitcoin.model

data class BitcoinValue(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<BitcoinData>
)
