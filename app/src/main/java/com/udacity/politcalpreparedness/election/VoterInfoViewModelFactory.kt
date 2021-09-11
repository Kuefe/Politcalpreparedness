package com.udacity.politcalpreparedness.election

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.politcalpreparedness.network.models.Election

/**
 * Simple ViewModel factory that provides the Asteroid and context to the ViewModel.
 */
class VoterInfoViewModelFactory(
    private val election: Election,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
            return VoterInfoViewModel(election, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}