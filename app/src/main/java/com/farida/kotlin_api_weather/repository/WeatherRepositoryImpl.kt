package com.farida.kotlin_api_weather.repository

import com.farida.kotlin_api_weather.api.OpenWeatherDataSource
import com.farida.kotlin_api_weather.api.WeatherResponse
import com.farida.kotlin_api_weather.common.Transformers
import com.farida.kotlin_api_weather.db.CityEntity
import com.farida.kotlin_api_weather.db.WeatherDatabase
import com.farida.kotlin_api_weather.entity.CurrentWeather
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
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
                Transformers.transformToCurrentWeather(cityName, weatherResponse)
            }
    }

    override fun addCity(cityName: String) {
        Completable.fromCallable { weatherDatabase.cityDao().insertCity(CityEntity(cityName = cityName)) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}