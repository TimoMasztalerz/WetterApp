package com.example.wetterapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wetterapp.databinding.FragmentLocationBinding
import com.example.wetterapp.model.WeatherViewModel

// Fragment zur Auswahl des Standorts für die Wetteranzeige
class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding

    // ViewModel der Activity
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//binde die städte an den Spinner
        viewModel.cities.observe(viewLifecycleOwner) { cities ->
            val cityNames = cities.map { it.name }
            val adapter = ArrayAdapter(requireContext(), R.layout.location_spinner_item, cityNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.citySpinner.adapter = adapter
        }
//location bestätigen- daten abrufen
        binding.buttonSubmit.setOnClickListener {
            val selectedCityName = binding.citySpinner.selectedItem.toString()
            val selectedCity = viewModel.cities.value?.firstOrNull { it.name == selectedCityName }
            if (selectedCity != null) {
                // Ruft das Wetter für die ausgewählte Stadt ab
                viewModel.fetchWeatherForCity(selectedCity)
                // Navigiert zum WeatherFragment und übergibt den Stadtnamen
                val action = LocationFragmentDirections.actionLocationFragmentToWeatherFragment(cityName = selectedCityName)
                findNavController().navigate(action)
            }
        }

        // Lädt die Liste der Städte
        viewModel.fetchCities()
    }

    // Wird aufgerufen, wenn die View zerstört wird
    override fun onDestroyView() {
        super.onDestroyView()

    }
}
