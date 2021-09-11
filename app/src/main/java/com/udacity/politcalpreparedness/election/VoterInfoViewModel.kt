package com.udacity.politcalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.politcalpreparedness.network.CivicsApi
import com.udacity.politcalpreparedness.network.models.Election
import com.udacity.politcalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch
import timber.log.Timber


class VoterInfoViewModel(election: Election, app: Application) : ViewModel() {

    //TODO: Add live data to hold voter info
    private val _selectedElection = MutableLiveData<Election>()

    val selectedElection: LiveData<Election>
        get() = _selectedElection

    //TODO: Add var and methods to populate voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()

    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    private val _votingLocationFinderUrl = MutableLiveData<String>()
    val votingLocationFinderUrl: LiveData<String>
        get() = _votingLocationFinderUrl

    private val _ballotInfoUrl = MutableLiveData<String>()

    val ballotInfoUrl: LiveData<String>
        get() = _ballotInfoUrl

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    var address = ""
    var electionId = 0

    init {
        Timber.i("Timber: init VoterInfoViewModel")
        _selectedElection.value = election
        address =
            _selectedElection.value?.division_state + " " + selectedElection.value?.division_country
        electionId = _selectedElection.value!!.id

        getVoterInfosFromNetwork(address, electionId)
    }

    /**
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the Voterinfo for use, observe [voterinfo]
     */
    private fun getVoterInfosFromNetwork(address: String, electionId: Int) {
        Timber.i("Timber: getVoterInfosFromNetwork")
        viewModelScope.launch {
            try {
                _voterInfo.value = CivicsApi.retrofitService.getVoterInfo(address, electionId)

                _ballotInfoUrl.value =
                    _voterInfo.value?.state?.first()?.electionAdministrationBody?.ballotInfoUrl

                _votingLocationFinderUrl.value =
                    _voterInfo.value?.state?.first()?.electionAdministrationBody?.electionInfoUrl
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.i("Timber: " + e.message)
            }
        }
    }

    fun getUrl(state: Int): String {
        return if (state == 1) _votingLocationFinderUrl.value.toString() else _ballotInfoUrl.value.toString()
    }
}