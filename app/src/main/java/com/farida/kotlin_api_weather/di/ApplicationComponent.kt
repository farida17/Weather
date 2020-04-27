package com.farida.kotlin_api_weather.di

import com.farida.kotlin_api_weather.ui.SearchCityActivity
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
    fun inject(searchCityActivity: SearchCityActivity)
}