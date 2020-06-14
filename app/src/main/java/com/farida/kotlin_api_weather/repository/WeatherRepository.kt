package com.farida.kotlin_api_weather.repository

import com.farida.kotlin_api_weather.db.CityEntity
import com.farida.kotlin_api_weather.entity.CurrentWeather
import com.farida.kotlin_api_weather.entity.Forecast
import io.reactivex.Flowable
import io.reactivex.Single

interface WeatherRepository {
    fun getCities(): Flowable<List<CityEntity>>
    fun getWeather(cityName: String): Single<CurrentWeather>
    fun getFiveDaysForecast(cityName: String): Single<Forecast>
    fun addCity(cityName: String)
}