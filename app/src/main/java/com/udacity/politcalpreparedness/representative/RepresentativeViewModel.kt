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

enum class RepresentativeStatus { LOADING, ERROR, DONE }

class RepresentativeViewModel : ViewModel(), Observable {
    private val propertyChangeRegistry = PropertyChangeRegistry()

    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    private val _status = MutableLiveData<RepresentativeStatus>()
    val status: LiveData<RepresentativeStatus>
        get() = _status

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

    /**
     *  The following code will prove helpful in constructing a representative from the API. T
     *  his code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    fun getAddressFromGeoLocation(addressGeoLocation: Address) {
        address = addressGeoLocation
        getAddressFromIndividualFields()
    }


    // Create function to get address from individual fields
    fun getAddressFromIndividualFields() {
        var query = address.line1 + " "
        query += address.line2 + " "
        query += address.zip + " "
        query += address.city + " "
        query += address.state
        getRepresentativeFromNetwork(query)
    }

    // function to fetch representatives from API from a provided address
    private fun getRepresentativeFromNetwork(address: String) {
        _status.value = RepresentativeStatus.LOADING
        viewModelScope.launch {
            try {
                val (offices, officials) =
                    CivicsApi.retrofitService.getRepresentatives(address)
                createListOfRepresentatives(offices, officials)
                _status.value = RepresentativeStatus.DONE
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = RepresentativeStatus.ERROR
                _representatives.value = null
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
}