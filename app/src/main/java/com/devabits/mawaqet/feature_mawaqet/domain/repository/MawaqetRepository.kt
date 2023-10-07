package com.devabits.mawaqet.feature_mawaqet.domain.repository

import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetEntity
import com.devabits.mawaqet.feature_mawaqet.domain.models.HttpResponse
import com.devabits.mawaqet.feature_mawaqet.domain.models.Mawaqet

interface MawaqetRepository {

    suspend fun getCurrentMonthMawaqet(): HttpResponse<Mawaqet>
    suspend fun getWeekFromCache(): List<MawaqetEntity>
    suspend fun addWeek(week: List<MawaqetEntity>)
    suspend fun deleteAllWeekFromCache()
}