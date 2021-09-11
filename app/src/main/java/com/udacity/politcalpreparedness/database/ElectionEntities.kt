package com.udacity.politcalpreparedness.database

import androidx.annotation.Nullable
import androidx.room.*
import com.udacity.politcalpreparedness.network.models.Division
import com.udacity.politcalpreparedness.network.models.Election
import java.util.*

@Entity(tableName = "election_table")
data class DatabaseElection constructor(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "electionDay") val electionDay: Date,
    @Embedded(prefix = "division_") val division: Division,
)

fun List<DatabaseElection>.asDomainModel(): List<Election> {
    return map {
        Election(
            id = it.id,
            name = it.name,
            electionDay = it.electionDay,
            division_id = it.division.id,
            division_state = it.division.state,
            division_country = it.division.country
        )
    }
}
