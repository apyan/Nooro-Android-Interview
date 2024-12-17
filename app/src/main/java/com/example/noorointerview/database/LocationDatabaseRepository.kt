package com.example.noorointerview.database

import com.example.noorointerview.database.entities.SavedLocation
import javax.inject.Inject

class LocationDatabaseRepository @Inject constructor(
    private val locationDatabase: LocationDatabase
) {
    suspend fun saveViewedLocation(
        name: String?,
        lastUpdated: String?,
        tempF: Int?,
        imageUrl: String?
    ) {
        locationDatabase.locationDao().insertSavedLocation(
            SavedLocation(
                name = name,
                lastUpdated = lastUpdated,
                tempF = tempF,
                imageUrl = imageUrl
            )
        )
    }

    suspend fun getViewedLocations(): List<SavedLocation> {
        return locationDatabase.locationDao().getAllSavedLocations()
    }
}