package com.example.noorointerview.helper

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import java.util.Date

object TimeStampHelper {

    private const val TIME_FORMAT_INPUT = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'"

    private const val TIME_FORMAT_OUTPUT_DATE = "MMM dd, yyyy"
    private const val TIME_FORMAT_OUTPUT_TIME = "HH:mm a"

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(dateString: String): String {
        var newFormattedDate = TIME_FORMAT_OUTPUT_DATE
        try {
            val oldFormatter = SimpleDateFormat(TIME_FORMAT_INPUT)
            oldFormatter.timeZone = TimeZone.getTimeZone("GMT")
            val value: Date = oldFormatter.parse(dateString)

            val newFormatter = SimpleDateFormat(TIME_FORMAT_OUTPUT_DATE)
            newFormatter.timeZone = TimeZone.getDefault()
            newFormattedDate = newFormatter.format(value)

        } catch (_: Exception) {
        }
        return newFormattedDate
    }

    @SuppressLint("SimpleDateFormat")
    fun getFormattedTime(dateString: String): String {
        var newFormattedDate = TIME_FORMAT_OUTPUT_TIME
        try {
            val oldFormatter = SimpleDateFormat(TIME_FORMAT_INPUT)
            oldFormatter.timeZone = TimeZone.getTimeZone("UTC")
            val value: Date = oldFormatter.parse(dateString)

            val newFormatter = SimpleDateFormat(TIME_FORMAT_OUTPUT_TIME)
            newFormatter.timeZone = TimeZone.getDefault()
            newFormattedDate = newFormatter.format(value)

        } catch (_: Exception) {
        }
        return newFormattedDate
    }
}