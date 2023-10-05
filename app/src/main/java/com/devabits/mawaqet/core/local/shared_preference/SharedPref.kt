package com.devabits.mawaqet.core.local.shared_preference

import android.content.Context
import android.content.SharedPreferences
import com.devabits.mawaqet.MawaqetApp

object SharedPref {

    private lateinit var appContext: MawaqetApp
    private const val PREF_NAME = "MAWAQET"
    private const val VISIT = "VISIT"
    private lateinit var sharedPreferences: SharedPreferences

    fun initShared(appContext: MawaqetApp){
        this.appContext = appContext
        sharedPreferences = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setFirstVisit(isFirstVisit: Boolean) {
        val editor = sharedPreferences.edit()
        editor?.putBoolean(VISIT, isFirstVisit)?.apply()
    }

    fun isFirstVisit(): Boolean = sharedPreferences.getBoolean(VISIT, true)

}