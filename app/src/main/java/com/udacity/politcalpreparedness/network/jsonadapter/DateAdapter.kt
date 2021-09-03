package com.udacity.politcalpreparedness.network.jsonadapter

import android.icu.text.SimpleDateFormat
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class DateAdapter {
    @FromJson
    fun dateFromJson(electionDay: String): Date {
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormat.parse(electionDay)!!
    }
    @ToJson
    fun dateToJson(electionDay: Date) : String {
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormat.format(electionDay)
    }
}