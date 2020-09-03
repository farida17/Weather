package com.farida.kotlin_api_weather.entity

data class CurrentWeather(
    val dt: Long?,
    val cityName: String,
    val main: MainData?,
    val clouds: CloudsData?,
    val wind: WindData?,
    val dt_txt: String?,
    val coord: CoordData?,
    val weather: List<WeatherInfo>?,
    val sys: Sys,
    val timezone: Int?
)

data class Forecast (
    val city: City?,
    val list: List<CurrentWeather>?)

data class WeatherInfo (val main: String?,
                        val description: String?,
                        val icon: String?)

data class MainData (val temp: Double?,
                     val feels_like: Double?,
                     val pressure: Double?,
                     val humidity: Int?,
                     val temp_min: Double?,
                     val temp_max: Double?)

data class CloudsData (val all: Int?)

data class WindData (val speed: Double?,
                     val deg: Double?,
                     val gust: Double?)

data class CoordData (val longitude: Double?,
                      val latitude: Double?)

data class City (val name: String?,
                 val coord: CoordData?,
                 val country: String?,
                 val population: Double?)

data class Sys(val country: String?,
               val sunrise: Long?,
               val sunset: Long?)