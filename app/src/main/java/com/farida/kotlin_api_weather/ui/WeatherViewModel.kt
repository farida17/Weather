package com.farida.kotlin_api_weather.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farida.kotlin_api_weather.repository.WeatherRepository
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    private val newCityName = MutableLiveData<String>()

    fun selectCityName(cityName: String) {
        newCityName.value = cityName
    }
    fun getCityName(): LiveData<String> {
        return newCityName
    }

    fun getWeather(cityName: String) = weatherRepository.getWeather(cityName)

    fun getFiveDaysForecast(cityName: String) = weatherRepository.getFiveDaysForecast(cityName)

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}