package com.udacity.politcalpreparedness.Launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.udacity.politcalpreparedness.R
import com.udacity.politcalpreparedness.databinding.FragmentLaunchBinding

/**
 * A simple [Fragment] subclass.
 * Use the [LaunchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LaunchFragment : Fragment() {
    private var _binding: FragmentLaunchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLaunchBinding.inflate(inflater, container, false)

        binding.upcomingButton.setOnClickListener { upComing() }
        binding.representativesButton.setOnClickListener { myRepresentatives() }

        return binding.root
    }

    fun upComing() {

    }

    fun myRepresentatives() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}