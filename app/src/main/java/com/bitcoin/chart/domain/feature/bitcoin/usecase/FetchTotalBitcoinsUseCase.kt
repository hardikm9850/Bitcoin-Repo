package com.bitcoin.chart.domain.feature.bitcoin.usecase

import com.bitcoin.chart.domain.common.BaseChartUseCase
import com.bitcoin.chart.network.beans.BitcoinRaw
import com.bitcoin.chart.network.service.BitcoinService
import com.bitcoin.chart.util.extension.flowSingle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTotalBitcoinsUseCase @Inject constructor(private val bitcoinService: BitcoinService) :
    BaseChartUseCase() {
    override fun onBuild(params: Params): Flow<BitcoinRaw> {
        return flowSingle {
            bitcoinService.retrieveBitcoinChart(
                params.chartName, params.timeSpan, params.rollingAverage
            )
        }
    }
}