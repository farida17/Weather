package com.farida.kotlin_api_weather.common

import com.farida.kotlin_api_weather.api.ForecastResponse
import com.farida.kotlin_api_weather.api.WeatherResponse
import com.farida.kotlin_api_weather.entity.*

object TransformAdapter {
    fun transformToCurrentWeather(
        cityName: String,
        weatherResponse: WeatherResponse?
    ): CurrentWeather {
        val dtTxt = weatherResponse?.dt_txt
        val main = MainData(
            weatherResponse?.main?.temp,
            weatherResponse?.main?.pressure,
            weatherResponse?.main?.feels_like,
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
        val name = if (cityName.isEmpty()) weatherResponse?.name!! else cityName
        return CurrentWeather(
            cityName = name,
            main = main,
            clouds = clouds,
            wind = wind,
            dt_txt = dtTxt,
            coord = coord,
            weather = weather
        )
    }

    fun transformToForecast(cityName: String, forecastResponse: ForecastResponse?): Forecast {
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
        val list = ArrayList<CurrentWeather>()
            forecastResponse?.list?.forEachIndexed { period, weatherResponse ->
                list.add(
                    CurrentWeather(
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
                        weather = arrayListOf(weather[period])))
            }
        return Forecast(city = city, list = list)
    }
        fun convertKelvinToCelsius(temp: Double?): Int {
            val celsiusTemp = (temp!! - 273)
            return celsiusTemp.toInt()
        }

        fun convertIconIdToUrl(icon: String?) = "https://openweathermap.org/img/w/$icon.png"
}