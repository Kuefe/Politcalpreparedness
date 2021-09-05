package com.udacity.politcalpreparedness.representative

import com.udacity.politcalpreparedness.database.ElectionDatabase
import com.udacity.politcalpreparedness.network.CivicsApi
import com.udacity.politcalpreparedness.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ElectionsRepository(private val database: ElectionDatabase) {
    suspend fun getElectionsFromNetwork() {
        Timber.i("Timber: getElections")
        withContext(Dispatchers.IO) {
            try {
                val election = CivicsApi.retrofitService.getElections()
                database.electionDao.insertAll(*election.asDatabaseModel())
                Timber.i("election: " + election)
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.i(e.message)
            }
        }
    }
}