package com.devabits.mawaqet.core.android_util.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val mIntent = Intent(context, AlarmService::class.java)
        context?.let {
            ContextCompat.startForegroundService(context, mIntent)
            if (intent?.action.equals("ACTION_DISMISS")){
                context.stopService(mIntent)
            }
        }

    }

}