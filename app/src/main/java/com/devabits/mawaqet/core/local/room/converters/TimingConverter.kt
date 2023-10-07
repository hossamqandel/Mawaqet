package com.devabits.mawaqet.core.local.room.converters

import androidx.room.TypeConverter
import com.devabits.mawaqet.feature_mawaqet.domain.models.Timings
import com.google.gson.Gson

object TimingConverter {

    @TypeConverter
    fun fromString(value: String?): Timings? {
        return Gson().fromJson(value, Timings::class.java)
    }

    @TypeConverter
    fun toString(timings: Timings?): String? {
        return Gson().toJson(timings)
    }

}