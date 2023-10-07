package com.devabits.mawaqet.feature_mawaqet.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devabits.mawaqet.feature_mawaqet.domain.models.Date
import com.devabits.mawaqet.feature_mawaqet.domain.models.Timings

@Entity
data class MawaqetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val timings: Timings,
    val date: Date,
)
