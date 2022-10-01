package com.berkhan.spacex.common

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.berkhan.spacex.databinding.LaunchesLoadStateFooterBinding

class FooterLoadStateViewHolder(
    private val binding: LaunchesLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        with(binding) {
            if (loadState is LoadState.Error) {
                footerMessage.text = loadState.error.localizedMessage
            }
            pbFooter.isVisible = (loadState is LoadState.Loading)
            btnRetry.isVisible = (loadState is LoadState.Error)
            footerMessage.isVisible = (loadState is LoadState.Error)
        }
    }
}