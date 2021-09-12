package com.udacity.politcalpreparedness.election.repository

import com.udacity.politcalpreparedness.database.ElectionDatabase
import com.udacity.politcalpreparedness.database.asDatabaseModel
import com.udacity.politcalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class VoterInfoRepository(private val database: ElectionDatabase) {

    suspend fun saveElection(election: Election) {
        Timber.i("Timber: saveElection")
        withContext(Dispatchers.IO) {
            try {
                database.electionDao.insertSaved(asDatabaseModel(election))
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.i("Timber: " + e.message)
            }
        }
    }

    suspend fun deleteElection(election: Election) {
        Timber.i("Timber: deleteElection")
        withContext(Dispatchers.IO) {
            try {
                database.electionDao.deleteSavedElection(asDatabaseModel(election))
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Timber.i("Timber: " + e.message)
            }
        }
    }
}