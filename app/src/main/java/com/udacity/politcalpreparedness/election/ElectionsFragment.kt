package com.udacity.politcalpreparedness.election

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.politcalpreparedness.election.adapter.ElectionListAdapter

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
        val binding =
            com.udacity.politcalpreparedness.databinding.FragmentElectionBinding.inflate(inflater)
        viewModelFactory = ElectionsViewModelFactory()

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ElectionsViewModel::class.java)
        // Giving the binding access to the ElectionViewModel
        binding.electionsViewModel = viewModel

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Sets the adapter of the upcoming ElectionView RecyclerView with clickHandler lambda that
        // tells the viewModel when a election is clicked
        binding.upcomingRecycler.adapter =
            ElectionListAdapter(ElectionListAdapter.ElectionListener {
                viewModel.displayVoterInfo(it)
            })

//        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
//        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
//        // for another navigation event.
//        viewModel.navigateToSelectedUpcomingElection.observe(viewLifecycleOwner, Observer {
//            if (null != it) {
//                // Must find the NavController from the Fragment
//                this.findNavController().navigate(
//                    ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment()
//                )
//                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
//                // Reset state to make sure we only navigate once, even if the device
//                // has a configuration change.
//                viewModel.displayPropertyDetailsComplete()
//            }
//        })

        return binding.root
    }
}