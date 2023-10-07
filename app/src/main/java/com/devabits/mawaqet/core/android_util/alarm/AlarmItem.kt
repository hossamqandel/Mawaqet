package com.devabits.mawaqet.core.android_util.alarm


data class AlarmItem(
    val id: Int,
    val name: String? = null,
    val time: Long,
    val message: String,
)
