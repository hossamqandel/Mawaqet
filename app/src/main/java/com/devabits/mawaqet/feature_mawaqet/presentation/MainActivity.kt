package com.devabits.mawaqet.feature_mawaqet.presentation

import android.os.Build
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.devabits.mawaqet.core.android_util.alarm.AndroidAlarmScheduler
import com.devabits.mawaqet.databinding.ActivityMainBinding
import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mawaqetViewModel: MawaqetViewModel
    private lateinit var scheduler: AndroidAlarmScheduler
    private lateinit var mawaqetAdapter: MawaqetAdapter
    private lateinit var binding: ActivityMainBinding
    private val currentDate = Date()
    private val dayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())




    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mawaqetViewModel = ViewModelProvider(this)[MawaqetViewModel::class.java]
        scheduler = AndroidAlarmScheduler(this)
        mawaqetAdapter = MawaqetAdapter()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        collectState()
        schedulePrayers()
        pullToRefresh()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun collectState(){
            lifecycleScope.launch {
                mawaqetViewModel.state.collectLatest {
                    Log.i(TAG, "collectState: ${it.size}")
                    setupPrayersRecycler(it)
                    uiDataState(it)
                }
            }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun schedulePrayers(){
//        val alarmTest = AlarmItem(
//            id =  Random.nextInt(1, 9000000),
//            name = "Salah",
//            time = 1696624200000,
//            message = "حان الان موعد المين اكتيفيتي"
//        )
//        scheduler.schedule(alarmTest)
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
        //TODO
        //Collections.emptyList<MawaqetEntity>().isEmpty()
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