package com.devabits.mawaqet.core.remote

import com.devabits.mawaqet.core.constants.web_service.EndPoints
import com.devabits.mawaqet.core.constants.web_service.RestUtil
import com.devabits.mawaqet.feature_mawaqet.domain.models.HttpResponse
import com.devabits.mawaqet.feature_mawaqet.domain.models.Mawaqet
import retrofit2.http.GET
import retrofit2.http.Query

interface MawaqetAPIs {

    @GET(RestUtil.BASE_URL + EndPoints.MAWAQET_SALAT_API)
    suspend fun getAzanMawaqet(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") methodTitle: String,
        @Query("method") method: Int,
        ): HttpResponse<Mawaqet>
}