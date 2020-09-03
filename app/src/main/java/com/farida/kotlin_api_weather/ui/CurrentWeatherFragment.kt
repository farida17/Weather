package com.farida.kotlin_api_weather.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.farida.kotlin_api_weather.R
import com.farida.kotlin_api_weather.common.TransformAdapter.convertIconIdToUrl
import com.farida.kotlin_api_weather.common.TransformAdapter.convertKelvinToCelsius
import com.farida.kotlin_api_weather.common.TransformAdapter.convertUnixTimeToTime
import com.farida.kotlin_api_weather.entity.CurrentWeather
import kotlinx.android.synthetic.main.fragment_current_weather.*

class CurrentWeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherAdapter = WeatherAdapter()
    }

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
        initRecyclerView()
        viewModel.currentWeather.observe(viewLifecycleOwner, Observer {
            setWeatherViews(it)
        })
        viewModel.forecast.observe(viewLifecycleOwner, Observer {
            weatherAdapter.submitList(it.list)
        })
    }

    private fun initRecyclerView() {
        list_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = weatherAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setWeatherViews(it: CurrentWeather?) {
        mainTemp.text =
            """${convertKelvinToCelsius(temp = it?.main?.temp)} ℃"""
        rangeTemp.text = """${convertKelvinToCelsius(it?.main?.temp_min)}  / ${convertKelvinToCelsius(
            it?.main?.temp_max
        )} ℃"""
        description.text = it?.weather?.get(0)?.description.toString()
        feels_like_value.text = """${convertKelvinToCelsius(it?.main?.feels_like)} ℃"""
        humidity_value.text = """${it?.main?.humidity} %"""
        clouds_value.text = """${it?.clouds?.all} %"""
        wind_value.text = """${it?.wind?.speed} m/s"""
        pressure_value.text = """${it?.main?.pressure?.toInt()} hpa"""
        geo_value.text = it?.coord?.latitude.toString() + ", " + it?.coord?.longitude.toString()
        sunrise_value.text = convertUnixTimeToTime(it?.sys?.sunrise, it?.timezone)
        sunset_value.text = convertUnixTimeToTime(it?.sys?.sunset, it?.timezone)

        Glide.with(this)
            .load(it?.weather?.get(0)?.icon?.let { it1 -> convertIconIdToUrl(icon = it1) })
            .into(weatherImg)
    }
}