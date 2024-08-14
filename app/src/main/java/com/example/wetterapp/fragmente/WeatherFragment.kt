package com.example.wetterapp.fragmente

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.wetterapp.R
import com.example.wetterapp.databinding.FragmentWeatherBinding
import com.example.wetterapp.model.WeatherViewModel

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = WeatherFragmentArgs.fromBundle(requireArguments())
        binding.textViewCity.text = args.cityName

        // Retrieve the temperature unit preference
        val sharedPreferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val useFahrenheit = sharedPreferences.getBoolean("useFahrenheit", false)

        viewModel.weatherData.observe(viewLifecycleOwner) { weatherResponse ->
            Log.d("WeatherFragment", "Received weather data: $weatherResponse")

            val temperatureInCelsius = weatherResponse.getValueOrNA("t_2m:C")?.toDoubleOrNull() ?: 0.0
            val displayTemperature = if (useFahrenheit) {
                celsiusToFahrenheit(temperatureInCelsius)
            } else {
                temperatureInCelsius
            }

            binding.textViewTemperature.text = "Temperature: ${String.format("%.1f", displayTemperature)}°${if (useFahrenheit) "F" else "C"}"
            binding.textViewHumidity.text = "Humidity: ${weatherResponse.getValueOrNA("relative_humidity_2m:p") ?: "N/A"}%"
            binding.textViewWindSpeed.text = "Wind Speed: ${weatherResponse.getValueOrNA("wind_speed_10m:ms") ?: "N/A"} m/s"
        }

        viewModel.weatherSymbolUrl.observe(viewLifecycleOwner) { url ->
            Log.d("WeatherFragment", "Weather Symbol URL: $url")
            url?.let {
                Glide.with(this)
                    .load(it)
                    .placeholder(R.drawable.raining) // Placeholder
                    .into(binding.imageViewWeatherSymbol)
            }
        }

        setupBackButton()

        // Fetch weather data for the selected city
        val selectedCity = viewModel.cities.value?.find { it.name == args.cityName }
        selectedCity?.let {
            Log.d("WeatherFragment", "Fetching weather for city: ${it.name}")
            viewModel.fetchWeatherForCity(it)
        }
    }

    private fun setupBackButton() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun celsiusToFahrenheit(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }
}