package com.devabits.mawaqet.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devabits.mawaqet.core.constants.local.RoomUtil

@Database(entities = [], version = RoomUtil.ROOM_VERSION, exportSchema = false)
abstract class MawaqetDatabase : RoomDatabase() {
}