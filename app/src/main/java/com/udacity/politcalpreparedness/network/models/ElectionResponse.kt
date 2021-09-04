package com.udacity.politcalpreparedness.network.models

import com.squareup.moshi.JsonClass
import com.udacity.politcalpreparedness.network.models.Election

@JsonClass(generateAdapter = true)
data class ElectionResponse(
    val kind: String,
    val elections: List<Election>
)
