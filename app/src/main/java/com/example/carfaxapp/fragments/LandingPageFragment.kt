package com.example.carfaxapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carfaxapp.databinding.LandingPageFragmentBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LandingPageFragment : Fragment() {

    private var _binding: LandingPageFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LandingPageFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //click listeners
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}