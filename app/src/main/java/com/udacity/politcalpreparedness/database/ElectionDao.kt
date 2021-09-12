package com.udacity.politcalpreparedness.database

import androidx.room.*


@Dao
interface ElectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg elections: DatabaseElection)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSaved(vararg elections: DatabaseSavedElection)

    @Query("select * from election_table")
    fun getElections(): List<DatabaseElection>

    @Query("select * from saved_election_table")
    fun getFollowElections(): List<DatabaseSavedElection>

    @Delete
    open fun deleteSavedElection(vararg election: DatabaseSavedElection?)

    //TODO: Add clean query

}