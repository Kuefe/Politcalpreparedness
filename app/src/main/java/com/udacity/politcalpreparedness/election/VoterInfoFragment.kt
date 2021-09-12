package com.udacity.politcalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.udacity.politcalpreparedness.databinding.FragmentVoterInfoBinding
import timber.log.Timber

const val KEY_FOLLOW = "key_follow"

class VoterInfoFragment : androidx.fragment.app.Fragment() {
    private var stateOfFollowButton = false

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
        val application = requireNotNull(activity).application
        val binding = FragmentVoterInfoBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val election = VoterInfoFragmentArgs.fromBundle(requireArguments()).selectedElection
        val viewModelFactory = VoterInfoViewModelFactory(election, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(VoterInfoViewModel::class.java)

        binding.stateLocations.setOnClickListener {
            loadUrlIntent(1)
        }

        binding.stateBallot.setOnClickListener {
            loadUrlIntent(2)
        }


        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state

        if (savedInstanceState != null) {
            stateOfFollowButton = savedInstanceState.getBoolean(KEY_FOLLOW)
        }

        viewModel.status.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer { status ->
            Timber.i("Timber: observe")
            stateOfFollowButton = status.state
        })


        //TODO: cont'd Handle save button clicks

        return binding.root
    }

    private fun loadUrlIntent(state: Int) {
        Timber.i("Timber: loadUrlIntent")
        val url = viewModel.getUrl(state)

        val webIntent: Intent = Uri.parse(url).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.i("Timber: onSaveInstanceState")
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_FOLLOW, stateOfFollowButton)
    }
}