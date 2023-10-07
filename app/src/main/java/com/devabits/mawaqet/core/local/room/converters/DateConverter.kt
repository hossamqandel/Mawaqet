package com.devabits.mawaqet.core.local.room.converters

import androidx.room.TypeConverter
import com.devabits.mawaqet.feature_mawaqet.domain.models.Date
import com.google.gson.Gson

object DateConverter {

    @TypeConverter
    fun fromString(value: String?): Date? {
        return Gson().fromJson(value, Date::class.java)
    }

    @TypeConverter
    fun toString(date: Date?): String? {
        return Gson().toJson(date)
    }

}