package com.example.wetterapp.fragmente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wetterapp.R
import com.example.wetterapp.databinding.FragmentLocationBinding
import com.example.wetterapp.model.WeatherViewModel

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

        // Binde die Städte an den Spinner
        setupCitySpinner()

        // Location bestätigen - Daten abrufen
        binding.buttonSubmit.setOnClickListener {
            onCitySelected()
        }

        // Lädt die Liste der Städte
        viewModel.fetchCities()
    }

    private fun setupCitySpinner() {
        viewModel.cities.observe(viewLifecycleOwner) { cities ->
            val cityNames = cities.map { it.name }
            val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, cityNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.citySpinner.adapter = adapter
        }
    }

    private fun onCitySelected() {
        val selectedCityName = binding.citySpinner.selectedItem.toString()
        viewModel.fetchWeatherForCity(selectedCityName)

        val action = LocationFragmentDirections.actionLocationFragmentToWeatherFragment(cityName = selectedCityName)
        findNavController().navigate(action)
    }
    // Wird aufgerufen, wenn die View zerstört wird
    override fun onDestroyView() {
        super.onDestroyView()
    }
}