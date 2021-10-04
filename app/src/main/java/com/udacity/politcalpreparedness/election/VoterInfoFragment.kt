package com.udacity.politcalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.udacity.politcalpreparedness.R
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

        if (savedInstanceState != null) {
            stateOfFollowButton = savedInstanceState.getBoolean(KEY_FOLLOW)
        }

        viewModel.status.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer { status ->
            stateOfFollowButton = status.state
        })

        return binding.root
    }

    private fun loadUrlIntent(state: Int) {
        val url = viewModel.getUrl(state)
        if (url.isNullOrEmpty()) {
            Toast.makeText(context, getString(R.string.no_election_information_found), Toast.LENGTH_SHORT).show()
        } else {
            val webIntent: Intent = Uri.parse(url).let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            startActivity(webIntent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_FOLLOW, stateOfFollowButton)
    }
}