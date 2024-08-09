package com.example.wetterapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
        val sharedPreferences =
            requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val useFahrenheit = sharedPreferences.getBoolean("useFahrenheit", false)

        viewModel.weatherData.observe(viewLifecycleOwner) { weatherResponse ->
            val temperatureInCelsius = //temperaturAuf2MeterInCelsius
                weatherResponse.getValueOrNA("t_2m:C")?.toDoubleOrNull() ?: 0.0
            //C oder F
            val displayTemperature = if (useFahrenheit) {
                convertCelsiusToFahrenheit(temperatureInCelsius)
            } else {
                temperatureInCelsius
            }
            //Binding
            
            binding.textViewTemperature.text =
                "Temperature: ${displayTemperature}°${if (useFahrenheit) "F" else "C"}"
            binding.textViewHumidity.text =
                "Humidity: ${weatherResponse.getValueOrNA("relative_humidity_2m:p") ?: "N/A"}%"
            binding.textViewWindSpeed.text =
                "Wind Speed: ${weatherResponse.getValueOrNA("wind_speed_10m:ms") ?: "N/A"} m/s"
        }

        setupBackButton()

        // Navigiere zu ForecastFragment
        binding.buttonForecast.setOnClickListener {
            findNavController().navigate(R.id.action_weatherFragment_to_forecastFragment)
        }

    }

    private fun setupBackButton() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
//wandle in fahrenheit um
    private fun convertCelsiusToFahrenheit(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }
}
