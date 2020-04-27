package com.farida.kotlin_api_weather.di

import com.farida.kotlin_api_weather.repository.WeatherRepository
import com.farida.kotlin_api_weather.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindsWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

}