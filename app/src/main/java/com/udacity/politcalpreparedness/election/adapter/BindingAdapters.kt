package com.udacity.politcalpreparedness

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.politcalpreparedness.election.FollowState
import com.udacity.politcalpreparedness.election.adapter.ElectionListAdapter
import com.udacity.politcalpreparedness.network.models.Election
import java.text.SimpleDateFormat
import java.util.*

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
        val pattern = "YYYY-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        text = simpleDateFormat.format(item.electionDay)
    }
}

@BindingAdapter("followButton")
fun setFollowText(button: Button, status: FollowState) {
    val context = button.context
    button.text =
        if (status == FollowState.UNFOLLOW)
            context.getText(R.string.unfollow_election)
        else context.getText(R.string.follow_election)
}