package com.example.wetterapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wetterapp.R
import com.example.wetterapp.localdata.Forecast

class ForecastAdapter(
    private val forecastList: List<Forecast>,
    private val useFahrenheit: Boolean
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecastList[position], useFahrenheit)
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
