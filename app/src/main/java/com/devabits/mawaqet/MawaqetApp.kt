package com.devabits.mawaqet

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.devabits.mawaqet.core.android_util.notification.NotificationUtil
import com.devabits.mawaqet.feature_mawaqet.presentation.UpdateWeekSalawatWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MawaqetApp : Application(){

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        setNextWeekSalawatWorker()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationUtil.CHANNEL_ID,
                NotificationUtil.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = NotificationUtil.DESCRIPTION
                lightColor = Color.MAGENTA
                enableLights(true)
                enableVibration(true)
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setNextWeekSalawatWorker(){
        val worker = WorkManager.getInstance(this)
        val request = PeriodicWorkRequest.Builder(
            workerClass = UpdateWeekSalawatWorker::class.java,
            repeatInterval = 7,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        ).build()
        worker.enqueue(request)
    }
}