package com.udacity.politcalpreparedness

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.politcalpreparedness.election.adapter.ElectionListAdapter
import com.udacity.politcalpreparedness.network.models.Election

/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Election>?) {
    val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(data)
}

@BindingAdapter("electionDay")
fun TextView.setElectionDateFormatted(item: Election?) {
    item?.let {
        text = item.electionDay.toString()
    }
}