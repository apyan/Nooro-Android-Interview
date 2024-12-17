package com.example.noorointerview.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noorointerview.database.LocationDatabaseRepository
import com.example.noorointerview.database.entities.SavedLocation
import com.example.noorointerview.helper.ConnectionHelper
import com.example.noorointerview.model.WeatherSearchRepository
import com.example.noorointerview.model.response.Current
import com.example.noorointerview.model.response.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class LocationSearchViewModel @Inject constructor(
    private val weatherSearchRepository: WeatherSearchRepository,
    private val locationDatabaseRepository: LocationDatabaseRepository
) : ViewModel() {

    private val _locationSearch: MutableStateFlow<Location?> = MutableStateFlow(null)
    val locationSearch = _locationSearch.asStateFlow()

    private val _currentWeather: MutableStateFlow<Current?> = MutableStateFlow(null)
    val currentWeather = _currentWeather.asStateFlow()

    private val _loadingStatus: MutableStateFlow<LoadingStatus> = MutableStateFlow(LoadingStatus.SEARCH_EMPTY)
    val loadingStatus = _loadingStatus.asStateFlow()

    private val _searchHistory: MutableStateFlow<List<SavedLocation>> = MutableStateFlow(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    private val _searchTerm: MutableStateFlow<String> = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    private var locationSearchJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        locationSearchJob?.cancel()
        locationSearchJob = null
    }

    fun setSearchTerm(locationTerm: String) {
        _searchTerm.value = locationTerm
        resetStatusOfSearchTerm()
    }

    private fun resetStatusOfSearchTerm() {
        if (searchTerm.value.isNotEmpty()) {
            _locationSearch.value = null
            _currentWeather.value = null
            _loadingStatus.value = LoadingStatus.SEARCH_FOUND
        } else {
            _loadingStatus.value = LoadingStatus.SEARCH_EMPTY
        }
    }

    fun setLoadingStatus(loadingStatus: LoadingStatus) {
        _loadingStatus.value = loadingStatus
    }

    fun launchLocationSearch(
        context: Context,
        toastConnectionIssue:() -> Unit = {}
    ) {
        locationSearchJob?.cancel()
        if (searchTerm.value.trim().isNotEmpty()) {
            if (ConnectionHelper.isConnected(context) && !ConnectionHelper.isAirplaneModeOn(context)) {
                locationSearchJob = viewModelScope.launch(Dispatchers.IO) {
                    getLocationSearchInfo(
                        locationTerm = searchTerm.value
                    )
                }
            } else {
                toastConnectionIssue.invoke()
            }
        }
    }

    private suspend fun getLocationSearchInfo(locationTerm: String) {
        _loadingStatus.value = LoadingStatus.LOADING
        try {
            val locationSearchResult = weatherSearchRepository.loadWeatherSearchInfo(
                    searchTerm = locationTerm
                )
            _locationSearch.value = locationSearchResult.location
            _currentWeather.value = locationSearchResult.current
            _loadingStatus.value = LoadingStatus.SEARCH_FOUND
        } catch (_: HttpException) {
            resetStatusOfSearchTerm()
        }
    }

    fun saveViewedLocationDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            locationDatabaseRepository.saveViewedLocation(
                name = locationSearch.value?.name,
                lastUpdated = currentWeather.value?.lastUpdated,
                tempF = currentWeather.value?.getRoundedTempF(),
                imageUrl = currentWeather.value?.condition?.getImageUrl()
            )
        }
    }

    fun loadListLocationDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            _searchHistory.value = locationDatabaseRepository.getViewedLocations()
        }
    }
}

enum class LoadingStatus {
    LOADING,
    SEARCH_FOUND,
    SEARCH_EMPTY,
    LOCATION_INFO
}