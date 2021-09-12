package com.udacity.politcalpreparedness.network

import androidx.room.Ignore
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.politcalpreparedness.database.DatabaseElection
import com.udacity.politcalpreparedness.network.models.Division
import java.util.*

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 */

/**
 * ElectionHolder holds a list of Elections.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "elections": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkElectionContainer(
    val kind: String,
    val elections: List<NetworkElection>
)

/**
 * Elections can be displayed
 */
@JsonClass(generateAdapter = true)
data class NetworkElection(
    val id: Int,
    val name: String,
    val electionDay: Date,
    @Json(name = "ocdDivisionId") val division: Division,
)


/**
 * Convert Network results to database objects
 */
fun NetworkElectionContainer.asDatabaseModel(): Array<DatabaseElection> {
    return elections.map {
        DatabaseElection(
            id = it.id,
            name = it.name,
            electionDay = it.electionDay,
            division = Division(it.division.id, it.division.state, it.division.country)
        )
    }.toTypedArray()
}
