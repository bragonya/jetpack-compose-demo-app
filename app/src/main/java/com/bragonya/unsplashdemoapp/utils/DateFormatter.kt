package com.bragonya.unsplashdemoapp.utils

import java.text.SimpleDateFormat
import java.util.*
const val NO_DATE_FALLBACK = "No date available"
object DateFormatter {
    const val pattern = "d MMM yyyy"
    const val patternIn = "yyyy-MM-dd"

    private val simpleFormatter = SimpleDateFormat(pattern, Locale.getDefault())
    private val simpleFormatterIn = SimpleDateFormat(patternIn, Locale.getDefault())

    fun formatToHumanDays(date: String) =  try {
        simpleFormatter.format(simpleFormatterIn.parse(date))
    }
    catch (e: Exception){
        NO_DATE_FALLBACK
    }
}