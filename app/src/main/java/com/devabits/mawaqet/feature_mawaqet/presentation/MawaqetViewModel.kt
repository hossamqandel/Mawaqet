package com.devabits.mawaqet.feature_mawaqet.presentation

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devabits.mawaqet.feature_mawaqet.data.local.MawaqetEntity
import com.devabits.mawaqet.feature_mawaqet.domain.use_case.GetWeekSalawatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject

@HiltViewModel
class MawaqetViewModel @Inject constructor(
    private val useCase: GetWeekSalawatUseCase
) : ViewModel(){

    private val _state = MutableStateFlow(Collections.emptyList<MawaqetEntity>())
    val state = _state.asStateFlow()
    init {
        getMawaqet()
    }

    fun getMawaqet() = viewModelScope.launch {
        val data = useCase()
        _state.value = data
        Log.i(TAG, "getMawaqetWeekDays: ${data.size}")
    }
}