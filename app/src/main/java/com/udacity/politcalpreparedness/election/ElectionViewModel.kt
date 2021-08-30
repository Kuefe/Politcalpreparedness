package com.udacity.politcalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.politcalpreparedness.network.models.Election
import timber.log.Timber

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel : ViewModel() {

    // Live data val for upcoming elections
    private val _upcomingElections = MutableLiveData<List<Election>>()

    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    // Live data val for saved elections
    private val _savedElections = MutableLiveData<List<Election>>()

    // The external immutable LiveData for the AsteroidApiStatus
    val savedElections: LiveData<List<Election>>
        get() = _savedElections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info
    fun displayElectionDetails(election: Election){

    }

    init {
        Timber.i("Timber: init")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Timber: onCleared")
    }
}