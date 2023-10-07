package com.devabits.mawaqet.core.android_util.alarm

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}