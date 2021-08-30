package com.udacity.politcalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.politcalpreparedness.R
import com.udacity.politcalpreparedness.databinding.FragmentElectionBinding
import com.udacity.politcalpreparedness.election.adapter.ElectionListAdapter

class ElectionsFragment : Fragment() {
    lateinit var viewModel: ElectionsViewModel
    private lateinit var binding: FragmentElectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflating and Returning the View with DataBindingUtil
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_election, container, false
        )

        // Get the viewmodel
        viewModel = ViewModelProvider(this).get(ElectionsViewModel::class.java)
        binding.electionViewModel = viewModel

        // Click on an entry of a upcoming election
        binding.upcomingRecycler.adapter = ElectionListAdapter(ElectionListAdapter.ElectionListener {
            viewModel.displayElectionDetails(it)
        })

        // Click on a entry of a saved election
        binding.savedRecycler.adapter = ElectionListAdapter(ElectionListAdapter.ElectionListener {
            viewModel.displayElectionDetails(it)
        })
        
        return binding.root
    }
}