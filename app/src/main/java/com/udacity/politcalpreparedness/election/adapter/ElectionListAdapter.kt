package com.udacity.politcalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.politcalpreparedness.databinding.ListViewItemBinding
import com.udacity.politcalpreparedness.network.models.Election

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */

class ElectionListAdapter(val clickListener: ElectionListener) :
    ListAdapter<Election, ElectionListAdapter.ElectionViewHolder>(ElectionDiffCallback) {

    /**
     * The ElectionViewHolder constructor takes the binding variable from the associated
     * ListViewItem, which nicely gives it access to the full [Election] information.
     */
    class ElectionViewHolder(private var binding: ListViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Election) {
            binding.upcomingElection = item
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {

        val item = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(item)
        }
        holder.bind(item)
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ElectionViewHolder {
        return ElectionViewHolder(
            ListViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    object ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
        override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
            return newItem == newItem
        }

        override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
            return newItem.id == oldItem.id
        }

    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Election]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Election]
     */
    class ElectionListener(val clickListener: (election: Election) -> Unit) {
        fun onClick(election: Election) = clickListener(election)
    }

}



