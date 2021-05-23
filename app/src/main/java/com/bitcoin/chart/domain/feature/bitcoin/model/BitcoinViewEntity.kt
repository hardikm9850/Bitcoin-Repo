package com.bitcoin.chart.domain.feature.bitcoin.model

import com.github.mikephil.charting.data.Entry

class BitcoinViewEntity(
    val entryList: List<Entry>,
    val xAxisLabelCount: Int,
    val currency: String,
    val description: String
)