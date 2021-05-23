package com.bitcoin.chart.domain.common

import com.bitcoin.chart.network.beans.BitcoinRaw

abstract class BaseChartUseCase : BaseUseCase<BaseChartUseCase.Params, BitcoinRaw>() {
    data class Params(val chartName: String, val timeSpan: String, val rollingAverage: String?)
}