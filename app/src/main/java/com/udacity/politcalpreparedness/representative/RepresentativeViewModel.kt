package com.udacity.politcalpreparedness.representative

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.politcalpreparedness.BR
import com.udacity.politcalpreparedness.network.CivicsApi
import com.udacity.politcalpreparedness.network.models.Address
import com.udacity.politcalpreparedness.network.models.Office
import com.udacity.politcalpreparedness.network.models.Official
import com.udacity.politcalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch
import timber.log.Timber


class RepresentativeViewModel : ViewModel(), Observable {
    private val propertyChangeRegistry = PropertyChangeRegistry()

    private val _representatives = MutableLiveData<List<Representative>>()

    val representatives: LiveData<List<Representative>>
        get() = _representatives

    //Get address from individual fields
    @Bindable
    var address = Address("", "", "", "", "")
        set(value) {
            if (value != field) {
                field = value
                propertyChangeRegistry.notifyChange(this, BR.address)
            }
        }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback)
    }

    init {
        Timber.i("Timber: init")
    }


    /**
     *  The following code will prove helpful in constructing a representative from the API. T
     *  his code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location
    fun getAddressFromGeoLocation() {
        Timber.i("Timber: getAddressFromGeoLocation")
    }


    // Create function to get address from individual fields
    fun getAddressFromIndividualFields() {
        Timber.i("Timber: getAddressFromIndividualFields")
        var query = address.line1 + " "
        query += address.line2 + " "
        query += address.zip + " "
        query += address.city + " "
        query += address.state
        Timber.i("Timber: query: " + query)
        viewModelScope.launch {
            try {
                getRepresentativeFromNetwork(query)
            } catch (e: Exception) {

            }
        }
    }

    // function to fetch representatives from API from a provided address
    private fun getRepresentativeFromNetwork(address: String) {
        viewModelScope.launch {
            try {
                val (offices, officials) =
                    CivicsApi.retrofitService.getRepresentatives(address)
                createListOfRepresentatives(offices, officials)
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.i(e.message)
            }
        }
    }

    // Create a list of representatives to display in the recylerview
    private fun createListOfRepresentatives(offices: List<Office>, officials: List<Official>) {
        val listOfRepresentatives = mutableListOf<Representative>()
        for (office in offices) {
            val representativesList = office.getRepresentatives(officials)

            for (representative in representativesList) {
                listOfRepresentatives.add(representative)
            }
            _representatives.value = listOfRepresentatives
        }
    }

    // function to call the web, facebook or twitter
    fun callSocialMediaChannel(channel: String) {
        Timber.i("Timber: channel: " + channel)
    }
}