package com.bitcoin.chart.util.extension

import androidx.core.content.ContextCompat
import com.bitcoin.chart.R
import com.bitcoin.chart.domain.common.customui.BitcoinLineChartMarkerView
import com.bitcoin.chart.domain.feature.bitcoin.model.BitcoinViewEntity
import com.bitcoin.chart.util.constants.ChartHelper.SHORT_DATE_FORMAT
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

fun LineChart.drawChart(
    viewEntity: BitcoinViewEntity?,
    animationDuration: Int = 1500
) {
    viewEntity?.let {
        // X axis
        val xAxis = this.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setLabelCount(it.xAxisLabelCount, true)
        xAxis.setValueFormatter { value, _ ->
            value.toLong().getFormattedDateFromTimestamp(SHORT_DATE_FORMAT)
        }

        // Y axis
        this.axisLeft.granularity = 1f
        this.axisRight.isEnabled = false

        // Data set
        val dataSetElementsColor = ContextCompat.getColor(context, R.color.teal_200)
        val dataSet = LineDataSet(it.entryList, it.currency)
        dataSet.setDrawValues(false)
        dataSet.color = dataSetElementsColor
        dataSet.valueTextColor = dataSetElementsColor
        dataSet.highLightColor = dataSetElementsColor
        dataSet.setCircleColor(dataSetElementsColor)

        // Chart
        this.setScaleEnabled(false)

        // The description is getting cut if the length doesn't fit in single line on screen
        // Tried to concatenate with new line character, but at the time of drawing it removes it
        this.description.text = it.description

        this.data = LineData(dataSet)
        this.marker = BitcoinLineChartMarkerView(it.currency, context)
        this.animateX(animationDuration)
    }
}