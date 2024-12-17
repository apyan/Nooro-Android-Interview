package com.example.noorointerview.dagger

import com.example.noorointerview.core.Constant
import com.example.noorointerview.model.WeatherSearchRepository
import com.example.noorointerview.model.WeatherSearchService
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @Provides
    fun provideWeatherSearchService(): WeatherSearchService {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constant.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { addApiKeyQuery(it) }.build()
            )
            .build()
        return retrofit.create(WeatherSearchService::class.java)
    }

    @Provides
    fun addApiKeyQuery(chain: Interceptor.Chain) = chain.proceed(
        chain.request()
            .newBuilder()
            .url(chain.request().url.newBuilder().addQueryParameter(Constant.QUERY_KEY,
                Constant.API_KEY).build())
            .build()
    )
}