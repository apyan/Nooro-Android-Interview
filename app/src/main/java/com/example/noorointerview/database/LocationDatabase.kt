package com.example.noorointerview.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noorointerview.database.entities.SavedLocation

@Database(entities = [SavedLocation::class], version = 1)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao() : LocationDao

    companion object {
        private const val DATABASE_SAVED_LOCATION = "app_saved_locations"

        @Volatile
        private var instance: LocationDatabase? = null

        fun getDatabase(context: Context): LocationDatabase {
            if (instance == null) {
                synchronized(LocationDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context, LocationDatabase::class.java, DATABASE_SAVED_LOCATION
                        )
                            .fallbackToDestructiveMigration()
                            .enableMultiInstanceInvalidation()
                            .build()
                    }
                }
            }

            return instance!!
        }
    }
}