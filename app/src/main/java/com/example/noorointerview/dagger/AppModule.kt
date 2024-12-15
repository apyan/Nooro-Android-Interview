package com.example.noorointerview.dagger

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: MainApplication) {
    @Provides
    @Singleton
    fun getApplication(): Application {
        return application
    }
}