package com.example.wetterapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wetterapp.data.Forecast
import com.example.wetterapp.databinding.FragmentForecastBinding
import com.example.wetterapp.ui.ForecastAdapter

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

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
//hole die Preferences aus dem SettingsFragment für die temperatur anzeige C oder F
        val sharedPreferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val useFahrenheit = sharedPreferences.getBoolean("useFahrenheit", false)

        // Beispiel data
        val exampleData = listOf(
            Forecast("2024-08-07", 25.0, 18.0, "Sunny"),
            Forecast("2024-08-08", 22.0, 16.0, "Cloudy"),
            Forecast("2024-08-09", 20.0, 15.0, "Rainy"),
            Forecast("2024-08-10", 23.0, 17.0, "Partly Cloudy"),
            Forecast("2024-08-11", 24.0, 19.0, "Sunny")
        )
//umgewandelte Temperaturen
        val convertedData = exampleData.map { forecast ->
            val maxTemp = if (useFahrenheit) convertCelsiusToFahrenheit(forecast.maxTemp) else forecast.maxTemp
            val minTemp = if (useFahrenheit) convertCelsiusToFahrenheit(forecast.minTemp) else forecast.minTemp
            forecast.copy(maxTemp = maxTemp, minTemp = minTemp)
        }

        binding.recyclerViewForecast.adapter = ForecastAdapter(convertedData, useFahrenheit)
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
    }
}
