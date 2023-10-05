package com.devabits.mawaqet.core.remote

import com.devabits.mawaqet.core.constants.web_service.EndPoints
import com.devabits.mawaqet.core.constants.web_service.RestUtil
import com.devabits.mawaqet.feature_mawaqet.domain.models.HttpResponse
import com.devabits.mawaqet.feature_mawaqet.domain.models.Mawaqet
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MawaqetAPIs {

    @GET(RestUtil.BASE_URL + EndPoints.MAWAQET_SALAT_API)
    suspend fun getAzanMawaqet(
        @Path("year") year: String,
        @Path("month") month: String,
        @Query("country") country: String,
        @Query("city") city: String,
        @Query("method") method: Int,
        ): HttpResponse<Mawaqet>
}