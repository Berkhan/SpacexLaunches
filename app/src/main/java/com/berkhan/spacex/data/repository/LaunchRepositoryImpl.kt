package com.berkhan.spacex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.berkhan.spacex.LaunchListQuery
import com.berkhan.spacex.LaunchQuery
import com.berkhan.spacex.data.NetworkResponse
import com.berkhan.spacex.data.pagingdatasource.LaunchPagingDataSource
import com.berkhan.spacex.data.remotedatasource.RemoteDataSourceImpl
import com.berkhan.spacex.di.coroutine.IoDispatcher
import com.berkhan.spacex.domain.repository.LaunchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 10

class LaunchRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val remoteDataSource: RemoteDataSourceImpl,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) : LaunchRepository {

    override fun getLaunches(): Flow<PagingData<LaunchListQuery.Launch>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { LaunchPagingDataSource(apolloClient) }
        ).flow
    }

    override fun getLaunch(launchId: String): Flow<NetworkResponse<LaunchQuery.Launch>> {
        return flow {
            emit(NetworkResponse.Loading)
            val response = remoteDataSource.getLaunch(launchId)
            when (response) {
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(response.exception))
                }
                is NetworkResponse.Success -> {
                    emit(NetworkResponse.Success(response.result))
                }
                else -> {}
            }
        }.flowOn(defaultDispatcher)
    }
}