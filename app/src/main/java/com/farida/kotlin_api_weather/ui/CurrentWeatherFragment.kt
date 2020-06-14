package com.farida.kotlin_api_weather.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.farida.kotlin_api_weather.R
import com.farida.kotlin_api_weather.common.TransformAdapter.convertIconIdToUrl
import com.farida.kotlin_api_weather.common.TransformAdapter.convertKelvinToCelsius
import com.farida.kotlin_api_weather.entity.CurrentWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.android.synthetic.main.fragment_current_weather.view.*

class CurrentWeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        search_btn.isEnabled = false
        search_btn.setOnClickListener {
            val cityName = searchCity?.city?.text.toString()
            context?.let { it1 -> viewModel.hideSoftKeyBoard(it1, view) }
            setCurrentWeatherObserver(cityName)

        }
        searchCity.city.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if(s.isEmpty()) {
                        search_btn.isEnabled = false
                        searchCity.error = null
                    } else {
                        search_btn.isEnabled = true
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()) {
                    searchCity.error = null
                    search_btn.isEnabled = true
                } else {
                    search_btn.isEnabled = false
                }
            }
        })
    }

    private fun setCurrentWeatherObserver(cityName: String): Disposable? {
         return viewModel.run {
            getWeather(cityName)
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    setWeatherViews(it)
                    city.error = null
                }) { throwable ->
                Log.d(tag, throwable.toString())
                city.error = "Not found. Try another city"
            }
         }
    }

    @SuppressLint("SetTextI18n")
    private fun setWeatherViews(it: CurrentWeather?) {
        mainTemp.text =
            """${convertKelvinToCelsius(temp = it?.main?.temp)} ℃"""
        rangeTemp.text =
            """${convertKelvinToCelsius(it?.main?.temp_min)}  - ${convertKelvinToCelsius(
                it?.main?.temp_max
            )} ℃"""
        description.text = it?.weather?.get(0)?.description.toString()
        humidity_value.text = """${it?.main?.humidity.toString()} %"""
        clouds_value.text = """${it?.clouds?.all.toString()} %"""
        wind_value.text = """${it?.wind?.speed.toString()} m/s"""
        pressure_value.text = """${it?.main?.pressure.toString()} hpa"""

        Glide.with(this)
            .load(it?.weather?.get(0)?.icon?.let { it1 -> convertIconIdToUrl(icon = it1) })
            .into(weatherImg)
    }

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }
}