package com.bitcoin.chart.ui.chart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitcoin.chart.domain.common.BaseChartUseCase
import com.bitcoin.chart.domain.feature.bitcoin.usecase.FetchBlockSizeUseCase
import com.bitcoin.chart.domain.feature.bitcoin.usecase.FetchMarketPriceUseCase
import com.bitcoin.chart.domain.feature.bitcoin.usecase.FetchTotalBitcoinsUseCase
import com.bitcoin.chart.domain.exception.NoConnectivityException
import com.bitcoin.chart.domain.feature.bitcoin.mapper.BitcoinDataMapper
import com.bitcoin.chart.domain.feature.bitcoin.model.BitcoinViewEntity
import com.bitcoin.chart.domain.feature.bitcoin.contract.BitcoinChartType
import com.bitcoin.chart.ui.common.UIController
import com.bitcoin.chart.util.constants.ChartNames
import com.bitcoin.chart.util.constants.ChartNames.CHART_DEFAULT_TIME_SPAN
import com.bitcoin.chart.util.extension.doToggleLoadingStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val fetchTotalBitcoinsUseCase: FetchTotalBitcoinsUseCase,
    private val fetchMarketPriceUseCase: FetchMarketPriceUseCase,
    private val fetchBlockSizeUseCase: FetchBlockSizeUseCase,
    private val bitcoinDataMapper: BitcoinDataMapper
) : ViewModel(), CoroutineScope, UIController {

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    val shouldShowProgressbar = MutableLiveData<Boolean>()
    val shouldShowNoInternetAlert = MutableLiveData<Boolean>()
    val bitcoinViewData = MutableLiveData<BitcoinViewEntity>()

    init {
        retrieveBitcoinMarketInfo(BitcoinChartType.TotalBitcoins)
    }

    fun retrieveBitcoinMarketInfo(chartType: BitcoinChartType) {
        val chartUseCase = getChartUseCaseFromType(chartType)
        val chartName = getChartNameFromChartType(chartType)
        chartUseCase.build(
            BaseChartUseCase.Params(chartName, CHART_DEFAULT_TIME_SPAN, null)
        ).doToggleLoadingStateOf(this)
            .map {
                bitcoinDataMapper.convert(it)
            }
            .onEach {
                bitcoinViewData.postValue(it)
            }
            .catch {
                if (it is NoConnectivityException) {
                    showNoInternetMessage()
                }
            }
            .launchIn(this)
    }

    private fun getChartNameFromChartType(chartType: BitcoinChartType) =
        when (chartType) {
            is BitcoinChartType.TotalBitcoins -> ChartNames.CHART_NAME_TOTAL_BITCOIN
            is BitcoinChartType.BlockSize -> ChartNames.CHART_NAME_BLOCK_SIZE
            is BitcoinChartType.MarketPrice -> ChartNames.CHART_NAME_MARKET_PRICE
        }

    private fun getChartUseCaseFromType(chartType: BitcoinChartType) =
        when (chartType) {
            is BitcoinChartType.TotalBitcoins -> fetchTotalBitcoinsUseCase
            is BitcoinChartType.BlockSize -> fetchMarketPriceUseCase
            is BitcoinChartType.MarketPrice -> fetchBlockSizeUseCase
        }

    override fun setLoading(shouldLoad: Boolean) {
        shouldShowProgressbar.postValue(shouldLoad)
    }

    override fun showNoInternetMessage() {
        shouldShowNoInternetAlert.postValue(true)
    }
}