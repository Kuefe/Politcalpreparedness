package com.udacity.politcalpreparedness.network.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.util.*


data class Election(
    val id: Int,
    val name: String,
    val electionDay: Date,
    val division_id: String,
    val division_state: String,
    val division_country: String
)