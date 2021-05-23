package com.bitcoin.chart.domain.common.customui

import android.content.Context
import android.graphics.Canvas
import android.view.LayoutInflater
import com.bitcoin.chart.R
import com.bitcoin.chart.databinding.LayoutLinechartMarkerviewBinding
import com.bitcoin.chart.util.constants.ChartHelper.LONG_DATE_FORMAT
import com.bitcoin.chart.util.constants.ChartHelper.THOUSANDS_SEPARATOR
import com.bitcoin.chart.util.extension.applyThousandsSeparator
import com.bitcoin.chart.util.extension.getFormattedDateFromTimestamp
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class BitcoinLineChartMarkerView(private val currency: String, context: Context) :
    MarkerView(
        context,
        R.layout.layout_linechart_markerview
    ) {

    private var binding: LayoutLinechartMarkerviewBinding =
        LayoutLinechartMarkerviewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    /**
     * Width of the device's screen.
     */
    private val screenWidthInPx = resources.displayMetrics.widthPixels

    /**
     * Offset used to position the [MarkerView] on the graph.
     */
    private var customOffset: MPPointF? = null

    override fun refreshContent(entry: Entry, highlight: Highlight) {

        val formattedDate = entry.x.toLong().getFormattedDateFromTimestamp(LONG_DATE_FORMAT)
        val formattedAmount = entry.y.toLong().applyThousandsSeparator(THOUSANDS_SEPARATOR)
        val amountWithCurrency = "$currency: $formattedAmount"

        binding.tvTop.text = formattedDate
        binding.tvBottom.text = amountWithCurrency

        super.refreshContent(entry, highlight)
    }

    override fun getOffset(): MPPointF {

        if (customOffset == null) {
            customOffset = MPPointF(-(width / 2).toFloat(), -height.toFloat())
        }

        return customOffset as MPPointF
    }

    override fun draw(canvas: Canvas, posX: Float, posY: Float) {

        var newPosX = posX

        val width = width
        if (screenWidthInPx - newPosX - width < width) {
            newPosX -= width.toFloat()
        }

        canvas.translate(newPosX, posY)
        draw(canvas)
        canvas.translate(-newPosX, -posY)
    }
}
