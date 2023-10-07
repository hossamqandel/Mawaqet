package com.devabits.mawaqet.core.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.devabits.mawaqet.core.constants.local.RoomUtil
import com.devabits.mawaqet.core.local.room.converters.DateConverter
import com.devabits.mawaqet.core.local.room.converters.TimingConverter
import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetDao
import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetEntity
import com.devabits.mawaqet.feature_mawaqet.domain.models.Date

@Database(
    entities = [MawaqetEntity::class],
    version = RoomUtil.ROOM_VERSION,
    exportSchema = false
)
@TypeConverters(value = [TimingConverter::class, DateConverter::class])
abstract class MawaqetDatabase : RoomDatabase() {

    abstract fun mawaqetDao(): MawaqetDao
}