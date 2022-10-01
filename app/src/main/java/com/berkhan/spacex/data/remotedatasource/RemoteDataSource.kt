package com.berkhan.spacex.data.remotedatasource

import com.berkhan.spacex.LaunchQuery
import com.berkhan.spacex.data.NetworkResponse

interface RemoteDataSource {
    suspend fun getLaunch(launchId: String) : NetworkResponse<LaunchQuery.Launch>
}