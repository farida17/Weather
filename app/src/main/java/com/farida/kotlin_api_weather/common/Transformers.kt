package com.farida.kotlin_api_weather.common

import com.farida.kotlin_api_weather.api.WeatherResponse
import com.farida.kotlin_api_weather.entity.*

object Transformers {
    fun transformToCurrentWeather(cityName: String, weatherResponse: WeatherResponse?): CurrentWeather {
        val dt = weatherResponse?.dt
        val main = MainData(weatherResponse?.main?.temp, weatherResponse?.main?.pressure, weatherResponse?.main?.feels_like, weatherResponse?.main?.humidity, weatherResponse?.main?.temp_min, weatherResponse?.main?.temp_max)
        val clouds = CloudsData(weatherResponse?.clouds?.all)
        val wind = WindData(weatherResponse?.wind?.speed, weatherResponse?.wind?.deg, weatherResponse?.wind?.gust)
        val coord = CoordData(weatherResponse?.coord?.lat, weatherResponse?.coord?.lon)
        val weather = ArrayList<WeatherInfo>()
        weatherResponse?.weather?.forEach{
            weather.add(WeatherInfo(it.main, it.description, it.icon))
        }
        val name = if (cityName.isEmpty()) weatherResponse?.name!! else cityName
        return  CurrentWeather(
            dt = dt,
            cityName = name,
            main = main,
            clouds = clouds,
            wind = wind,
            coord = coord,
            weather = weather)
        }
}