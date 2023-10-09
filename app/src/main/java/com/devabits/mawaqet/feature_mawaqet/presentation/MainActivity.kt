package com.devabits.mawaqet.feature_mawaqet.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.devabits.mawaqet.core.android_util.alarm.AndroidAlarmScheduler
import com.devabits.mawaqet.core.android_util.permission.PermissionUtil
import com.devabits.mawaqet.databinding.ActivityMainBinding
import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetEntity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mawaqetViewModel: MawaqetViewModel
    private lateinit var scheduler: AndroidAlarmScheduler
    private lateinit var mawaqetAlarmScheduler: MawaqetAlarmScheduler
    private lateinit var mawaqetAdapter: MawaqetAdapter
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var permissionUtil: PermissionUtil
    private lateinit var binding: ActivityMainBinding
    private val currentDate = Date()
    private val dayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mawaqetViewModel = ViewModelProvider(this)[MawaqetViewModel::class.java]
        scheduler = AndroidAlarmScheduler(this)
        mawaqetAlarmScheduler = MawaqetAlarmScheduler(alarmScheduler = scheduler)
        mawaqetAdapter = MawaqetAdapter()
        setsUpPermissionsRequestLauncher()
        permissionUtil = PermissionUtil(context = this, requestPermissionLauncher)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkNotificationPermission()

        collectState()
        pullToRefresh()
    }

    private fun showSnackBar(message: String){
        Snackbar.make(
            findViewById<View>(android.R.id.content).rootView,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun setsUpPermissionsRequestLauncher(){
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) showSnackBar("Notification Permission Granted")
            else showSnackBar("Please accept notification permission from App Settings")
        }
    }
    private fun checkNotificationPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            permissionUtil.checkPermission(Manifest.permission.POST_NOTIFICATIONS,
                onPermissionAccepted = {}
            )
        }
    }
    @RequiresApi(Build.VERSION_CODES.R)
    private fun collectState(){
            lifecycleScope.launch {
                mawaqetViewModel.state.collectLatest {
                    Log.i(TAG, "collectState: ${it.size}")
                    setupPrayersRecycler(it)
                    uiDataState(it)
                    schedulePrayers(it)
                }
            }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun schedulePrayers(mawaqet: List<MawaqetEntity>){
        if (mawaqet.isNotEmpty()) {
            mawaqetAlarmScheduler.setMawaqet(mawaqet)
            mawaqetAlarmScheduler.setNextSalatAlarm()
        }
    }

    private fun setupPrayersRecycler(mawaqet: List<MawaqetEntity>){
        if (mawaqet.isNotEmpty()){
            val formattedDay = dayFormat.format(currentDate)
            val timings = mawaqet.firstOrNull { it.date.gregorian.date == formattedDay }?.timings
            timings?.let {
                mawaqetAdapter.setPrayers(it)
                binding.rvPrayerTimes.adapter = mawaqetAdapter
            }
        }
    }

    private fun uiDataState(mawaqet: List<MawaqetEntity>){
        if (mawaqet.isNotEmpty()){
            binding.apply {
                tvPrayerName.isVisible = true
                tvPrayerTime.isVisible = true
                ivNoDataNoWifi.isGone = true
                swipeToRefresh.isEnabled = false
            }
            return
        }

        binding.apply {
            tvPrayerName.isGone = true
            tvPrayerTime.isGone = true
            ivNoDataNoWifi.isVisible = true
            swipeToRefresh.isEnabled = true
        }
        return
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun pullToRefresh(){
        binding.swipeToRefresh.setOnRefreshListener {
            mawaqetViewModel.getMawaqet()
            binding.swipeToRefresh.isRefreshing = false
        }
    }

}