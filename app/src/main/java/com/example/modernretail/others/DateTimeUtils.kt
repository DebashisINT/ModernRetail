package com.example.modernretail.others

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeUtils {
    fun getCurrentDateTime(): String {
        val dt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        return dt.format(Date()).toString()
    }
}