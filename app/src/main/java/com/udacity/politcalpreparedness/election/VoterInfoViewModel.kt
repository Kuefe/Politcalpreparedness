package com.udacity.politcalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.politcalpreparedness.database.ElectionDatabase
import com.udacity.politcalpreparedness.election.repository.VoterInfoRepository
import com.udacity.politcalpreparedness.network.CivicsApi
import com.udacity.politcalpreparedness.network.models.Election
import com.udacity.politcalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch
import timber.log.Timber

enum class FollowState(val state: Boolean) { FOLLOW(true), UNFOLLOW(false) }

class VoterInfoViewModel(election: Election, application: Application) : ViewModel() {
    private val database = ElectionDatabase.getInstance(application)
    private val voterInfoRepository = VoterInfoRepository(database)

    private val _selectedElection = MutableLiveData<Election>()

    val selectedElection: LiveData<Election>
        get() = _selectedElection

    private val _voterInfo = MutableLiveData<VoterInfoResponse>()

    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    private val _votingLocationFinderUrl = MutableLiveData<String>()
    val votingLocationFinderUrl: LiveData<String>
        get() = _votingLocationFinderUrl

    private val _ballotInfoUrl = MutableLiveData<String>()

    val ballotInfoUrl: LiveData<String>
        get() = _ballotInfoUrl

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<FollowState>()

    // The external immutable LiveData for the request status
    val status: LiveData<FollowState>
        get() = _status

    var address = ""
    var electionId = 0

    init {
        _selectedElection.value = election
        address =
            _selectedElection.value?.division_state + " " + selectedElection.value?.division_country
        electionId = _selectedElection.value!!.id

        _status.value = FollowState.FOLLOW
        viewModelScope.launch {
            try {
                _status.value = voterInfoRepository.checkFollowElection(electionId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

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
        viewModelScope.launch {
            try {
                _voterInfo.value = CivicsApi.retrofitService.getVoterInfo(address, electionId)

                _ballotInfoUrl.value =
                    _voterInfo.value?.state?.first()?.electionAdministrationBody?.ballotInfoUrl

                _votingLocationFinderUrl.value =
                    _voterInfo.value?.state?.first()?.electionAdministrationBody?.electionInfoUrl
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getUrl(state: Int): String? {
        return if (state == 1) _votingLocationFinderUrl.value else _ballotInfoUrl.value
    }

    fun switchFollowState() {
        if (_status.value == FollowState.FOLLOW) {
            followElection()
            _status.value = FollowState.UNFOLLOW
        } else {
            unfollowElection()
            _status.value = FollowState.FOLLOW
        }
    }

    fun followElection() {
        viewModelScope.launch {
            try {
                voterInfoRepository.saveElection(_selectedElection.value!!)
            } catch (e: java.lang.Exception) {
                Timber.e(e.message)
            }
        }
    }

    fun unfollowElection() {
        viewModelScope.launch {
            try {
                voterInfoRepository.deleteElection(_selectedElection.value!!)
            } catch (e: java.lang.Exception) {
                Timber.e(e.message)
            }
        }
    }
}