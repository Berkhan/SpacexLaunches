package com.berkhan.spacex.domain.usecase

data class LaunchUseCases (
    val getLaunchListUseCase: GetLaunchListUseCase,
    val getLaunchUseCase: GetLaunchUseCase
)