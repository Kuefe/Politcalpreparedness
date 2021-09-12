package com.udacity.politcalpreparedness.election.repository

import com.udacity.politcalpreparedness.database.*
import com.udacity.politcalpreparedness.network.CivicsApi
import com.udacity.politcalpreparedness.network.asDatabaseModel
import com.udacity.politcalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ElectionsRepository(private val database: ElectionDatabase) {

    /**
     * get a List of elections from the database an transform then to a domain object
     */
    suspend fun getElectionList(): List<Election> {
        return withContext(Dispatchers.IO) {
            val electionList: List<DatabaseElection> =
                database.electionDao.getElections()
            electionList.asDomainModel()
        }
    }

    /**
     * get a List of followed elections from the database an transform then to a domain object
     */
    suspend fun getFollowElectionList(): List<Election> {
        return withContext(Dispatchers.IO) {
            val electionList: List<DatabaseSavedElection> =
                database.electionDao.getFollowElections()
            electionList.asDomainModelFollow()
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