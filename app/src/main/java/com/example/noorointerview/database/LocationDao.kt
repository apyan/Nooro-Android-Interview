package com.example.noorointerview.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noorointerview.database.entities.SavedLocation

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedLocation(savedLocation: SavedLocation)

    @Query("SELECT * FROM savedlocation")
    suspend fun getAllSavedLocations(): List<SavedLocation>
}