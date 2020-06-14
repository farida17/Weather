package com.farida.kotlin_api_weather.di

import com.farida.kotlin_api_weather.ui.WeatherActivity
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
    OpenWeatherServiceModule::class])

interface ApplicationComponent {

    fun inject(weatherActivity: WeatherActivity)
}