package com.farida.kotlin_api_weather.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farida.kotlin_api_weather.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.five_day_forecast_fragment.*

class FiveDayForecastFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherAdapter = WeatherAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.five_day_forecast_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        initRecyclerView()
        setFiveDayForecastObserver("London")
    }

    private fun initRecyclerView() {
        list_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = weatherAdapter
        }
    }

    private fun setFiveDayForecastObserver(cityName: String): Disposable {
        return viewModel.getFiveDaysForecast(cityName)
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    weatherAdapter.submitList(it.list)
                }) { throwable ->
                    Log.d(tag, throwable.toString())
                    error(message = "Not found. Try another city")
                }
    }

//    private fun setFiveDaysForecastViews(it: Forecast?) {
//        //day_name_text_view.text = it?.list?.first()?.cityName
//        max_temp_text_view.text = 400.toString()
//            //convertKelvinToCelsius(it?.list?.get(0)?.main?.temp_max).toString()
//        min_temp_text_view.text = 500.toString()
//            //it?.list?.get(0)?.main?.temp_min.toString()
//    }
}
