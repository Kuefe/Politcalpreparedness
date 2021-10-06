package com.udacity.politcalpreparedness

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.politcalpreparedness.election.ElectionStatus
import com.udacity.politcalpreparedness.election.FollowState
import com.udacity.politcalpreparedness.election.adapter.ElectionListAdapter
import com.udacity.politcalpreparedness.election.adapter.FollowElectionListAdapter
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

/**
 * When there is no Election data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listdataFollow")
fun bindRecyclerViewFollow(recyclerView: RecyclerView, data: List<Election>?) {
    val adapter = recyclerView.adapter as FollowElectionListAdapter
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

/**
 * This binding adapter displays the [ElectionStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("electionStatus")
fun bindStatus(statusImageView: ImageView, status: ElectionStatus?) {
    when (status) {
        ElectionStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ElectionStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ElectionStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

