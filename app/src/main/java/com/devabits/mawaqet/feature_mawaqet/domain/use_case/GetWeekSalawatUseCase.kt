package com.devabits.mawaqet.feature_mawaqet.domain.use_case

import android.os.Build
import androidx.annotation.RequiresApi
import com.devabits.mawaqet.core.calendar.CalendarUtil
import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetEntity
import com.devabits.mawaqet.feature_mawaqet.domain.models.Date
import com.devabits.mawaqet.feature_mawaqet.domain.models.Gregorian
import com.devabits.mawaqet.feature_mawaqet.domain.models.Mawaqet
import com.devabits.mawaqet.feature_mawaqet.domain.models.Timings
import com.devabits.mawaqet.feature_mawaqet.domain.repository.MawaqetRepository
import java.util.Calendar
import java.util.Collections
import javax.inject.Inject

class GetWeekSalawatUseCase @Inject constructor(
    private val repo: MawaqetRepository
) {
    @RequiresApi(Build.VERSION_CODES.R)
    suspend operator fun invoke(): List<MawaqetEntity> {
        val isFirstVisit = repo.getWeekFromCache().isEmpty()
        return when (isFirstVisit) {
            true -> saveAsFirstTime()
            false -> repo.getWeekFromCache()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private suspend fun saveAsFirstTime(): List<MawaqetEntity> {
        val currentMonthMawaqet = repo.getCurrentMonthMawaqet()
        if (currentMonthMawaqet.code == 404)
            return Collections.emptyList()

        val mawaqetWeek = filterCurrentWeekDays(currentMonthMawaqet.data)
        val week = toEntityWeek(mawaqetWeek)
        repo.addWeek(week)
        return repo.getWeekFromCache()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun filterCurrentWeekDays(month: List<Mawaqet>): List<Mawaqet> {
        val resultInWeek = mutableListOf<Mawaqet>()
        val currentWeekDays = CalendarUtil.getWeekDays(Calendar.SATURDAY)
        currentWeekDays.forEach { day ->
            month.forEach { mawaqet ->
                if (day.equals(mawaqet.date.gregorian.date, true)) {
                    resultInWeek.add(mawaqet)
                }
            }
        }
        return resultInWeek
    }

    private fun toEntityWeek(mawaqet: List<Mawaqet>): List<MawaqetEntity> {
        val entityWeek = mutableListOf<MawaqetEntity>()
        mawaqet.forEach { mawaqetRec ->
            entityWeek.add(rebuildEntityValues(mawaqetRec))
        }
        return entityWeek
    }

    private fun rebuildEntityValues(mawaqet: Mawaqet): MawaqetEntity {
        val timings = Timings(
            fajr = mawaqet.timings.fajr.take(5),
            dhuhr = mawaqet.timings.dhuhr.take(5),
            asr = mawaqet.timings.asr.take(5),
            maghrib = mawaqet.timings.maghrib.take(5),
            isha = mawaqet.timings.isha.take(5)
        )
        val date = Date(
            gregorian = Gregorian(
                mawaqet.date.gregorian.date,
                mawaqet.date.gregorian.day),
            readable = mawaqet.date.readable,
            timestamp = mawaqet.date.timestamp
        )
        return MawaqetEntity(timings = timings, date = date)
    }
}