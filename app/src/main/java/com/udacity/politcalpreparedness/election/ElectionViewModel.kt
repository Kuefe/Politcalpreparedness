package com.udacity.politcalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Json
import com.udacity.politcalpreparedness.network.CivicsApi
import com.udacity.politcalpreparedness.network.CivicsApiService
import com.udacity.politcalpreparedness.network.Elections
import com.udacity.politcalpreparedness.network.models.Election
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info
    fun displayElectionDetails(election: Election){

    }

    init {
        Timber.i("Timber: init")
        getElections()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Timber: onCleared")
    }

    private fun getElections() {
        Timber.i("Timber: getElections")
        CivicsApi.retrofitService.getElections().enqueue( object: Callback<Elections> {
            override fun onFailure(call: Call<Elections>, t: Throwable) {
                _response.value = "Failure: " + t.message
                Timber.i("Timber: onFailure "+t.message)
            }

            override fun onResponse(call: Call<Elections>, response: Response<Elections>) {
                _response.value = "Success: ${response.body()} Elections properties retrieved"
                Timber.i("Timber: onResponse"+_response.value)
            }
        })
    }
}