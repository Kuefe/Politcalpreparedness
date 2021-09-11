package com.udacity.politcalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.politcalpreparedness.network.models.Election

@Dao
interface ElectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg elections: DatabaseElection)

    @Query("select * from election_table")
    fun getElections(): List<DatabaseElection>


    //TODO: Add delete query

    //TODO: Add clean query

}