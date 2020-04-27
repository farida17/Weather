package com.farida.kotlin_api_weather.di

import android.content.Context
import com.farida.kotlin_api_weather.repository.WeatherRepository
import com.farida.kotlin_api_weather.ui.WeatherViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: WeatherApplication) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = application

    @Provides
    @Singleton
    fun provideViewModel(weatherRepository: WeatherRepository): WeatherViewModel {
        return WeatherViewModel(weatherRepository)
    }
}