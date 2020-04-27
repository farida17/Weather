package com.farida.kotlin_api_weather.entity

data class CurrentWeather(
    val dt: Long?,
    val cityName: String,
    val main: MainData?,
    val clouds: CloudsData?,
    val wind: WindData?,
    val coord: CoordData?,
    val weather: ArrayList<WeatherInfo>
)

data class WeatherInfo(val main: String?, val description: String?, val icon: String?)

data class MainData(val temp: Double?, val feels_like: Double?, val pressure: Double?, val humidity: Int?, val temp_min: Double?, val temp_max: Double?)

data class CloudsData constructor(val all: Int?)

data class WindData constructor(val speed: Double?, val deg: Double?, val gust: Double?)

data class CoordData constructor(val longitude: Double?, val latitude: Double?)