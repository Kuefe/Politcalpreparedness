package com.udacity.politcalpreparedness.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.politcalpreparedness.database.ElectionDatabase.Companion.getInstance
import com.udacity.politcalpreparedness.election.repository.ElectionsRepository

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val repository = ElectionsRepository(database)
        return try {
            repository.getElectionsFromNetwork()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}