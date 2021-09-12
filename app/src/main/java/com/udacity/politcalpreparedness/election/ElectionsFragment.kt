package com.udacity.politcalpreparedness.election

import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.politcalpreparedness.databinding.FragmentElectionBinding
import com.udacity.politcalpreparedness.election.adapter.ElectionListAdapter
import com.udacity.politcalpreparedness.election.adapter.FollowElectionListAdapter
import com.udacity.politcalpreparedness.network.models.Election

class ElectionsFragment : Fragment() {
    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private lateinit var viewModel: ElectionsViewModel
    private lateinit var viewModelFactory: ElectionsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application

        val binding =
            com.udacity.politcalpreparedness.databinding.FragmentElectionBinding.inflate(inflater)
        viewModelFactory = ElectionsViewModelFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ElectionsViewModel::class.java)

        // Giving the binding access to the ElectionViewModel
        binding.viewModel = viewModel

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Sets the adapter of the upcoming ElectionView RecyclerView with clickHandler lambda that
        // tells the viewModel when a election is clicked
        binding.upcomingRecycler.adapter =
            ElectionListAdapter(ElectionListAdapter.OnClickListener {
                viewModel.displayVoterInfo(it)
            })

        // Sets the adapter of the followed ElectionView RecyclerView with clickHandler lambda that
        // tells the viewModel when a election is clicked
        binding.savedRecycler.adapter =
            FollowElectionListAdapter(FollowElectionListAdapter.OnClickListener {
                viewModel.displayVoterInfo(it)
            })

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedUpcomingElection.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }
}