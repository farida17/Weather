package com.farida.kotlin_api_weather.di

import android.content.Context
import com.farida.kotlin_api_weather.db.CityDao
import com.farida.kotlin_api_weather.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideCityDao(db: WeatherDatabase): CityDao {
        return db.cityDao()
    }
    @Provides
    @Singleton
    fun provideWeatherDatabase(context: Context) =
       WeatherDatabase.getInstance(context)
}