package com.devabits.mawaqet.feature_mawaqet.domain.models

import com.google.gson.annotations.SerializedName

data class Mawaqet(
    val date: Date,
    val timings: Timings
)

data class Timings(
    @SerializedName("Fajr")
    val fajr: String,
    @SerializedName("Dhuhr")
    val dhuhr: String,
    @SerializedName("Asr")
    val asr: String,
    @SerializedName("Maghrib")
    val maghrib: String,
    @SerializedName("Isha")
    val isha: String,
)

data class Date(
    val gregorian: Gregorian,
    val readable: String,
    val timestamp: String
)

data class Gregorian(
    val date: String,
    val day: String
)
