package com.example.wetterapp.fragmente

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wetterapp.databinding.FragmentForecastBinding
import com.example.wetterapp.localdata.AppDatabase
import com.example.wetterapp.localdata.Forecast
import com.example.wetterapp.localdata.ForecastDao
import com.example.wetterapp.ui.ForecastAdapter
import kotlinx.coroutines.launch

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    private lateinit var forecastDao: ForecastDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewForecast.layoutManager = LinearLayoutManager(requireContext())

        setupBackButton()

        val sharedPreferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val useFahrenheit = sharedPreferences.getBoolean("useFahrenheit", false)

        val db = AppDatabase.getDatabase(requireContext())
        forecastDao = db.forecastDao()

        viewLifecycleOwner.lifecycleScope.launch {
            saveForecasts(forecastDao)

            val forecasts = forecastDao.getAllForecasts()

            val convertedData = forecasts.map { forecast ->
                val maxTemp = if (useFahrenheit) convertCelsiusToFahrenheit(forecast.maxTemp) else forecast.maxTemp
                val minTemp = if (useFahrenheit) convertCelsiusToFahrenheit(forecast.minTemp) else forecast.minTemp
                forecast.copy(maxTemp = maxTemp, minTemp = minTemp)
            }

            binding.recyclerViewForecast.adapter = ForecastAdapter(convertedData, useFahrenheit)
        }
    }

    private suspend fun saveForecasts(forecastDao: ForecastDao) {
        if (forecastDao.getAllForecasts().isEmpty()) {
            val exampleData = listOf(
                Forecast(date = "2024-08-07", maxTemp = 25.0, minTemp = 18.0, weatherCondition = "Sunny"),
                Forecast(date = "2024-08-08", maxTemp = 22.0, minTemp = 16.0, weatherCondition = "Cloudy"),
                Forecast(date = "2024-08-09", maxTemp = 20.0, minTemp = 15.0, weatherCondition = "Rainy"),
                Forecast(date = "2024-08-10", maxTemp = 23.0, minTemp = 17.0, weatherCondition = "Partly Cloudy"),
                Forecast(date = "2024-08-11", maxTemp = 24.0, minTemp = 19.0, weatherCondition = "Sunny")
            )
            forecastDao.insertAll(exampleData)
        }
    }


    private fun setupBackButton() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun convertCelsiusToFahrenheit(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
