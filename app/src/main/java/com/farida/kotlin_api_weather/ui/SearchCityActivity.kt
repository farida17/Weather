package com.farida.kotlin_api_weather.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.farida.kotlin_api_weather.R
import com.farida.kotlin_api_weather.di.WeatherApplication
import com.farida.kotlin_api_weather.entity.CurrentWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject


class SearchCityActivity : AppCompatActivity() {

    @JvmField
    @BindView(R.id.toolbar)
    var toolbar: Toolbar? = null

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory

    private lateinit var viewModel: WeatherViewModel
    private val tag: String = SearchCityActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        (application as WeatherApplication).applicationComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

        search_btn.isEnabled = false
        search_btn.setOnClickListener {
            val cityName = searchCity.city?.text.toString()
             setupCurrentWeatherObserver(cityName)
            viewModel.hideKeyboard(this)
        }
        searchCity.city.addTextChangedListener(object: TextWatcher {
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

    private fun setupCurrentWeatherObserver(cityName: String): Disposable? {
        return viewModel.getWeather(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setWeatherViewsWithData(it)
                city.error = null
            }) { throwable ->
                Log.d(tag, throwable.toString())
                city.error = "Not found. Try another city"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun setWeatherViewsWithData(it: CurrentWeather?) {
        mainTemp.text =
            """${viewModel.convertKelvinToCelsius(temp = it?.main?.temp)} ℃"""
        rangeTemp.text =
            """${viewModel.convertKelvinToCelsius(it?.main?.temp_min)}  - ${viewModel.convertKelvinToCelsius(
                it?.main?.temp_max
            )} ℃"""
        description.text = it?.weather?.get(0)?.description.toString()
        humidity_value.text = """${it?.main?.humidity.toString()} %"""
        clouds_value.text = """${it?.clouds?.all.toString()} %"""
        wind_value.text = """${it?.wind?.speed.toString()} m/s"""
        pressure_value.text = """${it?.main?.pressure.toString()} hpa"""

        Glide.with(this)
            .load(it?.weather?.get(0)?.icon?.let { it1 -> viewModel.convertIconIdToUrl(icon = it1) })
            .into(weatherImg)
    }

}