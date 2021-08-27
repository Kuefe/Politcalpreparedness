package com.udacity.politcalpreparedness.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.udacity.politcalpreparedness.R
import com.udacity.politcalpreparedness.databinding.FragmentLaunchBinding

class LaunchFragment : Fragment() {
    private var _binding: FragmentLaunchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchBinding.inflate(inflater, container, false)


        //The complete onClickListener with Navigation using createNavigateOnClickListener
        binding.upcomingButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_launchFragment_to_electionsFragment)
        )

        binding.representativeButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_launchFragment_to_representativeFragment)
        )

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}