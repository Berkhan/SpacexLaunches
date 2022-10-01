package com.berkhan.spacex.domain.repository

import androidx.paging.PagingData
import com.berkhan.spacex.LaunchListQuery
import com.berkhan.spacex.LaunchQuery
import com.berkhan.spacex.data.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface LaunchRepository {
    fun getLaunches(): Flow<PagingData<LaunchListQuery.Launch>>
    fun getLaunch(launchId: String): Flow<NetworkResponse<LaunchQuery.Launch>>
}