package com.samuelseptiano.multipointmap

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by samuel.septiano on 27/03/2025.
 */

    fun dateToNumeric(dt: Date): Double {
        val df: DateFormat = SimpleDateFormat("yyyyMMdd", Locale.US)
        return df.format(dt).toDouble()
    }

    fun timeToNumeric(dt: Date): Double {
        val df: DateFormat = SimpleDateFormat("HHmmss", Locale.US)
        return df.format(dt).toDouble()
    }

    fun getCurrentTimeStampDouble(): Double {
        val dateNumeric: Double = dateToNumeric(Calendar.getInstance().time)
        val timeNumeric: Double = timeToNumeric(Calendar.getInstance().time)

        return (dateNumeric * 1000000) + timeNumeric
    }
