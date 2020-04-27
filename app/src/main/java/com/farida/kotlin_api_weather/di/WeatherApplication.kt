package com.farida.kotlin_api_weather.di

import android.app.Application

class WeatherApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = initDagger()
    }

    private fun initDagger(): ApplicationComponent =
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
}