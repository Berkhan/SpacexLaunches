package com.berkhan.spacex.ui.detail

import android.view.View
import com.berkhan.spacex.LaunchQuery

data class LaunchDetailUiState(
    val data: LaunchQuery.Launch? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
) {
    fun getProgressBarVisibility() = if (isLoading) View.VISIBLE else View.GONE
    fun getDataVisibility() = !isLoading && (errorMessage == null)

    companion object {
        fun initial() = LaunchDetailUiState()
    }
}