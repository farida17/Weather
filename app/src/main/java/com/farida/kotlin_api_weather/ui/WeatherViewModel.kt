package com.farida.kotlin_api_weather.ui

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModel
import com.farida.kotlin_api_weather.repository.WeatherRepository
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    fun getWeather(cityName: String) = weatherRepository.getWeather(cityName)

    fun convertKelvinToCelsius(temp: Double?): Int {
        val celsiusTemp = (temp!! - 273)
        return celsiusTemp.toInt()
    }
    fun convertIconIdToUrl(icon: String) = "https://openweathermap.org/img/w/$icon.png"

    fun hideKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}