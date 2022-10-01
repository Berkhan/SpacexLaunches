package com.berkhan.spacex.domain.usecase

import androidx.paging.PagingData
import com.berkhan.spacex.LaunchListQuery
import com.berkhan.spacex.domain.repository.LaunchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLaunchListUseCase @Inject constructor(private val launchRepository: LaunchRepository) {
    operator fun invoke(): Flow<PagingData<LaunchListQuery.Launch>> =
        launchRepository.getLaunches()
}