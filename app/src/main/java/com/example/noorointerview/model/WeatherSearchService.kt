package com.example.noorointerview.model

import com.example.noorointerview.core.Constant
import com.example.noorointerview.model.response.LocationSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherSearchService {

    @GET("v1/current.json")
    suspend fun loadLocationSearchInfo(
        @Query("q") query: String,
        @Query("aqi") ts: String = Constant.QUERY_AQI_NO,
    ): LocationSearchResponse
}