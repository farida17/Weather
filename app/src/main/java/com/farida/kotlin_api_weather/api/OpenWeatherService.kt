package com.farida.kotlin_api_weather.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("weather")
    fun loadWeatherByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Single<WeatherResponse>

    @GET("forecast")
    fun loadFiveDaysForecastByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Single<ForecastResponse>
}
