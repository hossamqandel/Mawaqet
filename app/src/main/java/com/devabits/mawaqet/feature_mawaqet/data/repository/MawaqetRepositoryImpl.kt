package com.devabits.mawaqet.feature_mawaqet.data.repository

import com.devabits.mawaqet.core.remote.MawaqetAPIs
import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetDao
import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetEntity
import com.devabits.mawaqet.feature_mawaqet.domain.models.HttpResponse
import com.devabits.mawaqet.feature_mawaqet.domain.models.Mawaqet
import com.devabits.mawaqet.feature_mawaqet.domain.repository.MawaqetRepository
import javax.inject.Inject

class MawaqetRepositoryImpl @Inject constructor(
    private val api: MawaqetAPIs,
    private val dao: MawaqetDao,
) : MawaqetRepository {
    override suspend fun getCurrentMonthMawaqet(): HttpResponse<Mawaqet> {
        return api.getAzanMawaqet(
            year = "2023",
            month = "10",
            country = "Egypt",
            city = "Giza",
            method = 5
        )
    }

    override suspend fun getWeekFromCache(): List<MawaqetEntity> {
        return dao.getWeekFromCache()
    }

    override suspend fun addWeek(week: List<MawaqetEntity>) {
        dao.insertWeek(week = week)
    }

    override suspend fun deleteAllWeekFromCache() {
        dao.deleteAllWeekFromCache()
    }


}