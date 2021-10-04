package com.udacity.politcalpreparedness.representative.adapter

import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.udacity.politcalpreparedness.R
import com.udacity.politcalpreparedness.representative.model.Representative
import timber.log.Timber

/**
 * When there is no Representative data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listdataRepresentative")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Representative>?) {
    val adapter = recyclerView.adapter as RepresentativeListAdapter
    adapter.submitList(data) {
        recyclerView.scrollToPosition(0)
    }
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("profileImage")
fun fetchImage(view: ImageView, src: String?) {
    src?.let {
        val uri = src.toUri().buildUpon().scheme("https").build()
        Glide.with(view.context)
            .load(uri)
            .circleCrop()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
            )
            .into(view)
    }
}

@BindingAdapter("stateValue")
fun Spinner.setNewValue(value: String?) {
    Timber.i("Timber: setNewValue")
    val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
    val position = when (adapter.getItem(0)) {
        is String -> adapter.getPosition(value)
        else -> this.selectedItemPosition
    }
    if (position >= 0) {
        setSelection(position)
    }
}

@BindingAdapter("webImage")
fun ImageView.setWebImage(item: Representative) {
    visibility = View.GONE
    if (item.official.urls != null) {
        visibility = View.VISIBLE
        setImageResource(R.drawable.ic_www)
    }
}

@BindingAdapter("faceBookImage")
fun ImageView.setFacebookImage(item: Representative) {
    val channels = item.official.channels
    visibility = View.GONE
    if (channels != null) {
        for (channel in channels) {
            if (channel.type == "Facebook") {
                visibility = View.VISIBLE
                setImageResource(R.drawable.ic_facebook)
            }
        }
    }
}

@BindingAdapter("twitterImage")
fun ImageView.setTwitterImage(item: Representative) {
    visibility = View.GONE
    val channels = item.official.channels
    if (channels != null) {
        for (channel in channels) {
            if (channel.type == "Twitter") {
                visibility = View.VISIBLE
                setImageResource(R.drawable.ic_twitter)
            }
        }
    }
}

inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T> {
    return adapter as ArrayAdapter<T>
}