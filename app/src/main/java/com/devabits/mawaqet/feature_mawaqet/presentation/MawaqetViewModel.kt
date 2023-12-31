package com.devabits.mawaqet.feature_mawaqet.presentation

import android.os.Build
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetEntity
import com.devabits.mawaqet.feature_mawaqet.domain.use_case.GetWeekSalawatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject
@RequiresApi(Build.VERSION_CODES.R)
@HiltViewModel
class MawaqetViewModel @Inject constructor(
    private val getWeekSalawatUseCase: GetWeekSalawatUseCase
) : ViewModel(){

    private val _state = MutableStateFlow(Collections.emptyList<MawaqetEntity>())
    val state = _state.asStateFlow()
    private var mawaqetJob: Job? = null

    init {
        getMawaqet()
    }


    fun getMawaqet() {
        mawaqetJob?.cancel()
        mawaqetJob = viewModelScope.launch {
            val data = getWeekSalawatUseCase()
            _state.value = data
            Log.i(TAG, "getMawaqetWeekDays: ${data.size}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        mawaqetJob?.cancel()
    }
}