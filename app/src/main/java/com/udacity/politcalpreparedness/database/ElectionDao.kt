package com.udacity.politcalpreparedness.database

import androidx.room.*


@Dao
interface ElectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg elections: DatabaseElection)

    @Query("select * from election_table")
    fun getElections(): List<DatabaseElection>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSaved(vararg elections: DatabaseSavedElection)

    //TODO: Add delete query
    @Delete
    open fun deleteSavedElection(vararg election: DatabaseSavedElection?)

    //TODO: Add clean query

}