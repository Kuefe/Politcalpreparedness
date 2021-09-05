package com.udacity.politcalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.politcalpreparedness.database.ElectionDatabase.Companion.getInstance
import com.udacity.politcalpreparedness.network.CivicsApi
import com.udacity.politcalpreparedness.network.models.Election
import com.udacity.politcalpreparedness.representative.ElectionsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(application: Application) : ViewModel() {
    private val database = getInstance(application)
    private val electionsRepository = ElectionsRepository(database)

    // Live data val for saved elections
    private val _savedElections = MutableLiveData<List<Election>>()

    // The external immutable LiveData for the AsteroidApiStatus
    val savedElections: LiveData<List<Election>>
        get() = _savedElections

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _upcomingElections = MutableLiveData<List<Election>>()

    // The external LiveData interface to the election is immutable, so only this class can modify
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    /**
     * Variable that tells the Fragment to navigate to a specific [VoterInfoFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */

    private val _navigateToSelectedUpcomingElection = MutableLiveData<Election>()

    /**
     * If this is non-null, immediately navigate to [SleepQualityFragment] and call [displayPropertyDetailsComplete]
     */
    val navigateToSelectedUpcomingElection: LiveData<Election>
        get() = _navigateToSelectedUpcomingElection

    /**
     * Call this immediately after navigating to [SleepQualityFragment]
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

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info


    init {
        Timber.i("Timber: init ElectionViewModel")
        viewModelScope.launch {
            electionsRepository.getElectionsFromNetwork()
        }
    }


}