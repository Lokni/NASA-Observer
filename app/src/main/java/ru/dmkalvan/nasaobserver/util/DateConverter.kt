package ru.dmkalvan.nasaobserver.util

import ru.dmkalvan.nasaobserver.data.Days
import java.text.SimpleDateFormat
import java.util.*

fun getDateFromEnum(day: Days): String{
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    if (day == Days.YESTERDAY) calendar.add(Calendar.DATE, -1)

    return dateFormat.format(calendar.time)
}