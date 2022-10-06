package com.alxnns1.tvmazecompose.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DateUtils @Inject constructor(
    private val dateWrapper: DateWrapper
) {

    fun daysSinceDateString(dateString: String): Long {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = format.parse(dateString) ?: Date(0)
        val currentDate = dateWrapper.getCurrentDate()
        val difference = currentDate.time - date.time
        return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
    }
}