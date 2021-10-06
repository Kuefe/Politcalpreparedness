package com.udacity.politcalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.politcalpreparedness.database.ElectionDatabase.Companion.getInstance
import com.udacity.politcalpreparedness.election.repository.ElectionsRepository
import com.udacity.politcalpreparedness.network.models.Election
import kotlinx.coroutines.launch

enum class ElectionStatus { LOADING, ERROR, DONE }

class ElectionsViewModel(application: Application) : ViewModel() {
    private val database = getInstance(application)
    private val electionsRepository = ElectionsRepository(database)

    private val _status = MutableLiveData<ElectionStatus>()
    val status: LiveData<ElectionStatus>
        get() = _status

    /**
     * Variable that tells the Fragment to navigate to a specific [VoterInfoFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */

    private val _navigateToSelectedUpcomingElection = MutableLiveData<Election>()

    /**
     * If this is non-null, immediately navigate to [VoterInfoFragment] and call [displayPropertyDetailsComplete]
     */
    val navigateToSelectedUpcomingElection: LiveData<Election>
        get() = _navigateToSelectedUpcomingElection

    /**
     * Call this immediately after navigating to [VoterInfoFragment]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate
     * twice.
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedUpcomingElection.value = null
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedUpcomingElection] [MutableLiveData]
     * @param election The [Election] that was clicked on.
     */
    fun displayVoterInfo(election: Election) {
        _navigateToSelectedUpcomingElection.value = election
    }

    private val _upcomingElections = MutableLiveData<List<Election>>()

    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    private val _followElections = MutableLiveData<List<Election>>()

    val followElections: LiveData<List<Election>>
        get() = _followElections

    init {
        getUpcomingElections()
    }

    fun refreshFollowElection() {
        viewModelScope.launch {
            try {
                _followElections.value = electionsRepository.getFollowElectionList()
            } catch (e: Exception) {
                _followElections.value = ArrayList()
            }
        }
    }

    private fun getUpcomingElections() {

        viewModelScope.launch {
            _status.value = ElectionStatus.LOADING
            try {
                electionsRepository.getElectionsFromNetwork()
                _upcomingElections.value = electionsRepository.getElectionList()
                _status.value = ElectionStatus.DONE
            } catch (e: Exception) {
                _upcomingElections.value = ArrayList()
                _status.value = ElectionStatus.ERROR
            }
        }
    }
}