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

/*
    @Insert
    suspend fun insert(election: Election)

    *//**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param election new value to write
     *//*
    @Update
    suspend fun update(election: Election)

    @Query("SELECT * from election_table")
    suspend fun getAllElections(): LiveData<List<Election>>

    @Query("SELECT * from election_table WHERE id=:key")
    suspend fun getElectionById(key: Int): Election

    //TODO: Add delete query


    *//**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     *//*
    @Query("DELETE FROM election_table")
    suspend fun clear()*/
}