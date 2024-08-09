package com.example.wetterapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wetterapp.databinding.FragmentWarningBinding

class WarningFragment : Fragment() {

    private var _binding: FragmentWarningBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWarningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayWarnings()
    }
//BespielWarnungen
    private fun displayWarnings() {
        // Example warnings
        val warnings = listOf(
            "Thunderstorm Warning",
            "heavy rain",
            "gusts of wind"
        )
//anzeige der Warnungen aus der Warning Liste nach index
        binding.textViewWarning1.text = warnings.get(0)
        binding.textViewWarning2.text = warnings.get(1)
        binding.textViewWarning3.text = warnings.get(2)
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}
