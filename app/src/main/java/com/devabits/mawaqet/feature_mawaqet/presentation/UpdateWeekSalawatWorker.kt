package com.devabits.mawaqet.feature_mawaqet.presentation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.devabits.mawaqet.core.calendar.CalendarUtil
import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetEntity
import com.devabits.mawaqet.feature_mawaqet.domain.models.Date
import com.devabits.mawaqet.feature_mawaqet.domain.models.Gregorian
import com.devabits.mawaqet.feature_mawaqet.domain.models.Mawaqet
import com.devabits.mawaqet.feature_mawaqet.domain.models.Timings
import com.devabits.mawaqet.feature_mawaqet.domain.repository.MawaqetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject


class UpdateWeekSalawatWorker(
    private val appContext: Context,
    private val params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    @Inject
    lateinit var repo: MawaqetRepository

    @RequiresApi(Build.VERSION_CODES.R)
    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                updateMawaqetAlSalawat()
                Result.success()
            }
        } catch (e: Exception) {
            Result.retry()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private suspend fun updateMawaqetAlSalawat() {
        val currentMonthMawaqet = repo.getCurrentMonthMawaqet()
        val mawaqetWeek = filterCurrentWeekDays(currentMonthMawaqet.data)
        val newWeekSalawat = toEntityWeek(mawaqetWeek)
        repo.deleteAllWeekFromCache()
        repo.addWeek(newWeekSalawat)
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