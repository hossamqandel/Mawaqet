package com.devabits.mawaqet.core.android_util.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.devabits.mawaqet.R
import com.devabits.mawaqet.core.android_util.notification.NotificationUtil
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val azanName = intent?.getStringExtra("AZAN_NAME") ?: return
        val azanMessage = intent.getStringExtra("AZAN_MESSAGE") ?: return
        println("Alarm Azan Name $azanName")
        println("Alarm Azan Message $azanMessage")
        showNotification(context = context, channelId = NotificationUtil.CHANNEL_ID, azanName = azanName)
    }

    private fun showNotification(context: Context, channelId: String, azanName: String) {
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(azanName)
            .setContentText("حان الان موعد الصلاة")
            .build()
        notificationManager.notify(Random.nextInt(900000), notification)
    }

}