package com.example.jetweatherforecast.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun formatDate(timeStamp : Int) : String {
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = java.util.Date(timeStamp.toLong() * 1000)

    return sdf.format(date)
}


fun formatDateTime(timeStamp: Int) : String {
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = java.util.Date(timeStamp.toLong() * 1000)

    return sdf.format(date)
}

fun fetchDay(timeStamp: Int): String {
    // Convert the timestamp (in seconds) to milliseconds
    val date = Date(timeStamp * 1000L)

    // Create a SimpleDateFormat instance to format the day of the week
    val sdf = SimpleDateFormat("EEE", Locale.getDefault())

    // Return the formatted day of the week
    return sdf.format(date)
}




fun formatPressure(pressure: Int) : Double {
    val actualPressure = 0.0145038 * pressure
    return String.format(Locale.US, "%.2f", actualPressure).toDouble()
}

