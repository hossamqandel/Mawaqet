package com.devabits.mawaqet

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.devabits.mawaqet.core.local.shared_preference.SharedPref
import com.devabits.mawaqet.feature_mawaqet.presentation.UpdateWeekSalawatWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MawaqetApp : Application(){

    override fun onCreate() {
        super.onCreate()
        SharedPref.initShared(appContext = this)
        createNotificationChannel()
        setNextWeekSalawatWorker()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ChanelId",
                "Azan Time",
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Used for the Azan"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setNextWeekSalawatWorker(){
        val worker = WorkManager.getInstance(applicationContext)
        val request = PeriodicWorkRequest.Builder(
            workerClass = UpdateWeekSalawatWorker::class.java,
            repeatInterval = 7,
            repeatIntervalTimeUnit = TimeUnit.DAYS)
            .build()
        worker.enqueue(request)
    }
}