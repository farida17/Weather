package com.farida.kotlin_api_weather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farida.kotlin_api_weather.entity.CurrentWeather
import com.farida.kotlin_api_weather.entity.Forecast
import com.farida.kotlin_api_weather.repository.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    val currentWeather: MutableLiveData<CurrentWeather> = MutableLiveData()

    val forecast: MutableLiveData<Forecast> = MutableLiveData()

    private var lastSubscription1: Disposable? = null
    private var lastSubscription2: Disposable? = null

    fun loadCurrentWeather(cityName: String) {
        lastSubscription1?.dispose()
        lastSubscription1 = weatherRepository.getWeather(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t: CurrentWeather? ->
                currentWeather.postValue(t)
            }
    }
    fun loadForecast(cityName: String) {
        lastSubscription2?.dispose()
        lastSubscription2 = weatherRepository.getFiveDaysForecast(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t: Forecast? ->
                forecast.postValue(t)
            }
    }

    override fun onCleared() {
        super.onCleared()
        lastSubscription1?.dispose()
        lastSubscription2?.dispose()
    }
}