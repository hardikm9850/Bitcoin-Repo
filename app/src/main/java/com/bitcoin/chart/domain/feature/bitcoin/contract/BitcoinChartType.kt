package com.bitcoin.chart.domain.feature.bitcoin.contract

sealed class BitcoinChartType {
    object TotalBitcoins : BitcoinChartType()
    object MarketPrice : BitcoinChartType()
    object BlockSize : BitcoinChartType()
}

