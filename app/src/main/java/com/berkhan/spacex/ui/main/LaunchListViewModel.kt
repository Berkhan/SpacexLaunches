package com.berkhan.spacex.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.berkhan.spacex.LaunchListQuery
import com.berkhan.spacex.domain.usecase.LaunchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(private val launchUseCases: LaunchUseCases) :
    ViewModel() {

    private lateinit var _launchesFlow: Flow<PagingData<LaunchListQuery.Launch>>
    val launchesFlow: Flow<PagingData<LaunchListQuery.Launch>>
        get() = _launchesFlow


    fun getLaunches() {
        _launchesFlow = launchUseCases
            .getLaunchListUseCase()
            .cachedIn(viewModelScope)
    }
}