package com.bitcoin.chart.ui.chart

import androidx.lifecycle.Observer
import com.bitcoin.chart.domain.exception.NoConnectivityException
import com.bitcoin.chart.domain.feature.bitcoin.contract.BitcoinChartType
import com.bitcoin.chart.domain.feature.bitcoin.mapper.BitcoinDataMapper
import com.bitcoin.chart.domain.feature.bitcoin.usecase.FetchBlockSizeUseCase
import com.bitcoin.chart.domain.feature.bitcoin.usecase.FetchMarketPriceUseCase
import com.bitcoin.chart.domain.feature.bitcoin.usecase.FetchTotalBitcoinsUseCase
import com.bitcoin.chart.util.extension.doToggleLoadingStateOf
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ChartViewModelTest {

    lateinit var viewModel: ChartViewModel

    @Mock
    lateinit var fetchTotalBitcoinsUseCase: FetchTotalBitcoinsUseCase

    @Mock
    lateinit var fetchMarketPriceUseCase: FetchMarketPriceUseCase

    @Mock
    lateinit var fetchBlockSizeUseCase: FetchBlockSizeUseCase

    @Mock
    lateinit var bitcoinDataMapper: BitcoinDataMapper

    @Mock
    lateinit var noInternetAlertObserver: Observer<Boolean>

    @Before
    fun setUp() {
        viewModel = ChartViewModel(
            fetchTotalBitcoinsUseCase,
            fetchMarketPriceUseCase,
            fetchBlockSizeUseCase,
            bitcoinDataMapper
        )

        viewModel.shouldShowNoInternetAlert.observeForever(noInternetAlertObserver)
    }

    @Test(expected = MockitoException::class)
    fun `should show no internet connection for internet absence`() {
        whenever(fetchTotalBitcoinsUseCase.build(any())).thenThrow()

        viewModel.retrieveBitcoinMarketInfo(BitcoinChartType.TotalBitcoins)
        Mockito.verify(noInternetAlertObserver).onChanged(true)
    }
}