package com.udacity.politcalpreparedness.election.repository

import android.provider.ContactsContract
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.politcalpreparedness.database.DatabaseElection
import com.udacity.politcalpreparedness.database.ElectionDatabase
import com.udacity.politcalpreparedness.database.asDomainModel
import com.udacity.politcalpreparedness.network.CivicsApi
import com.udacity.politcalpreparedness.network.asDatabaseModel
import com.udacity.politcalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ElectionsRepository(private val database: ElectionDatabase) {

    /**
     * get a List of asteroids from the database an transform then to a domain object
     */
    suspend fun getElectionList(): List<Election> {
        return withContext(Dispatchers.IO) {
            val electionList: List<DatabaseElection> =
                database.electionDao.getElections()
            electionList.asDomainModel()
        }
    }

    /**
     * Refresh the elections stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the elections for use, observe [elections]
     */
    suspend fun getElectionsFromNetwork() {
        Timber.i("Timber: getElections")
        withContext(Dispatchers.IO) {
            try {
                val election = CivicsApi.retrofitService.getElections()
                database.electionDao.insertAll(*election.asDatabaseModel())
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.i(e.message)
            }
        }
    }
}