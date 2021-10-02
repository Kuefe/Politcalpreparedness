package com.udacity.politcalpreparedness.representative.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.politcalpreparedness.databinding.ListViewItemBinding
import com.udacity.politcalpreparedness.databinding.ListViewItemRepresentativeBinding
import com.udacity.politcalpreparedness.representative.model.Representative

class RepresentativeListAdapter() :
    ListAdapter<Representative, RepresentativeListAdapter.RepresentativeViewHolder>(DiffCallback) {

    /**
     * The representatives that our Adapter will show
     */
    var representatives: List<Representative> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    /**
     * The RepresentativeViewHolder constructor takes the binding variable from the associated
     * ListViewItem, which nicely gives it access to the full [Representative] information.
     */
    class RepresentativeViewHolder(private var binding: ListViewItemRepresentativeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Representative) {
            binding.representative = item
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: RepresentativeViewHolder, position: Int) {

        val item = getItem(position)

        holder.bind(item)
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepresentativeViewHolder {
        return RepresentativeViewHolder(
            ListViewItemRepresentativeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    object DiffCallback : DiffUtil.ItemCallback<Representative>() {
        override fun areItemsTheSame(oldItem: Representative, newItem: Representative): Boolean {
            return newItem == newItem
        }

        override fun areContentsTheSame(oldItem: Representative, newItem: Representative): Boolean {
            return newItem.official.name == oldItem.official.name
        }

    }

}