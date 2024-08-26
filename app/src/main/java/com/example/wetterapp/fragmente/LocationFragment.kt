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
        setupCitySpinner()
        binding.buttonSubmit.setOnClickListener { onCitySelected() }
    }

    private fun setupCitySpinner() {
        viewModel.cities.observe(viewLifecycleOwner) { cities ->
            val cityNames = cities.map { it.name }
            val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, cityNames)
            binding.citySpinner.adapter = adapter
        }
    }

    private fun onCitySelected() {
        val selectedCityName = binding.citySpinner.selectedItem.toString()
        viewModel.fetchWeatherForCity(selectedCityName)
        val action =
            LocationFragmentDirections.actionLocationFragmentToWeatherFragment(cityName = selectedCityName)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}