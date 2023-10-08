package com.devabits.mawaqet.feature_mawaqet.presentation

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import com.devabits.mawaqet.core.android_util.alarm.AlarmItem
import com.devabits.mawaqet.core.android_util.alarm.AlarmScheduler
import com.devabits.mawaqet.core.enums.SalawatEnum
import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetEntity
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale

class MawaqetAlarmScheduler(val alarmScheduler: AlarmScheduler) {

    private var mawaqet: List<MawaqetEntity> = Collections.emptyList()
    private val date = Date()
    private val dayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val timeDayFormat = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val currentDay = dayFormat.format(date)
    private val currentTimeDay = timeDayFormat.format(date)
//    private val currentTime = dayFormat.format(timeFormat)

    fun setMawaqet(mawaqet: List<MawaqetEntity>) {
        this.mawaqet = mawaqet
    }

    fun scheduleTodaySalawat() {
        alarmScheduler.schedule(AlarmItem(1, name = "Asr", 1696773840000, ""))
        val todaySalawat = mawaqet.first { it.date.gregorian.date.equals(currentDay, true) }
        val todayAlarmsToSet = mapToAlarmItems(mawaqetEntity = todaySalawat)
        todayAlarmsToSet.forEach { alarmItem ->
            Log.i(TAG, "scheduleTodaySalawat: ${alarmItem.name} ${alarmItem.time}")
            alarmScheduler.schedule(alarmItem)
        }
    }


    fun setNextSalatAlarm(){
        if (mawaqet.isNotEmpty()){
            val mawaqetToday = mawaqet.first { it.date.gregorian.date.equals(currentDay, true) }
            val date = mawaqetToday.date.gregorian.date
            val times = listOf(
                mawaqetToday.timings.fajr.plus(" ").plus(date),
                mawaqetToday.timings.dhuhr.plus(" ").plus(date),
                mawaqetToday.timings.asr.plus(" ").plus(date),
                mawaqetToday.timings.maghrib.plus(" ").plus(date),
                mawaqetToday.timings.isha.plus(" ").plus(date)
            )
            val nextPray = times.firstOrNull { it > currentTimeDay }
            nextPray?.let {
                val alarmItem = AlarmItem(
                    id = 1,
                    name = "الصلاه الان",
                    time = changeTimeInMillis(nextPray),
                    ""
                )
                alarmScheduler.schedule(alarmItem)
            }
        }
    }
    private fun mapToAlarmItems(mawaqetEntity: MawaqetEntity): List<AlarmItem> {
        val date = mawaqetEntity.date.gregorian.date
        val fajr = AlarmItem(
            id = mawaqetEntity.id!!,
            name = SalawatEnum.FAJR.salat,
            time = changeTimeInMillis(mawaqetEntity.timings.fajr, date),
            message = ""
        )
        val dhuhr = AlarmItem(
            id = mawaqetEntity.id,
            name = SalawatEnum.DHUHR.salat,
            time = changeTimeInMillis(mawaqetEntity.timings.dhuhr, date),
            message = ""
        )
        val asr = AlarmItem(
            id = mawaqetEntity.id,
            name = SalawatEnum.ASR.salat,
            time = changeTimeInMillis(mawaqetEntity.timings.asr, date),
            message = ""
        )
        val maghrib = AlarmItem(
            id = mawaqetEntity.id,
            name = SalawatEnum.MAGHRIB.salat,
            time = changeTimeInMillis(mawaqetEntity.timings.maghrib, date),
            message = ""
        )
        val isha = AlarmItem(
            id = mawaqetEntity.id,
            name = SalawatEnum.ISHA.salat,
            time = changeTimeInMillis(mawaqetEntity.timings.isha, date),
            message = ""
        )
        return listOf(fajr, dhuhr, asr, maghrib, isha)
    }

    private fun changeTimeInMillis(time: String, day: String): Long {
        val fullDate = time.plus(" ").plus(day)
        val timeInMillis = timeDayFormat.parse(fullDate)?.time
        return timeInMillis ?: 0L
    }
    private fun changeTimeInMillis(time: String): Long {
        val timeInMillis = timeDayFormat.parse(time)?.time
        return timeInMillis ?: 0L
    }

}