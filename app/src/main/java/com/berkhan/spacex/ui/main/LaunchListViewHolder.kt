package com.berkhan.spacex.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.berkhan.spacex.LaunchListQuery
import com.berkhan.spacex.R
import com.berkhan.spacex.databinding.ItemLaunchBinding
import com.bumptech.glide.Glide

class LaunchListViewHolder(
    private val binding: ItemLaunchBinding,
    private val onItemClickListener: ((String) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(launchItem: LaunchListQuery.Launch) {

        with(binding) {
            tvLaunchItemMissionName.text = launchItem.mission_name
            tvLaunchItemSite.text = launchItem.launch_site?.site_name

            Glide.with(root)
                .load(launchItem.links?.flickr_images?.firstOrNull())
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.no_image)
                .into(imLaunchItem)
        }
        itemView.setOnClickListener { onItemClickListener?.invoke(launchItem.id!!) }
    }
}