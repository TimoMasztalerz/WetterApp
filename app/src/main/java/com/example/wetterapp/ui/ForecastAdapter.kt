package com.example.wetterapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wetterapp.R
import com.example.wetterapp.databinding.ItemForecastBinding
import com.example.wetterapp.localdata.Forecast

class ForecastAdapter(
    private val forecastList: List<Forecast>,
    private val useFahrenheit: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return forecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val forecastBinding = (holder.itemView.tag as? ItemForecastBinding) ?: return
        val forecast = forecastList[position]

        forecastBinding.textViewDay.text = forecast.date
        forecastBinding.textViewCondition.text = forecast.weatherCondition
        val unit = if (useFahrenheit) "°F" else "°C"
        forecastBinding.textViewTemp.text = "High: ${forecast.maxTemp}$unit, Low: ${forecast.minTemp}$unit"

        // Set weather icon based on condition
        forecastBinding.imageViewCondition.setImageResource(getWeatherIcon(forecast.weatherCondition))
    }

    override fun getItemCount(): Int = forecastList.size

    private fun forecastViewHolder(binding: ItemForecastBinding): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(binding.root) {
            init {
                itemView.tag = binding
            }
        }
    }

    private fun getWeatherIcon(condition: String): Int {
        return when (condition.toLowerCase()) {
            "sunny" -> R.drawable.sun
            "cloudy" -> R.drawable.cloud
            "rainy" -> R.drawable.raining

            else -> R.drawable.partly_cloud
        }
    }
}