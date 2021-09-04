package com.udacity.politcalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.politcalpreparedness.database.ElectionDao
import com.udacity.politcalpreparedness.network.CivicsApi
import com.udacity.politcalpreparedness.network.models.Election
import com.udacity.politcalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch
import timber.log.Timber

class VoterInfoViewModel(private val dataSource: ElectionDao): ViewModel() {
    //TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

/*    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()

    // The external LiveData interface to the election is immutable, so only this class can modify
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    init {
        Timber.i("Timber: init VoterInfoViewModel")
    }

    private fun getVoterInfoFromNetwork(election: Election) {
        Timber.i("Timber: getVoterInfoFromNetwork")
        var address = election.division.country + " " + election.division.state
        var electionId = election.id
        viewModelScope.launch {
            try {
                _voterInfo.value = CivicsApi.retrofitService.getVoterInfo(address, electionId)
            } catch (e: Exception) {
                _voterInfo.value = null
            }
        }
    }*/
}