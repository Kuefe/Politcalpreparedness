package com.udacity.politcalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


class VoterInfoFragment : Fragment() {
    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: VoterInfoViewModel by lazy {
        ViewModelProvider(this).get(VoterInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        val binding =
            com.udacity.politcalpreparedness.databinding.FragmentVoterInfoBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the ElectionViewModel
        binding.voterInfoViewModel = viewModel

        return binding.root
    }

    //TODO: Create method to load URL intents
}