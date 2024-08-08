package com.example.wetterapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wetterapp.data.Forecast

class ForecastAdapter(
    private val forecastList: List<Forecast>,
    private val useFahrenheit: Boolean
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = forecastList[position]
        holder.bind(forecast, useFahrenheit)
    }

    override fun getItemCount(): Int = forecastList.size

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTextView: TextView = itemView.findViewById(R.id.textViewDay)
        private val conditionTextView: TextView = itemView.findViewById(R.id.textViewCondition)
        private val tempTextView: TextView = itemView.findViewById(R.id.textViewTemp)

        fun bind(forecast: Forecast, useFahrenheit: Boolean) {
            dayTextView.text = forecast.date
            conditionTextView.text = forecast.weatherCondition
            val unit = if (useFahrenheit) "°F" else "°C"
            tempTextView.text = "High: ${forecast.maxTemp}$unit, Low: ${forecast.minTemp}$unit"
        }
    }
}