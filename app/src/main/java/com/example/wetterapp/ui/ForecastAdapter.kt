package com.example.wetterapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wetterapp.databinding.ItemForecastBinding
import com.example.wetterapp.localdata.Forecast

class ForecastAdapter(
    private val forecastList: List<Forecast>,
    private val useFahrenheit: Boolean
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecastList[position], useFahrenheit)
    }

    override fun getItemCount(): Int = forecastList.size

    class ForecastViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(forecast: Forecast, useFahrenheit: Boolean) {
            binding.textViewDay.text = forecast.date
            binding.textViewCondition.text = forecast.weatherCondition
            val unit = if (useFahrenheit) "°F" else "°C"
            binding.textViewTemp.text = "High: ${forecast.maxTemp}$unit, Low: ${forecast.minTemp}$unit"
        }
    }
}
