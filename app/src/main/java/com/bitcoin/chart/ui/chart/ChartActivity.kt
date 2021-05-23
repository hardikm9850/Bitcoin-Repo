package com.bitcoin.chart.ui.chart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bitcoin.chart.R
import com.bitcoin.chart.databinding.ActivityBitcoinChartBinding
import com.bitcoin.chart.domain.feature.bitcoin.model.BitcoinViewEntity
import com.bitcoin.chart.domain.feature.bitcoin.contract.BitcoinChartType
import com.bitcoin.chart.util.extension.drawChart
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBitcoinChartBinding

    private val bitcoinChartViewModel: ChartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding = ActivityBitcoinChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationView()
        setupClickListener()
        observeData()
    }

    private fun setupClickListener() {
        binding.retryLayout.btnRetry.setOnClickListener {
            bitcoinChartViewModel.retrieveBitcoinMarketInfo(
                findNavigationPositionById(
                    binding.bottomNavigationView.selectedItemId
                )
            )
        }
    }

    private fun observeData() {
        with(bitcoinChartViewModel) {
            bitcoinViewData.observe(this@ChartActivity) {
                toggleVisibilityOfViews(
                    shouldShowProgressBar = false,
                    shouldShowChart = true,
                    shouldShowNoInternetAlert = false
                )
                bindDataToChart(it)
            }
            shouldShowNoInternetAlert.observe(this@ChartActivity) {
                toggleVisibilityOfViews(
                    shouldShowProgressBar = false,
                    shouldShowChart = !it,
                    shouldShowNoInternetAlert = it
                )
            }
            shouldShowProgressbar.observe(this@ChartActivity) {
                toggleVisibilityOfViews(
                    shouldShowProgressBar = it,
                    shouldShowChart = !it,
                    shouldShowNoInternetAlert = false
                )
            }
        }
    }

    private fun toggleVisibilityOfViews(
        shouldShowProgressBar: Boolean,
        shouldShowNoInternetAlert: Boolean,
        shouldShowChart: Boolean
    ) {
        binding.retryLayout.root.isVisible = shouldShowNoInternetAlert
        binding.chart.isVisible = shouldShowChart
        binding.progressBar.isVisible = shouldShowProgressBar
    }

    private fun bindDataToChart(bitcoinViewEntity: BitcoinViewEntity?) {
        binding.tvTitle.text = getNavigationTitleById(binding.bottomNavigationView.selectedItemId)
        bitcoinViewEntity?.let {
            binding.chart.drawChart(it)
        }
    }

    private fun getNavigationTitleById(selectedItemId: Int) =
        when (selectedItemId) {
            R.id.btm_nav_item_total_bitcoin -> getString(R.string.total_bitcoins)
            R.id.btm_nav_item_market_price -> getString(R.string.market_price)
            R.id.btm_nav_item_block_size -> getString(R.string.block_size)
            else -> getString(R.string.total_bitcoins)
        }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.apply {
            setOnNavigationItemSelectedListener { item ->
                binding.tvTitle.text = item.title
                bitcoinChartViewModel.retrieveBitcoinMarketInfo(
                    findNavigationPositionById(
                        item.itemId
                    )
                )
                true
            }
        }
    }

    private fun findNavigationPositionById(itemId: Int) = when (itemId) {
        R.id.btm_nav_item_total_bitcoin -> BitcoinChartType.TotalBitcoins
        R.id.btm_nav_item_market_price -> BitcoinChartType.MarketPrice
        R.id.btm_nav_item_block_size -> BitcoinChartType.BlockSize
        else -> BitcoinChartType.TotalBitcoins
    }
}