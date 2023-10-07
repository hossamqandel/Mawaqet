package com.devabits.mawaqet.core.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

object CalendarUtil {

    val format: DateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    val calendar = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getToday(format: String): String {
        val currentDate = LocalDate.now()
        return format.format(currentDate)
    }
    fun getWeekDays(firstDayOfWeek: Int): List<String?> {
        calendar.firstDayOfWeek = firstDayOfWeek
        calendar[Calendar.DAY_OF_WEEK] = firstDayOfWeek

        val days = arrayOfNulls<String>(7)
        for (i in 0..6) {
            days[i] = format.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return days.toList()
    }

    fun dateTimeToMillis(dateTimeString: String): Long? {
        val format = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.ENGLISH)
        try {
            val date = format.parse(dateTimeString)
            return date?.time
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}