package com.berkhan.spacex.domain.usecase

import com.berkhan.spacex.domain.repository.LaunchRepository
import javax.inject.Inject

class GetLaunchUseCase @Inject constructor(private val launchRepository: LaunchRepository) {
    operator fun invoke(launchId: String) =
        launchRepository.getLaunch(launchId)
}