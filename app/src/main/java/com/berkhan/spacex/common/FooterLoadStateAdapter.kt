package com.berkhan.spacex.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.berkhan.spacex.R
import com.berkhan.spacex.databinding.LaunchesLoadStateFooterBinding

class FooterLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FooterLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: FooterLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FooterLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.launches_load_state_footer, parent, false)
        val binding = LaunchesLoadStateFooterBinding.bind(view)
        return FooterLoadStateViewHolder(binding, retry)
    }
}
