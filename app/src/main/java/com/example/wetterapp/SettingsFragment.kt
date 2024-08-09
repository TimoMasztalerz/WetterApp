package com.example.wetterapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wetterapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)

//Der Switch bestimmt die shared preferences
        val useFahrenheit = sharedPreferences.getBoolean("useFahrenheit", false)
        binding.switchTemperatureUnit.isChecked = useFahrenheit
        binding.textViewTemperatureUnitStatus.text = "Current unit: ${if (useFahrenheit) "Fahrenheit" else "Celsius"}"

        binding.switchTemperatureUnit.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("useFahrenheit", isChecked).apply()
            binding.textViewTemperatureUnitStatus.text = "Current unit: ${if (isChecked) "Fahrenheit" else "Celsius"}"
        }

        setupBackButton()
    }

    private fun setupBackButton() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
