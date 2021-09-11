package com.udacity.politcalpreparedness.network.models

import android.os.Parcelable
import androidx.annotation.Nullable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Election(
    val id: Int,
    val name: String,
    val electionDay: Date,
    val division_id: String?,
    val division_state: String?,
    val division_country: String?,
) : Parcelable