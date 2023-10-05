package com.devabits.mawaqet.core.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MawaqetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val salat: String
)
