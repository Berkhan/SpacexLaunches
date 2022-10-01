package com.berkhan.spacex.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.berkhan.spacex.LaunchListQuery
import com.berkhan.spacex.databinding.ItemLaunchBinding

class LaunchAdapter : PagingDataAdapter<LaunchListQuery.Launch, LaunchListViewHolder>(Comparator) {

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: ((String) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: LaunchListViewHolder, position: Int) {
        getItem(position)?.let { item -> holder.bind(item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemLaunchBinding = ItemLaunchBinding.inflate(inflater, parent, false)

        return LaunchListViewHolder(itemLaunchBinding, onItemClickListener)
    }

    object Comparator : DiffUtil.ItemCallback<LaunchListQuery.Launch>() {
        override fun areItemsTheSame(
            oldItem: LaunchListQuery.Launch,
            newItem: LaunchListQuery.Launch
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LaunchListQuery.Launch,
            newItem: LaunchListQuery.Launch
        ): Boolean {
            return oldItem == newItem
        }
    }
}