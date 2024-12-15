package com.example.noorointerview.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noorointerview.helper.ConnectionHelper
import com.example.noorointerview.model.WeatherSearchRepository
import com.example.noorointerview.model.response.Current
import com.example.noorointerview.model.response.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationSearchViewModel @Inject constructor(
    private val weatherSearchRepository: WeatherSearchRepository
) : ViewModel() {

    private val _locationSearch: MutableStateFlow<Location?> = MutableStateFlow(null)
    val locationSearch = _locationSearch.asStateFlow()

    private val _currentWeather: MutableStateFlow<Current?> = MutableStateFlow(null)
    val currentWeather = _currentWeather.asStateFlow()

    private val _loadingStatus: MutableStateFlow<LoadingStatus> = MutableStateFlow(LoadingStatus.SEARCH_EMPTY)
    val loadingStatus = _loadingStatus.asStateFlow()

    private var locationSearchJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        locationSearchJob?.cancel()
    }

    init {
        // TODO: Load last searches into cache
    }

    fun launchLocationSearch(
        context: Context,
        searchTerm: String,
        toastConnectionIssue:() -> Unit = {}
    ) {
        locationSearchJob?.cancel()
        if (searchTerm.trim().isNotEmpty()) {
            if (ConnectionHelper.isConnected(context) && !ConnectionHelper.isAirplaneModeOn(context)) {
                locationSearchJob = viewModelScope.launch(Dispatchers.IO) {
                    getLocationSearchInfo(
                        searchTerm = searchTerm
                    )
                }
            } else {
                toastConnectionIssue.invoke()
            }
        }
    }

    private suspend fun getLocationSearchInfo(searchTerm: String) {
        _loadingStatus.value = LoadingStatus.LOADING
        try {
            val locationSearchResult = weatherSearchRepository.loadWeatherSearchInfo(
                searchTerm = searchTerm
            )
            _locationSearch.value = locationSearchResult.location
            _currentWeather.value = locationSearchResult.current
            _loadingStatus.value = LoadingStatus.SEARCH_FOUND

            // TODO: Save last search into cache
        } catch (error: Error) {
            _loadingStatus.value = LoadingStatus.SEARCH_EMPTY
        }
    }
}

enum class LoadingStatus {
    LOADING,
    SEARCH_FOUND,
    SEARCH_EMPTY,
    RECENTLY_VIEWED,
    LOCATION_INFO
}