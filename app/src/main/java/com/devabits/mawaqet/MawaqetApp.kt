package com.devabits.mawaqet

import android.app.Application
import com.devabits.mawaqet.core.local.shared_preference.SharedPref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MawaqetApp : Application(){

    override fun onCreate() {
        super.onCreate()
        SharedPref.initShared(appContext = this)
    }
}