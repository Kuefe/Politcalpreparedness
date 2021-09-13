package com.udacity.politcalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ElectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg elections: DatabaseElection)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSaved(vararg elections: DatabaseSavedElection)

    @Query("SELECT * FROM election_table")
    fun getElections(): List<DatabaseElection>

    @Query("SELECT * FROM saved_election_table")
    fun getFollowElections(): List<DatabaseSavedElection>

    @Query("SELECT * FROM saved_election_table WHERE id = :key")
    fun checkIfFollow(key: Int): DatabaseSavedElection

    @Delete
    open fun deleteSavedElection(vararg election: DatabaseSavedElection?)

    //TODO: Add clean query

}