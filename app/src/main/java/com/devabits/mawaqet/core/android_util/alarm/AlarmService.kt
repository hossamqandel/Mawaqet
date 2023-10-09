package com.devabits.mawaqet.core.android_util.alarm

import android.app.Notification
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.devabits.mawaqet.R
import com.devabits.mawaqet.core.android_util.notification.NotificationUtil

class AlarmService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mediaPlayer = MediaPlayer.create(this, R.raw.azan_voice)
        mediaPlayer.setOnCompletionListener { stopSelf() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, showNotification(NotificationUtil.CHANNEL_ID, NotificationUtil.CHANNEL_NAME))
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show()
        if (!mediaPlayer.isPlaying){
            mediaPlayer.start()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(channelId: String, azanName: String): Notification {
        val dismissIntent = Intent(this, AlarmReceiver::class.java).apply {
            action = "ACTION_DISMISS"
            putExtra(EXTRA_NOTIFICATION_ID, 0)
        }
        val dismissPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, dismissIntent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(azanName)
            .setContentText("حان الان موعد الصلاة")
            .addAction(R.drawable.close_24, "Close", dismissPendingIntent)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "AlarmService onDestroy: ")
        if (mediaPlayer.isPlaying){
            mediaPlayer.stop()
            mediaPlayer.release()
            Log.i(TAG, "AlarmService onDestroy: MediaPlayer-Released")
        }
    }
}