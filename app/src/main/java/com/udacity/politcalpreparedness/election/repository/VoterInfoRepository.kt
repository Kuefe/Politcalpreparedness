package com.udacity.politcalpreparedness.election.repository

import com.udacity.politcalpreparedness.database.ElectionDatabase
import com.udacity.politcalpreparedness.database.asDatabaseModel
import com.udacity.politcalpreparedness.election.FollowState
import com.udacity.politcalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VoterInfoRepository(private val database: ElectionDatabase) {

    suspend fun saveElection(election: Election) {
        withContext(Dispatchers.IO) {
            try {
                database.electionDao.insertSaved(asDatabaseModel(election))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun deleteElection(election: Election) {
        withContext(Dispatchers.IO) {
            try {
                database.electionDao.deleteSavedElection(asDatabaseModel(election))
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun checkFollowElection(electionId: Int): FollowState {
        var status = FollowState.FOLLOW
        withContext(Dispatchers.IO) {
            try {
                @Suppress("SENSELESS_COMPARISON")
                if (database.electionDao.checkIfFollow(electionId) != null) {
                    status = FollowState.UNFOLLOW
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        return status
    }
}