package com.bitcoin.chart.util.extension

import java.sql.Date
import java.sql.Timestamp
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.getFormattedDateFromTimestamp(datePattern: String): String {

    val timestamp = Timestamp(this * 1000)
    val date = Date(timestamp.time)
    val englishLocale = Locale("en", "us")

    return try {
        val dateFormat = SimpleDateFormat(datePattern, englishLocale)
        dateFormat.format(date)
    } catch (e: Exception) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val month = calendar.get(Calendar.MONTH)
        val monthName = DateFormatSymbols(englishLocale).shortMonths[month]
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        "$dayOfMonth. $monthName" // Default format, in case there is an issue applying the pattern.
    }
}

fun Long.applyThousandsSeparator(separator: Char): String {

    val formatter = NumberFormat.getInstance() as DecimalFormat
    val symbols = formatter.decimalFormatSymbols.apply {
        groupingSeparator = separator
    }
    formatter.decimalFormatSymbols = symbols
    return formatter.format(this)
}
