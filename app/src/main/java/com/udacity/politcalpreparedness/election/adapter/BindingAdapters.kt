package com.udacity.politcalpreparedness

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.udacity.politcalpreparedness.election.adapter.ElectionListAdapter
import com.udacity.politcalpreparedness.network.models.Election

/**
 * When there is no Election data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listdata")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Election>?) {
    val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(data) {
        recyclerView.scrollToPosition(0)
    }
}

@BindingAdapter("electionDay")
fun TextView.setElectionDateFormatted(item: Election?) {
    item?.let {
        text = item.electionDay.toString()
    }
}