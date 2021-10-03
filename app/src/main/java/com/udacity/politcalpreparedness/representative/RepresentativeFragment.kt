package com.udacity.politcalpreparedness.representative

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.politcalpreparedness.databinding.FragmentRepresentativeBinding
import com.udacity.politcalpreparedness.network.models.Address
import com.udacity.politcalpreparedness.representative.adapter.RepresentativeListAdapter
import timber.log.Timber
import java.util.*

class RepresentativeFragment : Fragment() {

    companion object {
        //TODO: Add Constant for Location request
    }

    /**
     * Lazily initialize our [RepresentativeViewModel].
     */
    private val viewModel: RepresentativeViewModel by lazy {
        ViewModelProvider(this).get(RepresentativeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRepresentativeBinding.inflate(inflater, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.address.state = binding.state.selectedItem as String
                Timber.i("Timber: onNothingSelected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Timber.i("Timber: onItemSelected")
                viewModel.address.state = binding.state.selectedItem as String
                Timber.i("Timber: " + viewModel.address.state)
            }
        }

        // Sets the adapter of the RepresentativeView RecyclerView
        binding.representativeRecycler.adapter = RepresentativeListAdapter()

        //TODO: Populate Representative adapter

        //TODO: Establish button listeners for field and location search
        binding.buttonSearch.setOnClickListener {
            viewModel.getAddressFromIndividualFields()
        }

        binding.buttonLocation.setOnClickListener {
            viewModel.getAddressFromGeoLocation()
        }

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //TODO: Handle location permission result to get location on permission granted
    }

    private fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            true
        } else {
            //TODO: Request Location permissions
            false
        }
    }

    private fun isPermissionGranted(): Boolean {
        //TODO: Check if permission is already granted and return (true = granted, false = denied/other)
        return true
    }

    private fun getLocation() {
        //TODO: Get location from LocationServices
        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
            .map { address ->
                Address(
                    address.thoroughfare,
                    address.subThoroughfare,
                    address.locality,
                    address.adminArea,
                    address.postalCode
                )
            }
            .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}
