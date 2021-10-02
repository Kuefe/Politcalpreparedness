package com.udacity.politcalpreparedness.representative.model

import com.udacity.politcalpreparedness.network.models.Office
import com.udacity.politcalpreparedness.network.models.Official


data class Representative (
    val official: Official,
    val office: Office
)
