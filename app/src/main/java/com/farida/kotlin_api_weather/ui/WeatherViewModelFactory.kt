package com.farida.kotlin_api_weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farida.kotlin_api_weather.repository.WeatherRepository
import javax.inject.Inject

class WeatherViewModelFactory @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(weatherRepository) as T
        }
        throw IllegalArgumentException("Unknown model class $modelClass")
    }
}