package com.berkhan.spacex.data.pagingdatasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.berkhan.spacex.LaunchListQuery
import okio.IOException

const val START_INDEX = 1

class LaunchPagingDataSource(private val apolloClient: ApolloClient) :
    PagingSource<Int, LaunchListQuery.Launch>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LaunchListQuery.Launch> {

        return try {

            val offset = (params.key ?: START_INDEX)
            val limit = params.loadSize

            val response = apolloClient.query(
                LaunchListQuery(
                    limit = Optional.present(limit),
                    offset = Optional.present(offset)
                )
            ).execute()

            val data = if (!response.hasErrors() && response.data?.launches != null) {
                val launches = response.data?.launches!!
                launches.filterNotNull().filter {it.id != null }
            } else {
                throw Exception(response.errors?.toString())
            }

            LoadResult.Page(
                data = data,
                prevKey = if (offset == START_INDEX) null else offset.minus(1),
                nextKey = if (data.isEmpty()) null else offset.plus(params.loadSize)
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LaunchListQuery.Launch>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}