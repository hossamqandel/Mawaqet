package com.devabits.mawaqet.feature_mawaqet.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MawaqetDao {

    @Query("SELECT * FROM MawaqetEntity")
    suspend fun getWeekFromCache(): List<MawaqetEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeek(week: List<MawaqetEntity>)
    @Query("DELETE FROM MawaqetEntity")
    suspend fun deleteAllWeekFromCache()

}