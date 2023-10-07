package com.devabits.mawaqet.feature_mawaqet.domain.models

data class  HttpResponse <T> (
    val code: Int,
    val status: String,
    val data: List<T>,
)
