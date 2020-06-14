package com.farida.kotlin_api_weather.repository

import com.farida.kotlin_api_weather.api.ForecastResponse
import com.farida.kotlin_api_weather.api.OpenWeatherDataSource
import com.farida.kotlin_api_weather.api.WeatherResponse
import com.farida.kotlin_api_weather.common.TransformAdapter

import com.farida.kotlin_api_weather.db.CityEntity
import com.farida.kotlin_api_weather.db.WeatherDatabase
import com.farida.kotlin_api_weather.entity.CurrentWeather
import com.farida.kotlin_api_weather.entity.Forecast
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val openWeatherDataSource: OpenWeatherDataSource,
    private val weatherDatabase: WeatherDatabase
): WeatherRepository {

    override fun getCities(): Flowable<List<CityEntity>> {
        return weatherDatabase.cityDao().getCities()
    }

    override fun getWeather(cityName: String): Single<CurrentWeather> {
        return openWeatherDataSource.loadWeatherByCityName(cityName = cityName)
            .map { weatherResponse: WeatherResponse ->
                TransformAdapter.transformToCurrentWeather(cityName, weatherResponse)
            }
    }

    override fun getFiveDaysForecast(cityName: String): Single<Forecast> {
        return openWeatherDataSource.loadForecastByCityName(cityName = cityName)
            .map { forecastResponse: ForecastResponse ->
                TransformAdapter.transformToForecast(cityName, forecastResponse)
            }
    }

    override fun addCity(cityName: String) {
        Completable.fromCallable { weatherDatabase.cityDao().insertCity(CityEntity(cityName = cityName)) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}