package com.example.tudee.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


fun convertMillisToDate(millis: Long, pattern: String = "dd/MM/yyyy"): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(Date(millis))
}

fun convertMillisToLocalDate(millis: Long): LocalDate {
    return java.time.Instant.ofEpochMilli(millis)
        .atZone(java.time.ZoneId.systemDefault())
        .toLocalDate()
}