package com.farida.kotlin_api_weather.api


data class WeatherResponse(
    val dt: Long,
    val coord: Coord,
    val main: Main,
    val clouds: Clouds,
    val wind: Wind,
    val dt_txt: String,
    val name: String,
    val weather: List<WeatherInfo>,
    val sys: Sys,
    val timezone: Int
)


data class Main(val temp: Double, val feels_like: Double, val pressure: Double, val humidity: Int, val temp_min: Double, val temp_max: Double)

data class Coord(val lon: Double, val lat: Double)

data class Clouds(val all: Int)

data class Wind(val speed: Double, val deg: Double, val gust: Double)

data class WeatherInfo(val main: String, val description: String, val icon: String)

data class ForecastResponse(val city: CityForecast,
                            val list: List<WeatherResponse>)

data class CityForecast(val name: String,
                        val coord: Coord,
                        val country: String,
                        val population: Double)

data class Sys(val country: String,
               val sunrise: Long,
               val sunset: Long)

