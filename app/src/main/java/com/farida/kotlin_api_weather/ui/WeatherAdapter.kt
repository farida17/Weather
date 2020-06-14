package com.farida.kotlin_api_weather.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farida.kotlin_api_weather.R
import com.farida.kotlin_api_weather.common.TransformAdapter.convertIconIdToUrl
import com.farida.kotlin_api_weather.common.TransformAdapter.convertKelvinToCelsius
import com.farida.kotlin_api_weather.entity.CurrentWeather
import kotlinx.android.synthetic.main.list_item.view.*

class WeatherAdapter : ListAdapter<CurrentWeather, WeatherAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(forecastElement: CurrentWeather) {
            with(forecastElement) {
                itemView.day_name_text_view.text = dt_txt
                itemView.date_text_view.text = weather?.get(0)?.description
                itemView.max_temp_text_view.text =
                    "${convertKelvinToCelsius(main?.temp_max)}℃"
                itemView.min_temp_text_view.text =
                    "${convertKelvinToCelsius(main?.temp_min)}℃"
                Glide.with(itemView.context)
                    .load(convertIconIdToUrl(weather?.get(0)?.icon))
                    .into(itemView.weather_image_view)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CurrentWeather>() {
            override fun areItemsTheSame(
                oldItem: CurrentWeather,
                newItem: CurrentWeather
            ) = oldItem.dt_txt == newItem.dt_txt

            override fun areContentsTheSame(
                oldItem: CurrentWeather,
                newItem: CurrentWeather
            ) = oldItem == newItem

        }
    }
}