package com.example.noorointerview.model

import com.example.noorointerview.model.response.LocationSearchResponse
import javax.inject.Inject

class WeatherSearchRepository @Inject constructor(
    private val weatherSearchService: WeatherSearchService
) {
    suspend fun loadWeatherSearchInfo(searchTerm: String): LocationSearchResponse {
        return weatherSearchService.loadLocationSearchInfo(
            query = searchTerm
        )
    }
}