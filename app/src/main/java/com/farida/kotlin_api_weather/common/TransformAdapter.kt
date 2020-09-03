package com.farida.kotlin_api_weather.common

import com.farida.kotlin_api_weather.api.ForecastResponse
import com.farida.kotlin_api_weather.api.WeatherResponse
import com.farida.kotlin_api_weather.entity.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.collections.ArrayList

object TransformAdapter {
    fun transformToCurrentWeather(
        cityName: String,
        weatherResponse: WeatherResponse?
    ): CurrentWeather {
        val timezone = weatherResponse?.timezone
        val dt = weatherResponse?.dt
        val dtTxt = weatherResponse?.dt_txt
        val main = MainData(
            weatherResponse?.main?.temp,
            weatherResponse?.main?.feels_like,
            weatherResponse?.main?.pressure,
            weatherResponse?.main?.humidity,
            weatherResponse?.main?.temp_min,
            weatherResponse?.main?.temp_max
        )
        val clouds = CloudsData(weatherResponse?.clouds?.all)
        val wind = WindData(
            weatherResponse?.wind?.speed,
            weatherResponse?.wind?.deg,
            weatherResponse?.wind?.gust
        )
        val coord = CoordData(weatherResponse?.coord?.lat, weatherResponse?.coord?.lon)
        val weather = ArrayList<WeatherInfo>()
        weatherResponse?.weather?.forEach {
            weather.add(WeatherInfo(it.main, it.description, it.icon))
        }

        val sys = Sys(weatherResponse?.sys?.country, weatherResponse?.sys?.sunrise, weatherResponse?.sys?.sunset)
        val name = if (cityName.isEmpty()) weatherResponse?.name!! else cityName
        return CurrentWeather(
            dt = dt,
            cityName = name,
            main = main,
            clouds = clouds,
            wind = wind,
            dt_txt = dtTxt,
            coord = coord,
            weather = weather,
            sys = sys,
            timezone = timezone
        )
    }

    fun transformToForecast(cityName: String, forecastResponse: ForecastResponse?): Forecast {
        val dt = forecastResponse?.list?.get(0)?.dt
        val coord =
            CoordData(forecastResponse?.city?.coord?.lon, forecastResponse?.city?.coord?.lat)
        val city = City(
            forecastResponse?.city?.name,
            coord,
            forecastResponse?.city?.country,
            forecastResponse?.city?.population
        )
        val weather = ArrayList<WeatherInfo>()
        forecastResponse?.list?.forEach {
            weather.add(WeatherInfo(it.weather[0].main, it.weather[0].description, it.weather[0].icon))
        }
        val timezone = forecastResponse?.list?.get(0)?.timezone
        val sys = Sys(forecastResponse?.list?.get(0)?.sys?.country, forecastResponse?.list?.get(0)?.sys?.sunrise, forecastResponse?.list?.get(0)?.sys?.sunset)
        val list = ArrayList<CurrentWeather>()
            forecastResponse?.list?.forEachIndexed { period, weatherResponse ->
                list.add(
                    CurrentWeather(
                        dt = dt,
                        cityName = cityName,
                        main = MainData(
                            weatherResponse.main.temp,
                            weatherResponse.main.pressure,
                            weatherResponse.main.feels_like,
                            weatherResponse.main.humidity,
                            weatherResponse.main.temp_min,
                            weatherResponse.main.temp_max
                        ),
                        clouds = CloudsData(weatherResponse.clouds.all),
                        wind = WindData(
                            weatherResponse.wind.speed,
                            weatherResponse.wind.deg,
                            weatherResponse.wind.gust
                        ),
                        dt_txt = weatherResponse.dt_txt,
                        coord = coord,
                        sys = sys,
                        timezone = timezone,
                        weather = arrayListOf(weather[period])))
            }  
        return Forecast(city = city, list = list)
    }
        fun convertKelvinToCelsius(temp: Double?): Int {
            val celsiusTemp = (temp!! - 273).toInt()
            return celsiusTemp
        }

        fun convertIconIdToUrl(icon: String?) = "https://openweathermap.org/img/w/$icon.png"

    fun convertUnixTimeToTime(unixTime: Long?, timezone: Int?): String {
        val dt = unixTime?.let {
            Instant.ofEpochSecond(it)
                .atZone(timezone?.let { it1 -> ZoneOffset.ofTotalSeconds(it1) })
                .toLocalTime()
        }
        return dt?.truncatedTo(ChronoUnit.MINUTES).toString()
    }

    fun convertStringToDateTime(currentTime: String): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatter2: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM HH:mm")
        val dt_txt: LocalDateTime = LocalDateTime.parse(currentTime, formatter)
        return dt_txt.format(formatter2).toString()
    }
}
