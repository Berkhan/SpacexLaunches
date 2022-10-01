package com.berkhan.spacex.data.remotedatasource

import com.apollographql.apollo3.ApolloClient
import com.berkhan.spacex.LaunchQuery
import com.berkhan.spacex.data.NetworkResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    RemoteDataSource {

    override suspend fun getLaunch(launchId: String): NetworkResponse<LaunchQuery.Launch> =
        try {
            val response = apolloClient.query(
                LaunchQuery(
                    id = launchId
                )
            ).execute()
            NetworkResponse.Success(response.data?.launch!!)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
}