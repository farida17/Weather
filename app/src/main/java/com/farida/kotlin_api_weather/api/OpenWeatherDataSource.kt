package com.farida.kotlin_api_weather.api

import com.farida.kotlin_api_weather.common.Constants.API_KEY
import io.reactivex.Single
import javax.inject.Inject

class OpenWeatherDataSource @Inject constructor(private val openWeatherService: OpenWeatherService) {
    fun loadWeatherByCityName(cityName: String): Single<WeatherResponse> =
        openWeatherService.loadWeatherByCityName(cityName, API_KEY)

    fun loadForecastByCityName(cityName: String): Single<ForecastResponse> =
        openWeatherService.loadFiveDaysForecastByCityName(cityName, API_KEY)
}